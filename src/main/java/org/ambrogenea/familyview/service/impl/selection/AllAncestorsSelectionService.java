package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.FamilyData;
import org.ambrogenea.familyview.service.SelectionService;

public class AllAncestorsSelectionService extends CommonSelectionService implements SelectionService {

    public AllAncestorsSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId,  int generationLimit) {
        setGenerationLimit(generationLimit);
        AncestorPerson person = new AncestorPerson(getFamilyData().getIndividualMap().get(personId), true);
        Couple parents = findParents(person);

        addSpouse(person);
        person = addAllParents(person, parents);
        return person;
    }


    private AncestorPerson addAllParents(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            if (person.getAncestorLine().size() < getGenerationLimit()) {
                if (parents.getHusband() != null) {
                    AncestorPerson father = new AncestorPerson(parents.getHusband());
                    Couple fathersParents = findParents(father);
                    father.addChildrenCode(person.getAncestorLine());
                    person.setFather(addAllParents(father, fathersParents));
                }

                if (parents.getWife() != null) {
                    AncestorPerson mother = new AncestorPerson(parents.getWife());
                    Couple mothersParents = findParents(mother);
                    mother.addChildrenCode(person.getAncestorLine());
                    person.setMother(addAllParents(mother, mothersParents));
                }
            } else {
                person.addLastParentsCount(1);
            }

        }
        return person;
    }

}
