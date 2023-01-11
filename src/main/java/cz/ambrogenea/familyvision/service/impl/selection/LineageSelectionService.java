package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.service.util.Services;

public class LineageSelectionService extends CommonSelectionService implements SelectionService {

    @Override
    public AncestorPerson select(String personId) {
        Person person = Services.person().getPersonByGedcomId(personId);
        switch (Config.treeShape().getLineageType()) {
            case FATHER -> fromPersonWithManParents(person, 1);
            case MOTHER -> fromPersonWithWomanParents(person);
            case PARENTS -> {
                AncestorPerson ancestorPerson = fromPersonWithManParents(person, 1);
                AncestorPerson ancestorPersonCopy = fromPersonWithWomanParents(person);
                ancestorPerson.setMother(ancestorPersonCopy.getMother());

                switchParentSiblings(ancestorPerson);
                return ancestorPerson;
            }
        }
        return fromPersonWithManParents(person, 1);
    }

    private void switchParentSiblings(AncestorPerson person) {
        if (person.getFather() != null) {
            person.getFather().moveYoungerSiblingsToOlder();
        }
        if (person.getMother() != null) {
            person.getMother().moveOlderSiblingsToYounger();
        }
    }

}
