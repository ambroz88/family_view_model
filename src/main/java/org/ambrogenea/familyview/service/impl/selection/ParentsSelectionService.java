package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.service.SelectionService;

public class ParentsSelectionService extends CommonSelectionService implements SelectionService {

    public ParentsSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);
        AncestorPerson person = new AncestorPerson(getFamilyData().getIndividualMap().get(personId), true);
        Couple parents = findParents(person);

        addManParentsWithSiblings(person, parents);
        addWomanParentsWithSiblings(person, parents);

        switchParentSiblings(person);
        return person;
    }

    private void switchParentSiblings(AncestorPerson person) {
        person.getFather().moveYoungerSiblingsToOlder();
        person.getMother().moveOlderSiblingsToYounger();
    }

}
