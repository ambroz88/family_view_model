package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class HorizontalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public HorizontalAncestorService(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Position drawMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = new Position(childPosition);
        motherPosition.addX(getConfiguration().getHalfSpouseLabelSpace());
        motherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position label = new Position(
                childPosition.getX() - getConfiguration().getHalfSpouseLabelSpace(),
                motherPosition.getY() - getConfiguration().getMarriageLabelHeight() / 2
        );

        drawLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        drawPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position drawFather(Position childPosition, AncestorPerson father) {
        Position fatherPosition = new Position(childPosition);
        fatherPosition.addX(-getConfiguration().getHalfSpouseLabelSpace());
        fatherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        drawPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public Position drawSpouse(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spouse = new Position(rootPersonPosition);
            spouse.addX(getConfiguration().getMarriageLabelWidth() + getConfiguration().getAdultImageWidth());

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
    public Position drawAllSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseDistance = getConfiguration().getMarriageLabelWidth() + getConfiguration().getAdultImageWidth();
            Position spousePosition = new Position(rootPersonPosition);

            Position label = new Position(rootPersonPosition.getX() - getConfiguration().getHalfSpouseLabelSpace(),
                    rootPersonPosition.getY() - getConfiguration().getMarriageLabelHeight() / 2);

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                spousePosition.addX(spouseDistance);
                label.addX(spouseDistance);
                drawPerson(spousePosition, person.getSpouse(index));
                drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple(index).getMarriageDate());
            }

            return spousePosition;
        }
        return rootPersonPosition;
    }

    @Override
    public void drawSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
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
    public void drawSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
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
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        drawLine(child, new Position(child.getX(), endY), Line.LINEAGE);
    }
}
