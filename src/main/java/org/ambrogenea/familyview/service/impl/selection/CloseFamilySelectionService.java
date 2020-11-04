package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.SelectionService;

public class CloseFamilySelectionService extends CommonSelectionService implements SelectionService {

    public CloseFamilySelectionService() {
    }

    public CloseFamilySelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);

        Person person = getFamilyData().getPersonById(personId);
        AncestorPerson ancestorPerson = new AncestorPerson(person);
        ancestorPerson.setDirectLineage(true);

        addSiblings(ancestorPerson, person.getParentID());
        ancestorPerson.setParents(findParents(person.getParentID()));
        ancestorPerson.setSpouseCouples(addSpouse(person.getSpouseID()));
        return ancestorPerson;
    }

    private AncestorCouple findParents(String parentId) {
        AncestorCouple ancestorParents = null;
        if (parentId != null) {
            Couple parents = getFamilyData().getSpouseMap().get(parentId);
            if (parents != null) {
                ancestorParents = new AncestorCouple(parents);
            }
        }
        return ancestorParents;
    }

}
