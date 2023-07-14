package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.model.command.PersonCreateCommand;

import java.util.List;

public interface PersonService {
    Person createPerson(PersonCreateCommand person);
    Person savePerson(Person person);
    Person getPersonByGedcomId(String gedcomId, Long treeId);
    List<Person> getPeopleInTree(Long treeId);
}
