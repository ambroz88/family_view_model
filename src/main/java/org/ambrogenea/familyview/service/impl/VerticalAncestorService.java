package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class VerticalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public VerticalAncestorService(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = childPosition.addX(getConfiguration().getMarriageLabelWidth());
        motherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

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
    public Position addSpouse(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spouse = rootPersonPosition.addX(getConfiguration().getMarriageLabelWidth());
            spouse.addY(getConfiguration().getMarriageLabelHeight() + getConfiguration().getAdultImageHeightAlternative());

            Position label = new Position(rootPersonPosition.getX(),
                    rootPersonPosition.getY() + getConfiguration().getAdultImageHeightAlternative() / 2);

            drawPerson(spouse, person.getSpouse());
            drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple().getMarriageDate());
            return spouse;
        }
        return rootPersonPosition;
    }

    @Override
    public Position addAllSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseDistance = getConfiguration().getAdultImageWidth()
                    + getConfiguration().getMarriageLabelWidth() / 3 + Spaces.SIBLINGS_GAP;

            Position spousePosition = rootPersonPosition.addX(getConfiguration().getMarriageLabelWidth());
            spousePosition.addY(getConfiguration().getCoupleVerticalDifference());

            Position label = new Position(rootPersonPosition.getX(),
                    rootPersonPosition.getY() + getConfiguration().getAdultImageHeightAlternative() / 2);

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                drawPerson(spousePosition, person.getSpouse(index));
                drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple(index).getMarriageDate());

                label = label.addX(spouseDistance);
                spousePosition = spousePosition.addX(spouseDistance);
            }

            return spousePosition.addX(-spouseDistance);
        }
        return rootPersonPosition;
    }

    @Override
    public int addChildren(Position fatherPosition, Couple spouseCouple) {
        int childrenX = fatherPosition.getX();
        int fatherY = fatherPosition.getY();

        int childrenWidth = 0;
        if (spouseCouple != null) {
            int childrenCount = spouseCouple.getChildren().size();
            if (getConfiguration().isShowChildren() && childrenCount > 0) {
                int childrenLineY = fatherY + getConfiguration().getAdultImageHeightAlternative() + getConfiguration().getMarriageLabelHeight()
                        + (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
                Position lineLevel = new Position(fatherPosition.getX(), childrenLineY);
                int labelY = fatherY + (getConfiguration().getAdultImageHeightAlternative() + getConfiguration().getMarriageLabelHeight()) / 2;
                drawLine(lineLevel, new Position(fatherPosition.getX(), labelY), Line.SIBLINGS);

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
        int spouseGap;
        if (!rootChild.getSpouseID().isEmpty() && lastSpouseX == 0) {
            spouseGap = rootSibling.getX() + (getConfiguration().getAdultImageWidth() + getConfiguration().getMarriageLabelWidth()) * rootChild.getSpouseCouples().size();
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
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        drawLine(child, new Position(child.getX(), endY), Line.LINEAGE);
    }
}
