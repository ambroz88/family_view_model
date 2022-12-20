package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SelectionService;

public class AllAncestorsSelectionService extends CommonSelectionService implements SelectionService {

    public AllAncestorsSelectionService(ConfigurationService configuration) {
        super(configuration);
    }

    public AllAncestorsSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);

        Person person = getFamilyData().getPersonById(personId);
        AncestorPerson ancestorPerson = fromPersonWithParents(person, 1);

        ancestorPerson.setSpouseCouples(addSpouseWithChildren(person.getSpouseID()));
        return ancestorPerson;
    }

    public AncestorPerson fromPersonWithParents(Person person, int generation) {
        AncestorPerson newPerson = new AncestorPerson(person);
        newPerson.setDirectLineage(true);
        if (generation < 2) {
            addSiblings(newPerson, person.getParentID());
        }
        if (generation + 1 <= getGenerationLimit()) {
            addAllParents(newPerson, person.getParentID(), generation + 1);
        }
        return newPerson;
    }

    protected void addAllParents(AncestorPerson person, String parentId, int generation) {
        if (parentId != null) {
            Couple parents = getFamilyData().getSpouseMap().get(parentId);

            if (parents != null) {
                person.setParents(new AncestorCouple(parents, true));

                if (parents.getHusband() != null) {
                    AncestorPerson father = fromPersonWithParents(parents.getHusband(), generation);
                    father.addChildrenCode(person.getAncestorLine());
                    person.setFather(father);
                }

                if (parents.getWife() != null) {
                    AncestorPerson mother = fromPersonWithParents(parents.getWife(), generation);
                    mother.addChildrenCode(person.getAncestorLine());
                    person.setMother(mother);
                }

                if (parents.hasHusband() && parents.hasWife()) {
                    person.getFather().getSpouseCouples().add(new AncestorCouple(person.getFather(), person.getMother()));
                    person.getMother().getSpouseCouples().add(new AncestorCouple(person.getFather(), person.getMother()));
                }
            }

        }
    }

}
