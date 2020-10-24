package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.service.LineageService;

public class VerticalLineageService extends VerticalAncestorService implements LineageService {

    public VerticalLineageService(ConfigurationService configuration) {
        super(configuration);
    }

    @Override
    public void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson) {
        if (getConfiguration().isShowSpouses()) {
            Position lastSpouse = addRootSpouses(rootPersonPosition, rootPerson);
            if (getConfiguration().isShowSiblings()) {
                addSiblingsAroundWives(rootPersonPosition, rootPerson, lastSpouse.getX());
            }
        } else if (getConfiguration().isShowSiblings()) {
            drawSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public void generateFathersFamily(Position child, AncestorPerson person) {
        if (person.getMother() != null) {

            addVerticalLineToParents(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, person.getSimpleBirthPlace());
            }

            if (person.getFather() != null) {
                addMother(child, person.getMother(), person.getParents().getMarriageDate());

                Position fatherPosition = addFather(child, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    addSiblingsAroundMother(fatherPosition, person.getFather());
                }

                generateFathersFamily(fatherPosition, person.getFather());
            } else {
                Position motherPosition = addFather(child, person.getMother());
                drawLine(child, motherPosition, Line.LINEAGE);
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
            addMother(childPosition, person.getFather(), person.getParents().getMarriageDate());
        }

        if (configuration.isShowSiblings()) {
            addSiblingsAroundMother(motherPosition, person.getMother());
        }

        if (configuration.isShowHeraldry()) {
            addHeraldry(childPosition, person.getSimpleBirthPlace());
        }

        generateFathersFamily(motherPosition, person.getMother());
    }

}
