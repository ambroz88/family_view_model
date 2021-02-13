package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class VerticalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    private final int parentsWidth;

    public VerticalAncestorService(ConfigurationService configuration) {
        super(configuration);
        parentsWidth = configuration.getCoupleWidth() + (int) (configuration.getAdultImageWidth() / 3.0);
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = childPosition.addXAndY(
                getConfiguration().getMarriageLabelWidth(), -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);
        int labelYShift = 0;
        if (configuration.getAdultDiagram() != Diagrams.SCROLL) {
            labelYShift = Spaces.LABEL_GAP;
        }

        Position label = new Position(childPosition.getX(), motherPosition.getY()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - getConfiguration().getMarriageLabelHeight() + labelYShift);

        addLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
//        addPerson(motherPosition, mother);
        addPerson(motherPosition.addXAndY(0, 3 * labelYShift), mother);
        return motherPosition;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        int fatherYShift = -getConfiguration().getAdultImageHeightAlternative()
                - getConfiguration().getAdultImageHeight()
                - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = childPosition.addXAndY(0, fatherYShift);
        addPerson(fatherPosition, father);
        return fatherPosition;
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

            int parentY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;

            if (child.getFather() == null) {
                Position motherPosition = childPosition.addXAndY(0, -configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), new Position());
                addLine(motherPosition, childPosition, Relation.DIRECT);
            } else {
                Position shiftPosition = new Position(-configuration.getMarriageLabelWidth(),
                        configuration.getAdultImageHeightAlternative() + configuration.getMarriageLabelHeight());
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

                Position motherPosition = new Position(motherX, parentY);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), shiftPosition);

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
                motherX = calculateFatherX(child.getMother(), childX);
                motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                        - configuration.getAdultImageHeightAlternative() - configuration.getMarriageLabelHeight();

                Position motherPosition = new Position(motherX, motherY);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother(), new Position());

                addLine(motherPosition, childPosition, Relation.DIRECT);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(new Position(motherX, childY - heraldryShiftY), child.getBirthDatePlace().getSimplePlace());
                }
            } else {
                int verticalFemaleShift = configuration.getAdultImageHeightAlternative() + configuration.getMarriageLabelHeight();
                if (child.getSex().equals(Sex.FEMALE)) {
                    fatherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - 2 * verticalFemaleShift + femaleShift.getY();
                    motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - verticalFemaleShift + femaleShift.getY();

                    fatherX = calculateFatherX(child.getFather(), childX) + femaleShift.getX();
                    motherX = fatherX + configuration.getMarriageLabelWidth();
                    heraldryShiftY = verticalFemaleShift;
                } else {
                    fatherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - verticalFemaleShift;
                    motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;

                    motherX = calculateMotherX(child.getMother(), childX);
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
    public void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (int) (getConfiguration().getAdultImageWidth() / 2 + getConfiguration().getAdultImageWidth() * 0.25);
        }
        Position spousePosition = new Position(rootSibling.getX() + spouseGap, rootSibling.getY());

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            int lineY = rootSibling.getY() - (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
            addLine(new Position(rootSibling.getX(), lineY),
                    new Position(spousePosition.getX(), lineY),
                    Relation.SIDE);
        }

        addOlderSiblings(rootSibling, rootChild);
        addYoungerSiblings(spousePosition, rootChild);
    }

    @Override
    public void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap = lastSpouseX - rootSibling.getX();

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);
        }

        addOlderSiblings(rootSibling, rootChild);
        addYoungerSiblings(new Position(lastSpouseX, rootSibling.getY()), rootChild);
    }

    @Override
    public void addVerticalLineToParents(Position child) {
        int endY = child.getY() - getConfiguration().getAdultImageHeight()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        addLine(child, new Position(child.getX(), endY), Relation.DIRECT);
    }
}
