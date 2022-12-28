package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.Services;

public class FathersSelectionService extends CommonSelectionService implements SelectionService {

    public FathersSelectionService(ConfigurationService configuration) {
        super(configuration);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);
        Person person = Services.person().getPersonByGedcomId(personId);
        return fromPersonWithManParents(person, 1);
    }

}
