package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorCouple;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.mapper.dto.AncestorCoupleMapper;
import cz.ambrogenea.familyvision.mapper.dto.AncestorPersonMapper;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.util.Services;

public class AllAncestorsSelectionService extends CommonSelectionService implements SelectionService {

    @Override
    public AncestorPerson select(String personId) {
        Person person = Services.person().getPersonByGedcomId(personId);
        AncestorPerson ancestorPerson = fromRootPersonWithAllDescendents(person);
        addAllParents(ancestorPerson, person.getParentId(), 1);
        return ancestorPerson;
    }

    public AncestorPerson fromPersonWithParents(Person person, int generation) {
        AncestorPerson newPerson = AncestorPersonMapper.map(person);
        newPerson.setDirectLineage(true);
        if (generation < 3) {
            addSiblings(newPerson, person.getParentId());
        }
        if (generation <= Config.treeShape().getAncestorGenerations()) {
            addAllParents(newPerson, person.getParentId(), generation + 1);
        }
        return newPerson;
    }

    protected void addAllParents(AncestorPerson person, String parentId, int generation) {
        if (parentId != null) {
            MarriageDto parents = Services.marriage().getMarriageDtoByGedcomId(parentId);

            if (person != null && parents != null) {
                person.setParents(AncestorCoupleMapper.mapWithoutKids(parents, true));

                if (parents.husband() != null) {
                    AncestorPerson father = fromPersonWithParents(parents.husband(), generation);
                    person.setFather(father);
                }

                if (parents.wife() != null) {
                    AncestorPerson mother = fromPersonWithParents(parents.wife(), generation);
                    person.setMother(mother);
                }

                if (parents.husband() != null && parents.wife() != null) {
                    person.getFather().getSpouseCouples().add(new AncestorCouple(person.getFather(), person.getMother()));
                    person.getMother().getSpouseCouples().add(new AncestorCouple(person.getFather(), person.getMother()));
                }
            }

        }
    }

}
