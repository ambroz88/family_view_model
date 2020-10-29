package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.FamilyData;
import org.ambrogenea.familyview.service.SelectionService;

public class FathersSelectionService extends CommonSelectionService implements SelectionService {

    public FathersSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);
        AncestorPerson person = new AncestorPerson(getFamilyData().getIndividualMap().get(personId), true);
        Couple parents = findParents(person);

        addManParentsWithSiblings(person, parents);
        return person;
    }

}
