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

    public static Person map(PersonCreateCommand personCreateCommand) {
        return new Person(
                personCreateCommand.getGedcomId(),
                personCreateCommand.getFamilyTreeId(),
                Objects.requireNonNullElse(personCreateCommand.getFirstName(), ""),
                Objects.requireNonNullElse(personCreateCommand.getSurname(), ""),
                personCreateCommand.getSex(),
                personCreateCommand.isLiving(),
                personCreateCommand.getBirthDate(),
                personCreateCommand.getBirthPlace(),
                personCreateCommand.getDeathDate(),
                personCreateCommand.getDeathPlace(),
                Objects.requireNonNullElse(personCreateCommand.getOccupation(), ""),
                personCreateCommand.getResidenceList().stream()
                        .map(ResidenceMapper::map)
                        .collect(Collectors.toList()),
                new ArrayList<>()
        );
    }

}
