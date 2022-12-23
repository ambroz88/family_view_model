package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.FamilyData;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.SelectionService;

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
