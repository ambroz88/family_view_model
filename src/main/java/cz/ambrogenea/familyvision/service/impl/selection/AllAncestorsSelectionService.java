package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.model.dto.AncestorCouple;
import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.MarriageDto;
import cz.ambrogenea.familyvision.mapper.dto.AncestorCoupleMapper;
import cz.ambrogenea.familyvision.mapper.dto.AncestorPersonMapper;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.util.Services;

public class AllAncestorsSelectionService extends CommonSelectionService implements SelectionService {

    @Override
    public AncestorPerson select(Long personId) {
        Person person = Services.person().getById(personId);
        AncestorPerson ancestorPerson = fromRootPersonWithAllDescendents(person);
        addSiblings(ancestorPerson, person.getParentId());
        addAllParents(ancestorPerson, person.getParentId(), 1);
        return ancestorPerson;
    }

    public AncestorPerson fromPersonWithParents(Person person, int generation) {
        AncestorPerson newPerson = AncestorPersonMapper.map(person);
        newPerson.setDirectLineage(true);
        if (generation < 4) {
            addSiblings(newPerson, person.getParentId());
        }
        if (generation <= Config.treeShape().getAncestorGenerations()) {
            addAllParents(newPerson, person.getParentId(), generation + 1);
        }
        return newPerson;
    }

    protected void addAllParents(AncestorPerson person, Long parentId, int generation) {
        if (parentId != null) {
            MarriageDto parents = Services.marriage().getMarriageDtoById(parentId);

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
