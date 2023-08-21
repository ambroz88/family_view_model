package cz.ambrogenea.familyvision.mapper.domain;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.model.command.PersonCreateCommand;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonMapper {

    public static Person map(PersonCreateCommand personCreateCommand, Long birthCityId, Long deathCityId) {
        Person person = new Person();
        person.setGedcomId(personCreateCommand.getGedcomId());
        person.setFamilyTreeId(personCreateCommand.getFamilyTreeId());
        person.setFirstName(Objects.requireNonNullElse(personCreateCommand.getFirstName(), ""));
        person.setSurname(Objects.requireNonNullElse(personCreateCommand.getSurname(), ""));
        person.setSex(personCreateCommand.getSex());
        person.setLiving(personCreateCommand.isLiving());
        person.setBirthDate(personCreateCommand.getBirthDate());
        person.setBirthPlaceId(birthCityId);
        person.setDeathDate(personCreateCommand.getDeathDate());
        person.setDeathPlaceId(deathCityId);
        person.setOccupation(Objects.requireNonNullElse(personCreateCommand.getOccupation(), ""));
        person.setResidences(personCreateCommand.getResidenceList().stream()
                .map(ResidenceMapper::map)
                .collect(Collectors.toList()));
        person.setSpouseId(new ArrayList<>());
        return person;
    }

}
