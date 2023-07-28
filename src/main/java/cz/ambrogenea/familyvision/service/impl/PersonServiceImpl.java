package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.mapper.domain.PersonMapper;
import cz.ambrogenea.familyvision.model.command.PersonCreateCommand;
import cz.ambrogenea.familyvision.repository.PersonRepository;
import cz.ambrogenea.familyvision.service.CityService;
import cz.ambrogenea.familyvision.service.PersonService;
import cz.ambrogenea.familyvision.service.util.Services;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository = new PersonRepository();
    private final CityService cityService = Services.city();

    @Override
    public Person createPerson(PersonCreateCommand createCommand) {
        String birthPlace = createCommand.getBirthPlace();
        String deathPlace = createCommand.getDeathPlace();
        Long birthCityId = cityService.getCityId(birthPlace);
        Long deathCityId = cityService.getCityId(deathPlace);
        return personRepository.save(PersonMapper.map(createCommand, birthCityId, deathCityId));
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person getByGedcomId(String gedcomId, Long treeId) {
        return personRepository.findByGedcomId(gedcomId, treeId).orElse(null);
    }

    @Override
    public Person getById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> getPeopleInTree(Long treeId) {
        return personRepository.findAll(treeId);
    }

}
