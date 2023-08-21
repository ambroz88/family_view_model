package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.service.util.Services;

public class LineageSelectionService extends CommonSelectionService implements SelectionService {

    @Override
    public AncestorPerson select(Long personId) {
        Person person = Services.person().getById(personId);
        return switch (Config.treeShape().getLineageType()) {
            case FATHER, ALL -> fromPersonWithManParents(person, 1);
            case MOTHER -> fromPersonWithWomanParents(person);
            case PARENTS -> loadParents(person);
        };
    }

    private AncestorPerson loadParents(Person person) {
        AncestorPerson ancestorPerson = fromPersonWithManParents(person, 1);
        AncestorPerson ancestorPersonCopy = fromPersonWithWomanParents(person);
        ancestorPerson.setMother(ancestorPersonCopy.getMother());

        switchParentSiblings(ancestorPerson);
        return ancestorPerson;
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
