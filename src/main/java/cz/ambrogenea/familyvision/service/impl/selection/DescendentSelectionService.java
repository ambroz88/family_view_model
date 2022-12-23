package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.FamilyData;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.SelectionService;

public class DescendentSelectionService extends CommonSelectionService implements SelectionService {

    public DescendentSelectionService(ConfigurationService configuration) {
        super(configuration);
    }

    public DescendentSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);
        Person person = getFamilyData().getPersonById(personId);
        return fromPersonWithAllDescendents(person, 1);
    }

}
