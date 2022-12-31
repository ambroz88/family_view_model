package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.util.Services;

public class FathersSelectionService extends CommonSelectionService implements SelectionService {

    @Override
    public AncestorPerson select(String personId) {
        Person person = Services.person().getPersonByGedcomId(personId);
        return fromPersonWithManParents(person, 1);
    }

}
