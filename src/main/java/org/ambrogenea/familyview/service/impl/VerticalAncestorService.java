package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.model.AncestorPerson;
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

        drawLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        drawPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        int fatherY = childPosition.getY() - getConfiguration().getAdultImageHeightAlternative()
            - getConfiguration().getAdultImageHeight()
            - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = new Position(childPosition.getX(), fatherY);
        drawPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public void addGrandParents(AncestorPerson child, Position childPosition) {
        if (child.getMother() != null) {
            if (child.getFather() == null) {
                Position motherPosition = childPosition.addXAndY(0,-configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP);
                drawPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother());
                drawLine(motherPosition, childPosition, Line.LINEAGE);
            } else {
                Position coupleCenterPosition = addParents(childPosition,child);

                Position fatherPosition = coupleCenterPosition.addXAndY(-configuration.getHalfSpouseLabelSpace(),0);
                addAllParents(fatherPosition, child.getFather());

                Position motherPosition = coupleCenterPosition.addXAndY(configuration.getHalfSpouseLabelSpace(),0);
                addAllParents(motherPosition, child.getMother());

                Position linePosition = new Position(childPosition.getX(),fatherPosition.getY());
                drawLine(linePosition, childPosition, Line.LINEAGE);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(childPosition, child.getSimpleBirthPlace());
                }
            }
        }
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
                drawPerson(fatherPosition, child.getFather());
                addAllParents(fatherPosition, child.getFather());

                Position motherPosition = new Position(motherX, motherY);
                drawPerson(motherPosition, child.getMother());
                addAllParents(motherPosition, child.getMother());

                drawLabel(new Position(fatherX, labelY - configuration.getMarriageLabelHeight() / 2),
                    configuration.getMarriageLabelWidth(), child.getParents().getMarriageDate());
                drawLine(new Position(fatherX, labelY), childPosition, Line.LINEAGE);

                if (configuration.isShowHeraldry()) {
                    addHeraldry(new Position(fatherX, childY), child.getSimpleBirthPlace());
                }
            }

        }
    }

    @Override
    public Position addSpouse(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spouse = rootPersonPosition.addXAndY(getConfiguration().getMarriageLabelWidth(),
                getConfiguration().getMarriageLabelHeight() + getConfiguration().getAdultImageHeightAlternative());

            Position label = rootPersonPosition.addXAndY(0, getConfiguration().getAdultImageHeightAlternative() / 2);

            drawPerson(spouse, person.getSpouse());
            drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple().getMarriageDate());
            return spouse;
        }
        return rootPersonPosition;
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
            drawLine(new Position(rootSibling.getX(), lineY),
                new Position(spousePosition.getX(), lineY),
                Line.SIBLINGS);
        }

        drawOlderSiblings(rootSibling, rootChild);
        drawYoungerSiblings(spousePosition, rootChild);
    }

    @Override
    public void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap = lastSpouseX - rootSibling.getX();

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);
        }

        drawOlderSiblings(rootSibling, rootChild);
        drawYoungerSiblings(new Position(lastSpouseX, rootSibling.getY()), rootChild);
    }

    @Override
    public void addVerticalLineToParents(Position child) {
        int endY = child.getY() - getConfiguration().getAdultImageHeight()
            - getConfiguration().getAdultImageHeightAlternative() / 2
            - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        drawLine(child, new Position(child.getX(), endY), Line.LINEAGE);
    }
}
