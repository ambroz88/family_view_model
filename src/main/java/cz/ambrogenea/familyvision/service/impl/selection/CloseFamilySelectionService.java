package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Couple;
import cz.ambrogenea.familyvision.domain.FamilyData;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorCouple;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.SelectionService;

public class CloseFamilySelectionService extends CommonSelectionService implements SelectionService {

    public CloseFamilySelectionService(ConfigurationService configuration) {
        super(configuration);
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
        ancestorPerson.setSpouseCouples(addSpouseWithChildren(person.getSpouseID()));
        return ancestorPerson;
    }

    private AncestorCouple findParents(String parentId) {
        AncestorCouple ancestorParents = null;
        if (parentId != null) {
            Couple parents = getFamilyData().getSpouseMap().get(parentId);
            if (parents != null) {
                ancestorParents = new AncestorCouple(parents, true);
            }
        }
        return ancestorParents;
    }

}
