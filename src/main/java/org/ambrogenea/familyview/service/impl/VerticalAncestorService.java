package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class VerticalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public VerticalAncestorService(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Position drawMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = new Position(childPosition);
        motherPosition.addX(getConfiguration().getMarriageLabelWidth());
        motherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position label = new Position(childPosition.getX(), motherPosition.getY()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - getConfiguration().getMarriageLabelHeight());

        drawLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        drawPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position drawFather(Position childPosition, AncestorPerson father) {
        int fatherY = childPosition.getY() - getConfiguration().getAdultImageHeightAlternative()
                - getConfiguration().getAdultImageHeight()
                - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = new Position(childPosition.getX(), fatherY);
        drawPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public Position drawSpouse(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spouse = new Position(rootPersonPosition);
            spouse.addX(getConfiguration().getMarriageLabelWidth());
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
    public Position drawAllSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseDistance = getConfiguration().getAdultImageWidth()
                    + getConfiguration().getMarriageLabelWidth() / 3 + Spaces.SIBLINGS_GAP;

            Position spousePosition = new Position(rootPersonPosition);
            spousePosition.addX(getConfiguration().getMarriageLabelWidth());
            spousePosition.addY(getConfiguration().getCoupleVerticalDifference());

            Position label = new Position(rootPersonPosition.getX(),
                    rootPersonPosition.getY() + getConfiguration().getAdultImageHeightAlternative() / 2);

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                drawPerson(spousePosition, person.getSpouse(index));
                drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple(index).getMarriageDate());

                label.addX(spouseDistance);
                spousePosition.addX(spouseDistance);
            }
            spousePosition.addX(-spouseDistance);
            return spousePosition;
        }
        return rootPersonPosition;
    }

    @Override
    public void drawSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
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
    public void drawSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
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
