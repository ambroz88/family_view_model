package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
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
    public void drawFathersFamilyVertical(Position child, AncestorPerson person) {
        if (person.getMother() != null) {

            addVerticalLineToParents(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, person.getSimpleBirthPlace());
            }

            if (person.getFather() != null) {
                addVerticalLineToParents(child);
                drawMother(child, person.getMother(), person.getParents().getMarriageDate());

                Position fatherPosition = drawFather(child, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(fatherPosition, person.getFather());
                }

                drawFathersFamilyVertical(fatherPosition, person.getFather());
            } else {
                Position motherPosition = drawFather(child, person.getMother());
                drawLine(child, motherPosition, Line.LINEAGE);
                drawFathersFamilyVertical(motherPosition, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(motherPosition, person.getMother());
                }
            }

        }
    }
}
