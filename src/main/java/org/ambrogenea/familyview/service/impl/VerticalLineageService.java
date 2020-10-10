package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.LineageService;

public class VerticalLineageService extends VerticalAncestorService implements LineageService {

    public VerticalLineageService(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void drawSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson) {
        if (getConfiguration().isShowSpouses()) {
            Position lastSpouse = drawAllSpouses(rootPersonPosition, rootPerson);
            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundWives(rootPersonPosition, rootPerson, lastSpouse.getX());
            }
        } else if (getConfiguration().isShowSiblings()) {
            drawSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public void drawFathersFamily(Position child, AncestorPerson person) {
        if (person.getMother() != null) {

            addVerticalLineToParents(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, person.getSimpleBirthPlace());
            }

            if (person.getFather() != null) {
                drawMother(child, person.getMother(), person.getParents().getMarriageDate());

                Position fatherPosition = drawFather(child, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(fatherPosition, person.getFather());
                }

                drawFathersFamily(fatherPosition, person.getFather());
            } else {
                Position motherPosition = drawFather(child, person.getMother());
                drawLine(child, motherPosition, Line.LINEAGE);
                drawFathersFamily(motherPosition, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(motherPosition, person.getMother());
                }
            }

        }
    }

    @Override
    public void drawMotherFamily(Position childPosition, AncestorPerson person) {
        addVerticalLineToParents(childPosition);
        Position motherPosition = drawFather(childPosition, person.getMother());

        if (person.getFather() != null) {
            drawMother(childPosition, person.getFather(), person.getParents().getMarriageDate());
        }

        if (configuration.isShowSiblings()) {
            drawSiblingsAroundMother(motherPosition, person.getMother());
        }

        if (configuration.isShowHeraldry()) {
            addHeraldry(childPosition, person.getSimpleBirthPlace());
        }

        drawFathersFamily(motherPosition, person.getMother());
    }

}
