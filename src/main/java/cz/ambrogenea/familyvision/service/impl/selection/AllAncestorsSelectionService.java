package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorCouple;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.mapper.dto.AncestorCoupleMapper;
import cz.ambrogenea.familyvision.mapper.dto.AncestorPersonMapper;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.Services;

public class AllAncestorsSelectionService extends CommonSelectionService implements SelectionService {

    private final ConfigurationService configurationService;

    public AllAncestorsSelectionService(ConfigurationService configuration) {
        super(configuration);
        configurationService = configuration;
    }

    @Override
    public AncestorPerson select(String personId, int generationLimit) {
        setGenerationLimit(generationLimit);

        Person person = Services.person().getPersonByGedcomId(personId);
        AncestorPerson ancestorPerson = fromPersonWithParents(person, 1);
        if (configurationService.isShowSpouses() && person != null) {
            ancestorPerson.setSpouses(addSpouseWithChildren(person.getSpouseId()));
        }
        return ancestorPerson;
    }

    public AncestorPerson fromPersonWithParents(Person person, int generation) {
        AncestorPerson newPerson = AncestorPersonMapper.map(person);
        newPerson.setDirectLineage(true);
        if (generation < 2) {
            addSiblings(newPerson, person.getParentId());
        }
        if (generation + 1 <= getGenerationLimit()) {
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
