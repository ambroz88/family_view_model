package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.SelectionService;

public class AllAncestorsSelectionService extends CommonSelectionService implements SelectionService {

    public AllAncestorsSelectionService() {
    }

    public AllAncestorsSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);

        Person person = getFamilyData().getPersonById(personId);
        AncestorPerson ancestorPerson = fromPersonWithParents(person);

        ancestorPerson.setSpouseCouples(addSpouse(person.getSpouseID()));
        return ancestorPerson;
    }

    public AncestorPerson fromPersonWithParents(Person person) {
        AncestorPerson newPerson = new AncestorPerson(person);
        newPerson.setDirectLineage(true);
        addAllParents(newPerson, person.getParentID());
        return newPerson;
    }

    protected void addAllParents(AncestorPerson person, String parentId) {
        if (parentId != null) {
            Couple parents = getFamilyData().getSpouseMap().get(parentId);

            if (parents != null) {
                person.setParents(new AncestorCouple(parents));

                if (parents.getHusband() != null) {
                    AncestorPerson father = fromPersonWithParents(parents.getHusband());
                    father.addChildrenCode(person.getAncestorLine());
                    person.setFather(father);
                }

                if (parents.getWife() != null) {
                    AncestorPerson mother = fromPersonWithParents(parents.getWife());
                    mother.addChildrenCode(person.getAncestorLine());
                    person.setMother(mother);
                }
            }

        }
    }

}
