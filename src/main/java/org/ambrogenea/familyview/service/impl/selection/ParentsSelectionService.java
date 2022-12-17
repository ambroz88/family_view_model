package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SelectionService;

public class ParentsSelectionService extends CommonSelectionService implements SelectionService {

    public ParentsSelectionService(ConfigurationService configuration) {
        super(configuration);
    }

    public ParentsSelectionService(FamilyData familyData) {
        super(familyData);
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);

        Person person = getFamilyData().getPersonById(personId);
        AncestorPerson ancestorPerson = fromPersonWithManParents(person, 1);
        AncestorPerson ancestorPersonCopy = fromPersonWithWomanParents(person);
        ancestorPerson.setMother(ancestorPersonCopy.getMother());

        switchParentSiblings(ancestorPerson);
        return ancestorPerson;
    }

    private void switchParentSiblings(AncestorPerson person) {
        person.getFather().moveYoungerSiblingsToOlder();
        person.getMother().moveOlderSiblingsToYounger();
    }

}
