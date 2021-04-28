package org.ambrogenea.familyview.service.impl;

import java.util.List;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
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
        int fathersSiblings = rootPerson.getFather().getMaxYoungerSiblings();
        int mothersSiblings = rootPerson.getMother().getMaxOlderSiblings();
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
    public Position generateAllDescendents(Position rootPosition, List<AncestorCouple> couples) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position addCoupleFamily(Position firstChildPosition, AncestorCouple couple, int descendentsWidth) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
