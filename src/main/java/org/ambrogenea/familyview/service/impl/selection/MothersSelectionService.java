package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SelectionService;

public class MothersSelectionService extends CommonSelectionService implements SelectionService {

    public MothersSelectionService(ConfigurationService configuration) {
        super(configuration);
    }

    public MothersSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);
        Person person = getFamilyData().getPersonById(personId);
        return fromPersonWithWomanParents(person);
    }
}
