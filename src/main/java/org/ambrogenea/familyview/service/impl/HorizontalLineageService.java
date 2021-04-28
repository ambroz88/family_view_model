package org.ambrogenea.familyview.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.DescendentTreeInfo;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;

public class HorizontalLineageService extends HorizontalAncestorService implements LineageService {

    public HorizontalLineageService(ConfigurationService configuration) {
        super(configuration);
    }

    @Override
    public void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson) {
        if (getConfiguration().isShowSpouses()) {
            Position lastSpouse = addRootSpouses(rootPersonPosition, rootPerson);
            if (getConfiguration().isShowSiblings()) {
                addSiblingsAroundWives(rootPersonPosition, rootPerson, lastSpouse.getX());
            }
        } else if (getConfiguration().isShowSiblings()) {
            addSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public void generateFathersFamily(Position child, AncestorPerson person) {
        if (person != null && person.getMother() != null) {

            addVerticalLineToParents(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, person.getBirthDatePlace().getSimplePlace());
            }

            if (person.getFather() != null) {
                addMother(child, person.getMother(),
                        person.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale()));

                Position fatherPosition = addFather(child, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    addSiblingsAroundMother(fatherPosition, person.getFather());
                }

                generateFathersFamily(fatherPosition, person.getFather());
            } else {
                Position motherPosition = child.addXAndY(0, getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);
                addPerson(motherPosition, person.getMother());

                generateFathersFamily(motherPosition, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    addSiblingsAroundMother(motherPosition, person.getMother());
                }
            }

        }
    }

    @Override
    public void generateMotherFamily(Position childPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            addVerticalLineToParents(childPosition);

            Position motherPosition;
            if (person.getFather() != null) {
                addFather(childPosition, person.getFather());
                motherPosition = addMother(childPosition, person.getMother(),
                        person.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale()));

                if (configuration.isShowSiblings()) {
//                    addSiblingsAroundFather(motherPosition, person.getMother());
                }
            } else {
                motherPosition = addFather(childPosition, person.getMother());

                if (configuration.isShowSiblings()) {
                    addSiblingsAroundMother(motherPosition, person.getMother());
                }
            }

            if (configuration.isShowHeraldry()) {
                addHeraldry(childPosition, person.getBirthDatePlace().getSimplePlace());
            }

            generateFathersFamily(motherPosition, person.getMother());
        }
    }

    @Override
    public Position calculateMotherPosition(Position fatherPosition, AncestorPerson rootPerson) {
        int fathersSiblings = rootPerson.getFather().getMaxYoungerSiblings();
        int mothersSiblings = rootPerson.getMother().getMaxOlderSiblings();
        int siblingsAmount = fathersSiblings + mothersSiblings;
        int siblingsWidth = siblingsAmount * (configuration.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + 2 * Spaces.SIBLINGS_GAP;
        int parentWidth = rootPerson.getMother().getFatherGenerations() * configuration.getParentImageSpace();
        return fatherPosition.addXAndY(
                configuration.getAdultImageWidth() + Math.max(siblingsWidth + parentWidth, configuration.getWideMarriageLabel()),
                0);
    }

    @Override
    public void addFirstParents(Position childPosition, AncestorPerson child) {
        addAllParents(childPosition, child);
    }

    private void addAllParents(Position childPosition, AncestorPerson child) {
        if (child.getMother() != null) {
            int verticalShift = -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
            double motherParentsCount = Math.min(child.getMother().getInnerParentsCount(), child.getMother().getLastParentsCount());

            Position motherPosition;
            if (child.getFather() == null) {
                motherPosition = childPosition.addXAndY(0, verticalShift);
            } else {
                double fatherParentsCount = Math.min(child.getFather().getInnerParentsCount(), child.getFather().getLastParentsCount());

                Position fatherPosition;
                if (motherParentsCount == 0 || fatherParentsCount == 0) {
                    fatherPosition = childPosition.addXAndY(-getConfiguration().getHalfSpouseLabelSpace(), verticalShift);
                    motherPosition = childPosition.addXAndY(getConfiguration().getHalfSpouseLabelSpace(), verticalShift);
                } else {
                    double motherParentWidth = (getConfiguration().getCoupleWidth() + Spaces.SIBLINGS_GAP) * motherParentsCount;
                    double fatherParentWidth = (getConfiguration().getCoupleWidth() + Spaces.SIBLINGS_GAP) * fatherParentsCount;
                    int halfParentWidth = (int) (fatherParentWidth + motherParentWidth) / 2;
                    fatherPosition = childPosition.addXAndY(-halfParentWidth, verticalShift);
                    motherPosition = childPosition.addXAndY(halfParentWidth, verticalShift);
                }

                addPerson(fatherPosition, child.getFather());
                Position labelPosition = fatherPosition.addXAndY(getConfiguration().getAdultImageWidth() / 2 + Spaces.LABEL_GAP,
                        -getConfiguration().getMarriageLabelHeight() / 2);
                addLabel(labelPosition,
                        motherPosition.getX() - fatherPosition.getX() - getConfiguration().getAdultImageWidth() - 2 * Spaces.LABEL_GAP,
                        child.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale())
                );
                addAllParents(fatherPosition, child.getFather());
            }

            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(childPosition, child.getBirthDatePlace().getSimplePlace());
            }

            addPerson(motherPosition, child.getMother());
            addAllParents(motherPosition, child.getMother());
            addVerticalLineToParents(childPosition);
        }
    }

    @Override
    public Position generateAllDescendents(Position rootPosition, List<AncestorCouple> spouseCouples) {
        int allChildrenCoupleCount = calculateCouplesCount(spouseCouples);
        int allChildrenSinglesCount = calculateSinglesCount(spouseCouples);
        int descendentsWidth = allChildrenCoupleCount * (configuration.getCoupleWidth() + Spaces.SIBLINGS_GAP)
                + allChildrenSinglesCount * (configuration.getAdultImageWidth() + Spaces.SIBLINGS_GAP);
        Position actualChildPosition = rootPosition.addXAndY(
                descendentsWidth / 2,
                configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP
        );

        for (AncestorCouple spouseCouple : spouseCouples) {
            Position nextChildPosition = addCoupleFamily(actualChildPosition, spouseCouple, descendentsWidth);
            actualChildPosition = new Position(nextChildPosition);
        }

        return rootPosition.addXAndY(descendentsWidth, 0);
    }

    @Override
    public Position addCoupleFamily(Position parentPosition, AncestorCouple couple, int descendentsWidth) {
        Position startChildPosition = parentPosition.addXAndY(-descendentsWidth / 2, 0);
        for (AncestorPerson child : couple.getChildren()) {
            Position startNextGeneration = generateAllDescendents(startChildPosition, child.getSpouseCouples());
            int centerX = (startNextGeneration.getX() - startChildPosition.getX()) / 2;
            Position parent = startChildPosition.addXAndY(centerX - configuration.getSpouseDistance() / 2, 0);
            addPerson(parent, child);
            Position spousePosition = addSpouse(parent, child);
            if (startNextGeneration.getX() > spousePosition.getX()) {
                startChildPosition = startNextGeneration.addXAndY(configuration.getAdultImageWidth() + Spaces.SIBLINGS_GAP, 0);
            } else {
                startChildPosition = spousePosition.addXAndY(configuration.getAdultImageWidth() + Spaces.SIBLINGS_GAP, 0);
            }
        }
        return startChildPosition;
    }

    private int calculateCouplesCount(List<AncestorCouple> spouseCouples) {
//        int maxDescendentCouples = 0;
//        maxDescendentCouples = spouseCouples.stream()
        return (int) spouseCouples.stream()
                .map(spouseCouple -> spouseCouple.getDescendentTreeInfo()/*.getMaxCouplesCount()*/)
                //                .reduce(maxDescendentCouples, Integer::sum);
                .collect(Collectors.summarizingInt(DescendentTreeInfo::getMaxCouplesCount))
                .getSum();
//        return maxDescendentCouples;
    }

    private int calculateSinglesCount(List<AncestorCouple> spouseCouples) {
//        int maxDescendentSingles = 0;
        return (int) spouseCouples.stream()
                .map(spouseCouple -> spouseCouple.getDescendentTreeInfo()/*.getMaxSinglesCount()*/)
                //                .reduce(maxDescendentSingles, Integer::sum);
                .collect(Collectors.summarizingInt(DescendentTreeInfo::getMaxSinglesCount))
                .getSum();
//        return maxDescendentSingles;
    }

}
