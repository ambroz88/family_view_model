package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.mapper.domain.PersonMapper;
import cz.ambrogenea.familyvision.model.command.PersonCreateCommand;
import cz.ambrogenea.familyvision.repository.PersonRepository;
import cz.ambrogenea.familyvision.service.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository = new PersonRepository();

    @Override
    public Person createPerson(PersonCreateCommand person) {
        return personRepository.save(PersonMapper.map(person));
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person getPersonByGedcomId(String gedcomId) {
        return personRepository.findByGedcomId(gedcomId).orElse(null);
    }

    @Override
    public List<Person> getPeopleInTree() {
        return personRepository.findAll();
    }

}
