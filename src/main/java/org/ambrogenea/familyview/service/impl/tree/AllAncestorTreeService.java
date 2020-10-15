package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.SpecificAncestorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalAncestorService;
import org.ambrogenea.familyview.service.impl.VerticalAncestorService;

public class AllAncestorTreeService implements TreeService {

    private SpecificAncestorService specificAncestorService;
    private Configuration configuration;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, Position rootPosition, Configuration configuration) {
        this.configuration = configuration;
        if (configuration.isShowCouplesVertical()) {
            specificAncestorService = new VerticalAncestorService(configuration);
        } else {
            specificAncestorService = new HorizontalAncestorService(configuration);
        }

        drawFirstParents(rootPerson, rootPosition);
//        drawFirstParentsVertical(rootPerson, rootPosition);
        return specificAncestorService.getTreeModel();
    }

    private void drawFirstParentsVertical(AncestorPerson rootPerson, Position child) {
        if (rootPerson.getMother() != null) {

            specificAncestorService.addVerticalLineToParents(child);
            if (configuration.isShowHeraldry()) {
                specificAncestorService.addHeraldry(child, rootPerson.getSimpleBirthPlace());
            }

            if (rootPerson.getFather() != null) {
                Position fatherPosition = specificAncestorService.addFather(child, rootPerson.getFather());
                drawParents(rootPerson.getFather(), fatherPosition);
            }

            Position motherPosition = specificAncestorService.addMother(child, rootPerson.getMother(), rootPerson.getParents().getMarriageDate());
            drawParents(rootPerson.getMother(), motherPosition);
        }

        specificAncestorService.drawPerson(child, rootPerson);

        if (configuration.isShowSpouses()) {
            specificAncestorService.addSpouse(child, rootPerson);
            if (configuration.isShowChildren()) {
                specificAncestorService.generateChildren(child, rootPerson.getSpouseCouple());
            }
        }

    }

    private void drawFirstParents(AncestorPerson rootPerson, Position child) {
        if (rootPerson.getMother() != null) {
            int parentsY = child.getY() - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;

            if (configuration.isShowHeraldry()) {
                specificAncestorService.addHeraldry(child, rootPerson.getSimpleBirthPlace());
            }

            int shiftX = configuration.getAdultImageWidth() / 4;
            double motherParentsCount = Math.min(rootPerson.getFather().getInnerParentsCount(), rootPerson.getFather().getLastParentsCount());
            double fatherParentsCount = Math.min(rootPerson.getMother().getInnerParentsCount(), rootPerson.getMother().getLastParentsCount());
            int fatherX = child.getX() - configuration.getAdultImageWidth() + shiftX
                    - (int) ((configuration.getCoupleWidthVertical() + shiftX) * fatherParentsCount);
            int motherX = child.getX() + configuration.getAdultImageWidth() - shiftX
                    + (int) ((configuration.getCoupleWidthVertical() + shiftX) * motherParentsCount);

            if (rootPerson.getFather() != null) {
                Position fatherPosition = new Position(fatherX, parentsY);
                specificAncestorService.drawPerson(fatherPosition, rootPerson.getFather());
                drawGrandParent(rootPerson.getFather(), fatherPosition);
            }

            Position motherPosition = new Position(motherX, parentsY);
            specificAncestorService.drawPerson(motherPosition, rootPerson.getMother());
            drawGrandParent(rootPerson.getMother(), motherPosition);

            int halfAdult = configuration.getAdultImageWidth() / 2;
            int labelWidth = motherX - fatherX - configuration.getAdultImageWidth();
            specificAncestorService.drawLabel(new Position(fatherX + halfAdult, parentsY - configuration.getMarriageLabelHeight() / 2), labelWidth, rootPerson.getParents().getMarriageDate());

            int newChildX = (fatherX + motherX) / 2;
            Position newChild = new Position(newChildX, child.getY());
            specificAncestorService.drawPerson(newChild, rootPerson);
            specificAncestorService.drawLine(new Position(newChildX, parentsY), newChild, Line.LINEAGE);

            if (configuration.isShowSpouses()) {
                specificAncestorService.addSpouse(newChild, rootPerson);

                if (configuration.isShowChildren()) {
                    specificAncestorService.addChildren(newChild, rootPerson.getSpouseCouple());
                }
            }
        }

    }

    private void drawGrandParent(AncestorPerson child, Position childPosition) {
        if (child.getMother() != null) {
            if (child.getFather() == null) {
                Position motherPosition = new Position(childPosition);
                motherPosition.addY(-configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP);
                specificAncestorService.drawPerson(motherPosition, child.getMother());
                drawParents(child.getMother(), motherPosition);
                specificAncestorService.drawLine(motherPosition, childPosition, Line.LINEAGE);
            } else {
                Position fatherPosition = specificAncestorService.addFather(childPosition, child.getFather());
                drawParents(child.getFather(), fatherPosition);

                Position motherPosition = specificAncestorService.addMother(childPosition, child.getMother(), child.getParents().getMarriageDate());
                drawParents(child.getMother(), motherPosition);

                fatherPosition.addY(configuration.getAdultImageHeightAlternative() + configuration.getMarriageLabelHeight() / 2);
                specificAncestorService.drawLine(fatherPosition, childPosition, Line.LINEAGE);

                if (configuration.isShowHeraldry()) {
                    specificAncestorService.addHeraldry(childPosition, child.getSimpleBirthPlace());
                }
            }
        }
    }

    private void drawParents(AncestorPerson child, Position childPosition) {
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
                                + (int) ((configuration.getCoupleWidthVertical() - shiftX) * fatherParentsCount);
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
                                - (int) ((configuration.getCoupleWidthVertical() - shiftX) * motherParentsCount);
                    } else {
                        motherParentsCount = (child.getMother().getAncestorGenerations());
                        motherX = childX - (int) ((configuration.getCoupleWidthVertical() - shiftX) * motherParentsCount);
                    }
                    fatherX = motherX - configuration.getMarriageLabelWidth();
                }

                int labelY = (motherY + fatherY) / 2;

                Position fatherPosition = new Position(fatherX, fatherY);
                specificAncestorService.drawPerson(fatherPosition, child.getFather());
                drawParents(child.getFather(), fatherPosition);

                Position motherPosition = new Position(motherX, motherY);
                specificAncestorService.drawPerson(motherPosition, child.getMother());
                drawParents(child.getMother(), motherPosition);

                specificAncestorService.drawLabel(new Position(fatherX, labelY - configuration.getMarriageLabelHeight() / 2),
                        configuration.getMarriageLabelWidth(), child.getParents().getMarriageDate());
                specificAncestorService.drawLine(new Position(fatherX, labelY), childPosition, Line.LINEAGE);

                if (configuration.isShowHeraldry()) {
                    specificAncestorService.addHeraldry(new Position(fatherX, childY), child.getSimpleBirthPlace());
                }
            }

        }
    }

}
