package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class HorizontalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public HorizontalAncestorService(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = childPosition.addX(getConfiguration().getHalfSpouseLabelSpace());
        motherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position label = new Position(
                childPosition.getX() - getConfiguration().getMarriageLabelWidth() / 2,
                motherPosition.getY() - getConfiguration().getMarriageLabelHeight() / 2
        );

        drawLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        drawPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        Position fatherPosition = childPosition.addX(-getConfiguration().getHalfSpouseLabelSpace());
        fatherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        drawPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public Position addSpouse(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spouse = rootPersonPosition.addX(getConfiguration().getMarriageLabelWidth() + getConfiguration().getAdultImageWidth());

            Position label = new Position(
                    rootPersonPosition.getX() + getConfiguration().getAdultImageWidth() / 2,
                    rootPersonPosition.getY() - getConfiguration().getMarriageLabelHeight() / 2
            );

            drawPerson(spouse, person.getSpouse());
            drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple().getMarriageDate());
            return spouse;
        }
        return rootPersonPosition;
    }

    @Override
    public Position addAllSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseDistance = getConfiguration().getMarriageLabelWidth() + getConfiguration().getAdultImageWidth();
            Position spousePosition = new Position(rootPersonPosition);

            Position label = rootPersonPosition.addX(-getConfiguration().getAdultImageWidth() / 2 - getConfiguration().getMarriageLabelWidth());
            label.addY(-getConfiguration().getMarriageLabelHeight() / 2);

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                spousePosition = spousePosition.addX(spouseDistance);
                label = label.addX(spouseDistance);
                drawPerson(spousePosition, person.getSpouse(index));
                drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple(index).getMarriageDate());
            }

            return spousePosition;
        }
        return rootPersonPosition;
    }

    @Override
    public int addChildren(Position fatherPosition, Couple spouseCouple) {
        int childrenX = fatherPosition.getX() + getConfiguration().getHalfSpouseLabelSpace();
        int fatherY = fatherPosition.getY();

        int childrenWidth = 0;
        if (spouseCouple != null) {
            int childrenCount = spouseCouple.getChildren().size();
            if (getConfiguration().isShowChildren() && childrenCount > 0) {
                int childrenLineY = fatherY + (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
                Position lineLevel = new Position(childrenX, childrenLineY);
                drawLine(lineLevel, new Position(childrenX, fatherY), Line.SIBLINGS);

                int childrenY = childrenLineY + (getConfiguration().getSiblingImageHeight() + Spaces.VERTICAL_GAP) / 2;
                childrenWidth = childrenCount * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) - Spaces.HORIZONTAL_GAP;
                int startXPosition = childrenX + getConfiguration().getSiblingImageWidth() / 2 - childrenWidth / 2;

                Position childrenPosition = new Position(startXPosition, childrenY);

                for (int i = 0; i < childrenCount; i++) {
                    int childXPosition = startXPosition + i * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP);
                    if (i == 0 && childrenCount > 1) {
                        addRoundChildrenLine(childXPosition, childrenY, childrenX);
                    } else if (i == childrenCount - 1) {
                        addRoundChildrenLine(childXPosition, childrenY, childrenX);
                    } else {
                        addStraightChildrenLine(childXPosition, childrenY);
                    }
                    //TODO: draw spouse of the children
                    drawPerson(childrenPosition, spouseCouple.getChildren().get(i));
                    childrenPosition = childrenPosition.addX(getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP);
                }
                childrenWidth = childrenWidth / 2;

                if (getConfiguration().isShowHeraldry()) {
                    addChildrenHeraldry(new Position(childrenX, childrenY), spouseCouple);
                }
            }
        }
        return childrenWidth;
    }

    @Override
    public void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = getConfiguration().getSpouseLabelSpace();
        }
        Position spousePosition = new Position(rootSibling.getX() + spouseGap, rootSibling.getY());

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);

//            int lineY = rootSibling.getY() - (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
//            drawLine(new Position(rootSibling.getX(), lineY),
//                    new Position(spousePosition.getX(), lineY),
//                    Line.SIBLINGS);
        }

        drawOlderSiblings(rootSibling, rootChild);
        drawYoungerSiblings(spousePosition, rootChild);
    }

    @Override
    public void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap;
        if (!rootChild.getSpouseID().isEmpty() && lastSpouseX == 0) {
            spouseGap = rootSibling.getX() + getConfiguration().getSpouseLabelSpace() * rootChild.getSpouseCouples().size();
        } else {
            spouseGap = lastSpouseX;
        }

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);
        }

        drawOlderSiblings(rootSibling, rootChild);
        drawYoungerSiblings(new Position(spouseGap, rootSibling.getY()), rootChild);
    }

    @Override
    public void addVerticalLineToParents(Position child) {
        int endY = child.getY() - getConfiguration().getAdultImageHeight()
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        drawLine(child, new Position(child.getX(), endY), Line.LINEAGE);
    }
}
