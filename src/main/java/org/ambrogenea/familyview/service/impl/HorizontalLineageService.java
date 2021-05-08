package org.ambrogenea.familyview.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.DescendentTreeInfo;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Relation;
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
    public Position generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth) {
        Position actualChildPosition = firstChildPosition.addXAndY(0,
                configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP
        );

        int descendentsWidth;
        for (AncestorCouple spouseCouple : spouseCouples) {
            int childrenCoupleCount = spouseCouple.getDescendentTreeInfo().getMaxCouplesCount();
            int childrenSinglesCount = spouseCouple.getDescendentTreeInfo().getMaxSinglesCount();
            descendentsWidth = childrenCoupleCount * (configuration.getCoupleWidth() + Spaces.HORIZONTAL_GAP)
                    + childrenSinglesCount * (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);

            actualChildPosition = actualChildPosition.addXAndY(descendentsWidth / 2, 0);
            actualChildPosition = addCoupleFamily(actualChildPosition, spouseCouple, descendentsWidth);
        }

        return firstChildPosition.addXAndY(Math.max(configuration.getAdultImageWidth(), allDescendentsWidth), 0);
    }

    @Override
    public Position addCoupleFamily(Position parentCentralPosition, AncestorCouple couple, int descendentsWidth) {
        Position actualChildPosition = parentCentralPosition.addXAndY(-descendentsWidth / 2, 0);
        List<AncestorPerson> children = couple.getChildren();
        for (int i = 0; i < children.size(); i++) {
            AncestorPerson child = children.get(i);

            int allChildrenCoupleCount = calculateCouplesCount(child.getSpouseCouples());
            int allChildrenSinglesCount = calculateSinglesCount(child.getSpouseCouples());
            int allDescendentsWidth = allChildrenCoupleCount * (configuration.getCoupleWidth() + Spaces.HORIZONTAL_GAP)
                    + allChildrenSinglesCount * (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);
            int childWidth = child.getSpouseCount() * configuration.getCoupleWidth();

            boolean widerParents = false;
            if (allDescendentsWidth < childWidth) {
                actualChildPosition = actualChildPosition.addXAndY((childWidth - allDescendentsWidth) / 2, 0);
                widerParents = true;
            }

            Position endGenerationPosition = generateAllDescendents(actualChildPosition, child.getSpouseCouples(), allDescendentsWidth);
            int centerX = (endGenerationPosition.getX() - actualChildPosition.getX()) / 2;
            Position parent;
            if (child.getSpouse() != null || widerParents) {
                parent = actualChildPosition.addXAndY(centerX - configuration.getSpouseDistance() / 2, 0);
            } else {
                parent = actualChildPosition.addXAndY(centerX, 0);
            }

            addPerson(parent, child);
            Position spousePosition = addSpouse(parent, child);

            addLineToChildren(child, parent);
            addLineFromChildren(children.size(), i, parent, parentCentralPosition);

            if (endGenerationPosition.getX() > spousePosition.getX()) {
                actualChildPosition = endGenerationPosition.addXAndY(Spaces.HORIZONTAL_GAP, 0);
            } else {
                actualChildPosition = spousePosition.addXAndY(configuration.getAdultImageWidth() / 2 + Spaces.SIBLINGS_GAP, 0);
            }
        }
        return actualChildPosition;
    }

    private void addLineFromChildren(int childrenCount, int childrenIndex, Position childPosition, Position parentCentralPosition) {
        if (childrenCount == 1 || (childrenCount > 2 && childrenIndex > 0 && childrenIndex < childrenCount - 1)) {
            addLine(childPosition,
                    childPosition.addXAndY(0, -(configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
        } else {
            addLine(childPosition,
                    parentCentralPosition.addXAndY(0, -(configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
        }
    }

    private void addLineToChildren(AncestorPerson child, Position parent) {
        if (child.getSpouseCouple() != null && child.getSpouse() == null) {
            addLine(parent,
                    parent.addXAndY(0, (configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
            addHeraldry(parent.addXAndY(0, configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP), child.getBirthDatePlace().getSimplePlace());
        } else if (child.getSpouseCouple() != null && !child.getSpouseCouple().getChildren().isEmpty()) {
            addLine(parent.addXAndY(configuration.getSpouseDistance() / 2, 0),
                    parent.addXAndY(configuration.getSpouseDistance() / 2, (configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
            addHeraldry(parent.addXAndY(configuration.getSpouseDistance() / 2,
                    configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP), child.getBirthDatePlace().getSimplePlace()
            );
        }
    }

    private int calculateCouplesCount(List<AncestorCouple> spouseCouples) {
        return (int) spouseCouples.stream()
                .map(spouseCouple -> spouseCouple.getDescendentTreeInfo())
                .collect(Collectors.summarizingInt(DescendentTreeInfo::getMaxCouplesCount))
                .getSum();
    }

    private int calculateSinglesCount(List<AncestorCouple> spouseCouples) {
        return (int) spouseCouples.stream()
                .map(spouseCouple -> spouseCouple.getDescendentTreeInfo())
                .collect(Collectors.summarizingInt(DescendentTreeInfo::getMaxSinglesCount))
                .getSum();
    }

}
