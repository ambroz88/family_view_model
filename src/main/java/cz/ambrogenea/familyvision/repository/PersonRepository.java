package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.Person;

import java.util.*;

public class PersonRepository {

    private final HashMap<Long, SortedMap<String, Person>> personMap = new HashMap<>();

    public Person save(Person person) {
        if (personMap.containsKey(person.getFamilyTreeId())) {
            personMap.get(person.getFamilyTreeId()).put(person.getGedcomId(), person);
        } else {
            TreeMap<String, Person> map = new TreeMap<>();
            map.put(person.getGedcomId(), person);
            personMap.put(person.getFamilyTreeId(), map);
        }
        return person;
    }

    public Optional<Person> findByGedcomId(String gedcomId, Long treeId) {
        if (gedcomId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(personMap.get(treeId).get(gedcomId));
    }

    public List<Person> findAll(Long treeId) {
        return new ArrayList<>(personMap.get(treeId).values());
    }
}
