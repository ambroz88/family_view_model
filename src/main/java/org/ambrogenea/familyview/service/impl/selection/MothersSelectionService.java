package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.service.SelectionService;

public class MothersSelectionService extends CommonSelectionService implements SelectionService {

    public MothersSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);
        AncestorPerson person = new AncestorPerson(getFamilyData().getIndividualMap().get(personId), true);
        Couple parents = findParents(person);

        addSiblings(parents, person);
        addSpouse(person);
        addWomanParentsWithSiblings(person, parents);
        return person;
    }
}
