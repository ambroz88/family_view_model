package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.SelectionService;

public class DescendentSelectionService extends CommonSelectionService implements SelectionService {

    public DescendentSelectionService() {
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
