package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.Person;

import java.util.*;

public class PersonRepository {

    private final SortedMap<String, Person> personMap = new TreeMap<>();

    public Person save(Person person) {
        personMap.put(person.getGedcomId(), person);
        return person;
    }

    public Optional<Person> findByGedcomId(String gedcomId) {
        if (gedcomId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(personMap.get(gedcomId));
    }

    public List<Person> findAll() {
        return new ArrayList<>(personMap.values());
    }
}
