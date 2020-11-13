package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationService;
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
            addSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public void generateFathersFamily(Position child, AncestorPerson person) {
        if (person != null && person.getMother() != null) {

            addVerticalLineToParents(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, person.getBirthDatePlace().getSimplePlace());
            }

            if (person.getFather() != null) {
                addMother(child, person.getMother(),
                        person.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale()));

                Position fatherPosition = addFather(child, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    addSiblingsAroundMother(fatherPosition, person.getFather());
                }

                generateFathersFamily(fatherPosition, person.getFather());
            } else {
                Position motherPosition = addFather(child, person.getMother());
                addLine(child, motherPosition, Relation.DIRECT);
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
            addMother(childPosition, person.getFather(),
                    person.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale()));
        }

        if (configuration.isShowSiblings()) {
            addSiblingsAroundMother(motherPosition, person.getMother());
        }

        if (configuration.isShowHeraldry()) {
            addHeraldry(childPosition, person.getBirthDatePlace().getSimplePlace());
        }

        generateFathersFamily(motherPosition, person.getMother());
    }

}
