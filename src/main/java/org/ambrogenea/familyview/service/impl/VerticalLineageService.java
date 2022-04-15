package org.ambrogenea.familyview.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.DescendentTreeInfo;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;

public class VerticalLineageService extends VerticalAncestorService implements LineageService {

    private final int parentsWidth;

    public VerticalLineageService(ConfigurationService configuration) {
        super(configuration);
        parentsWidth = configuration.getCoupleWidth() + (int) (configuration.getAdultImageWidth() / 3.0);
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
                Position motherPosition = addFather(child, person.getMother());
                addLine(child, motherPosition, Relation.DIRECT);
                generateFathersFamily(motherPosition, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    addSiblingsAroundMother(motherPosition, person.getMother());
                }
            }

        }
    }

    @Override
    public void generateMotherFamily(Position childPosition, AncestorPerson person) {
        addVerticalLineToParents(childPosition);
        Position motherPosition = addFather(childPosition, person.getMother());

        if (person.getFather() != null) {
            addMother(childPosition, person.getFather(),
                    person.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale()));
        }

        if (configuration.isShowSiblings()) {
            addSiblingsAroundMother(motherPosition, person.getMother());
        }

        if (configuration.isShowHeraldry()) {
            addHeraldry(childPosition, person.getBirthDatePlace().getSimplePlace());
        }

        generateFathersFamily(motherPosition, person.getMother());
    }

    @Override
    public Position calculateMotherPosition(Position fatherPosition, AncestorPerson rootPerson) {
        int fathersSiblings = rootPerson.getFather().getFather().getMaxYoungerSiblings();
        int mothersSiblings = rootPerson.getMother().getFather().getMaxOlderSiblings();
        int siblingsAmount = fathersSiblings + mothersSiblings;
        int siblingsWidth = siblingsAmount * (configuration.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP)
                + 2 * Spaces.SIBLINGS_GAP + configuration.getCoupleWidth() / 2;
        return fatherPosition.addXAndY(
                configuration.getAdultImageWidth() + Math.max(siblingsWidth, configuration.getWideMarriageLabel()),
                0);
    }

    @Override
    public void addFirstParents(Position childPosition, AncestorPerson child) {
        Position coupleCenterPosition = addParents(childPosition, child);

        Position fatherPosition = coupleCenterPosition.addXAndY(-configuration.getHalfSpouseLabelSpace() - 3, 0);
        addParentsFromHorizontal(fatherPosition, child.getFather());

        Position motherPosition = coupleCenterPosition.addXAndY(configuration.getHalfSpouseLabelSpace() + 3, 0);
        addParentsFromHorizontal(motherPosition, child.getMother());

        Position linePosition = new Position(childPosition.getX(), fatherPosition.getY());
        addLine(childPosition, linePosition, Relation.DIRECT);

        if (configuration.isShowHeraldry()) {
            addHeraldry(childPosition, child.getBirthDatePlace().getSimplePlace());
        }
    }

    private void addParentsFromHorizontal(Position childPosition, AncestorPerson child) {
        if (child.getMother() != null) {
            int fatherX;
            int motherX;
            int childX = childPosition.getX();
            int childY = childPosition.getY();

            int parentY = childY - configuration.getAdultImageHeightAlternative() - Spaces.VERTICAL_GAP;

            if (child.getFather() == null) {
                double motherParentsCount = child.getMother().getInnerParentsCount();
                motherX = (int) (motherParentsCount * parentsWidth) - (int) (configuration.getAdultImageWidth() / 3.0 * 2);

                Position motherPosition = childPosition.addXAndY(-motherX + configuration.getCoupleWidth() / 2, -configuration.getAdultImageHeightAlternative() - Spaces.VERTICAL_GAP);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), new Position());
                addLine(motherPosition, childPosition, Relation.DIRECT);
            } else {
                Position shiftPosition = new Position(-configuration.getMarriageLabelWidth(), configuration.getAdultImageHeightAlternative());
                if (child.getSex().equals(Sex.FEMALE)) {
                    fatherX = calculateFatherXHorizontal(child, childX);
                    motherX = fatherX + configuration.getAdultImageWidth() + configuration.getMarriageLabelWidth();
                } else {
                    motherX = calculateMotherXHorizontal(child, childX);
                    fatherX = motherX - configuration.getAdultImageWidth() - configuration.getMarriageLabelWidth();
                }

                Position fatherPosition = new Position(fatherX, parentY);
                addPerson(fatherPosition, child.getFather());
                addAllParents(fatherPosition, child.getFather(), shiftPosition);

                Position shiftMotherPosition = new Position(-configuration.getMarriageLabelWidth(),
                        configuration.getAdultImageHeightAlternative() + configuration.getMarriageLabelHeight());
                Position motherPosition = new Position(motherX, parentY);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), shiftMotherPosition);

                Position linePosition = fatherPosition.addXAndY(
                        (configuration.getAdultImageWidth() + configuration.getMarriageLabelWidth()) / 2, 0);
                addLabel(fatherPosition.addXAndY(
                        configuration.getAdultImageWidth() / 2 + Spaces.LABEL_GAP,
                        -configuration.getMarriageLabelHeight() / 2),
                        configuration.getMarriageLabelWidth() - 2 * Spaces.LABEL_GAP,
                        child.getParents().getDatePlace().getLocalizedDate(configuration.getLocale()));
                addLine(linePosition, childPosition, Relation.DIRECT);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(new Position(linePosition.getX(), childPosition.getY()), child.getBirthDatePlace().getSimplePlace());
                }
            }
        }
    }

    private int calculateFatherXHorizontal(AncestorPerson child, int childX) {
        int fatherX;
        if (child.getFather().getAncestorGenerations() > 0) {

            double fatherParentsCount = child.getFather().getLastParentsCount();
            int generationsWidth = (int) (fatherParentsCount * parentsWidth) - (int) (configuration.getAdultImageWidth() / 3.0 * 2);
            fatherX = childX + generationsWidth - configuration.getMarriageLabelWidth();

        } else {
            fatherX = childX;
        }
        return fatherX;
    }

    private int calculateMotherXHorizontal(AncestorPerson child, int childX) {
        int motherX;
        if (child.getMother().getAncestorGenerations() > 0) {

            double motherParentsCount = child.getMother().getLastParentsCount();
            int generationsWidth = (int) (motherParentsCount * parentsWidth) - (int) (configuration.getAdultImageWidth() / 3.0 * 2);
            motherX = childX - generationsWidth + configuration.getMarriageLabelWidth();

        } else {
            motherX = childX;
        }
        return motherX;
    }

    private void addAllParents(Position childPosition, AncestorPerson child, Position femaleShift) {
        if (child.getMother() != null) {
            int fatherY;
            int motherY;
            int fatherX;
            int motherX;
            int childX = childPosition.getX();
            int childY = childPosition.getY();
            int heraldryShiftY = 0;

            if (child.getFather() == null) {
                double motherParentsCount = child.getMother().getInnerParentsCount();
                motherX = (int) -(motherParentsCount * parentsWidth) + (int) (configuration.getAdultImageWidth() / 3.0 * 2);
                motherY = -configuration.getAdultImageHeight() - configuration.getAdultImageHeightAlternative() - configuration.getMarriageLabelHeight() - Spaces.VERTICAL_GAP;

                Position motherPosition = childPosition.addXAndY(motherX, motherY);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), new Position());

                addLine(motherPosition, childPosition, Relation.DIRECT);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(new Position(motherX, childY - heraldryShiftY), child.getBirthDatePlace().getSimplePlace());
                }
            } else {
                int verticalFemaleShift = configuration.getAdultImageHeightAlternative() + configuration.getMarriageLabelHeight();
                if (child.getSex().equals(Sex.FEMALE)) {
                    if (child.getSpouse() == null) {
                        fatherY = childY - configuration.getAdultImageHeightAlternative() - Spaces.VERTICAL_GAP
                                - verticalFemaleShift + femaleShift.getY();
                        motherY = childY - configuration.getAdultImageHeightAlternative() - Spaces.VERTICAL_GAP
                                + femaleShift.getY();
                        fatherX = childX - configuration.getMarriageLabelWidth();
                    } else {
                        fatherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                                - verticalFemaleShift - configuration.getAdultImageHeight() + femaleShift.getY();
                        motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                                - verticalFemaleShift + femaleShift.getY();
                        fatherX = calculateFatherX(child.getFather(), childX) + femaleShift.getX();
                    }
                    motherX = fatherX + configuration.getMarriageLabelWidth();
                    heraldryShiftY = verticalFemaleShift;
                } else {
                    fatherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - verticalFemaleShift;
                    motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;

                    if (child.getSpouse() == null) {
                        motherX = childX;
                    } else {
                        motherX = calculateMotherX(child.getMother(), childX);
                    }
                    fatherX = motherX - configuration.getMarriageLabelWidth();
                }

                int labelY = (motherY + fatherY) / 2;

                Position fatherPosition = new Position(fatherX, fatherY);
                addPerson(fatherPosition, child.getFather());
                addAllParents(fatherPosition, child.getFather(), new Position());

                Position motherPosition = new Position(motherX, motherY);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), new Position());

                addLabel(new Position(fatherX, labelY - configuration.getMarriageLabelHeight() / 2),
                        configuration.getMarriageLabelWidth(),
                        child.getParents().getDatePlace().getLocalizedDate(configuration.getLocale()));
                addLine(new Position(fatherX, labelY), childPosition, Relation.DIRECT);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(new Position(fatherX, childY - heraldryShiftY), child.getBirthDatePlace().getSimplePlace());
                }
            }

        }
    }

    private int calculateFatherX(AncestorPerson father, int childX) {
        int fatherX;
        if (father.getAncestorGenerations() > 0) {

            double fatherParentsCount = father.getLastParentsCount();
            int generationsWidth = (int) (fatherParentsCount * parentsWidth) - (int) (configuration.getAdultImageWidth() / 3.0 * 2);
            fatherX = childX + generationsWidth;

        } else {
            fatherX = childX + (int) (configuration.getAdultImageWidth() / 3.0 * 2);
        }
        return fatherX;
    }

    private int calculateMotherX(AncestorPerson mother, int childX) {
        int motherX;
        if (mother.getAncestorGenerations() > 0) {

            double motherParentsCount = mother.getLastParentsCount();
            int generationsWidth = (int) (motherParentsCount * parentsWidth) - (int) (configuration.getAdultImageWidth() / 3.0 * 2);
            motherX = childX - generationsWidth;

        } else {
            motherX = childX;
        }
        return motherX;
    }

    @Override
    public Position generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth) {
        int verticalShift = 2 * configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP + configuration.getMarriageLabelHeight();
        Position actualChildPosition = firstChildPosition.addXAndY(0, verticalShift);

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
    public Position addCoupleFamily(Position parentCentralPosition, AncestorCouple couple, int allDescendentsWidth) {
        Position actualChildPosition = parentCentralPosition.addXAndY(-allDescendentsWidth / 2, 0);
        List<AncestorPerson> children = couple.getChildren();
        for (int i = 0; i < children.size(); i++) {
            AncestorPerson child = children.get(i);

            int allChildrenCoupleCount = calculateCouplesCount(child.getSpouseCouples());
            int allChildrenSinglesCount = calculateSinglesCount(child.getSpouseCouples());
            int descendentsWidth = allChildrenCoupleCount * (configuration.getCoupleWidth() + Spaces.HORIZONTAL_GAP)
                    + allChildrenSinglesCount * (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);
            int childWidth = child.getSpouseCount() * configuration.getCoupleWidth();

            boolean widerParents = false;
            if (descendentsWidth < childWidth) {
                actualChildPosition = actualChildPosition.addXAndY((childWidth - descendentsWidth) / 2, 0);
                widerParents = true;
            }

            Position endGenerationPosition = generateAllDescendents(actualChildPosition, child.getSpouseCouples(), descendentsWidth);
            int centerX = (endGenerationPosition.getX() - actualChildPosition.getX()) / 2;
            Position parentPosition;
            if (child.getSpouse() != null || widerParents) {
                parentPosition = actualChildPosition.addXAndY(centerX - configuration.getSpouseDistance() / 2, 0);
            } else {
                parentPosition = actualChildPosition.addXAndY(centerX, 0);
            }

            addPerson(parentPosition, child);
            Position spousePosition = addSpouse(parentPosition, child);

            addLineToChildren(child, parentPosition);
            addLineFromChildren(children.size(), i, parentPosition, parentCentralPosition);

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

    private void addLineToChildren(AncestorPerson child, Position parentPosition) {
        if (child.getSpouseCouple() != null && child.getSpouse() == null) {
            addLine(parentPosition,
                    parentPosition.addXAndY(0, (configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2
                            + configuration.getAdultImageHeightAlternative() + configuration.getMarriageLabelHeight()
                    ),
                    Relation.DIRECT
            );
            addHeraldry(parentPosition.addXAndY(0,
                    configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP + configuration.getMarriageLabelHeight()), child.getBirthDatePlace().getSimplePlace());
        } else if (child.getSpouseCouple() != null && !child.getSpouseCouple().getChildren().isEmpty()) {
            Position labelPosition = parentPosition.addXAndY(0, configuration.getAdultImageHeightAlternative() / 2 + configuration.getMarriageLabelHeight() / 2);
            addLine(labelPosition,
                    labelPosition.addXAndY(0, (2 * configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP + configuration.getMarriageLabelHeight()) / 2),
                    Relation.DIRECT
            );
            addHeraldry(labelPosition.addXAndY(0,
                    configuration.getAdultImageHeightAlternative() + (Spaces.VERTICAL_GAP + configuration.getMarriageLabelHeight()) / 2), child.getBirthDatePlace().getSimplePlace()
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
