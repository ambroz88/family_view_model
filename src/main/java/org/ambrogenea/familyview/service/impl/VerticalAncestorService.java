package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class VerticalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public VerticalAncestorService(ConfigurationService configuration) {
        super(configuration);
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = childPosition.addXAndY(
                getConfiguration().getMarriageLabelWidth(), -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position label = new Position(childPosition.getX(), motherPosition.getY()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - getConfiguration().getMarriageLabelHeight());

        addLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        addPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        int fatherY = childPosition.getY() - getConfiguration().getAdultImageHeightAlternative()
                - getConfiguration().getAdultImageHeight()
                - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = new Position(childPosition.getX(), fatherY);
        addPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public void addFirstParents(Position childPosition, AncestorPerson child) {
        Position coupleCenterPosition = addParents(childPosition, child);

        Position fatherPosition = coupleCenterPosition.addXAndY(-configuration.getHalfSpouseLabelSpace() - 3, 0);
        addGrandParents(fatherPosition, child.getFather());

        Position motherPosition = coupleCenterPosition.addXAndY(configuration.getHalfSpouseLabelSpace() + 3, 0);
        addGrandParents(motherPosition, child.getMother());

        Position linePosition = new Position(childPosition.getX(), fatherPosition.getY());
        addLine(childPosition, linePosition, Relation.DIRECT);

        if (configuration.isShowHeraldry()) {
            addHeraldry(childPosition, child.getSimpleBirthPlace());
        }
    }

    @Override
    public void addGrandParents(Position childPosition, AncestorPerson child) {
        addAllParents(childPosition, child);
//        if (child.getMother() != null) {
//            if (child.getFather() == null) {
//                Position motherPosition = childPosition.addXAndY(0, -configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP);
//                addPerson(motherPosition, child.getMother());
//                addAllParents(motherPosition, child.getMother());
//                addLine(motherPosition, childPosition, Relation.DIRECT);
//            } else {
//                Position coupleCenterPosition = addParents(childPosition, child);
//
//                Position fatherPosition = coupleCenterPosition.addXAndY(-configuration.getHalfSpouseLabelSpace(), 0);
//                addAllParents(fatherPosition, child.getFather());
//
//                Position motherPosition = coupleCenterPosition.addXAndY(configuration.getHalfSpouseLabelSpace(), 0);
//                addAllParents(motherPosition, child.getMother());
//
//                Position linePosition = new Position(childPosition.getX(), fatherPosition.getY());
//                addLine(linePosition, childPosition, Relation.DIRECT);
//
//                if (configuration.isShowHeraldry()) {
//                    addHeraldry(childPosition, child.getSimpleBirthPlace());
//                }
//            }
//        }
    }

    @Override
    public void addAllParents(Position childPosition, AncestorPerson child) {
        if (child.getMother() != null) {
            int fatherY;
            int motherY;
            int fatherX;
            int motherX;
            int childX = childPosition.getX();
            int childY = childPosition.getY();

            if (child.getFather() == null) {
                //TODO: generate mother only
//                motherX = childX;
            } else {
                int shiftX = configuration.getAdultImageWidth() / 4;

                if (child.getSex().equals(Sex.FEMALE)) {
                    fatherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - 2 * configuration.getAdultImageHeightAlternative() - 2 * configuration.getMarriageLabelHeight();
                    motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - configuration.getAdultImageHeightAlternative() - configuration.getMarriageLabelHeight();

                    double fatherParentsCount;
                    if (child.getFather().getAncestorGenerations() > 1) {
                        fatherParentsCount = child.getFather().getLastParentsCount();
                        fatherX = childX + configuration.getAdultImageWidth() - shiftX
                                + (int) ((configuration.getCoupleWidth() - shiftX) * fatherParentsCount);
                    } else {
                        fatherParentsCount = (child.getFather().getAncestorGenerations());
                        fatherX = childX + configuration.getAdultImageWidth() - shiftX
                                + (int) ((configuration.getAdultImageWidth() * 0.75) * fatherParentsCount);
                    }
                    motherX = fatherX + configuration.getMarriageLabelWidth();
                } else {
                    fatherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - configuration.getAdultImageHeightAlternative() - configuration.getMarriageLabelHeight();
                    motherY = childY - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;

                    double motherParentsCount;
                    if (child.getMother().getAncestorGenerations() > 1) {
                        motherParentsCount = child.getMother().getLastParentsCount();
                        motherX = childX - configuration.getAdultImageWidth() + shiftX
                                - (int) ((configuration.getCoupleWidth() - shiftX) * motherParentsCount);
                    } else {
                        motherParentsCount = (child.getMother().getAncestorGenerations());
                        motherX = childX - (int) ((configuration.getCoupleWidth() - shiftX) * motherParentsCount);
                    }
                    fatherX = motherX - configuration.getMarriageLabelWidth();
                }

                int labelY = (motherY + fatherY) / 2;

                Position fatherPosition = new Position(fatherX, fatherY);
                addPerson(fatherPosition, child.getFather());
                addAllParents(fatherPosition, child.getFather());

                Position motherPosition = new Position(motherX, motherY);
                addPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother());

                addLabel(new Position(fatherX, labelY - configuration.getMarriageLabelHeight() / 2),
                        configuration.getMarriageLabelWidth(), child.getParents().getMarriageDate());
                addLine(new Position(fatherX, labelY), childPosition, Relation.DIRECT);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(new Position(fatherX, childY), child.getSimpleBirthPlace());
                }
            }

        }
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
