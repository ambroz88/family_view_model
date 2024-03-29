package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorPerson;

import java.util.ArrayList;

public class AncestorPersonMapper {

    public static AncestorPerson map(Person person) {
        return new AncestorPerson(
                person.getGedcomId(),
                person.getFirstName(),
                person.getSurname(),
                person.getSex(),
                person.isLiving(),
                person.getBirthDatePlace(),
                person.getDeathDatePlace(),
                person.getOccupation(),
                new ArrayList<>(person.getResidences())
        );
    }
}
