package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.model.dto.AncestorPerson;

import java.util.ArrayList;

public class AncestorPersonMapper {

    public static AncestorPerson map(Person person) {
        return new AncestorPerson(
                person.getId(),
                person.getGedcomId(),
                person.getFirstName(),
                person.getSurname(),
                person.getSex(),
                person.isLiving(),
                DatePlaceMapper.map(person.getBirthDate(), person.getBirthPlaceId()),
                DatePlaceMapper.map(person.getDeathDate(), person.getDeathPlaceId()),
                person.getOccupation(),
                new ArrayList<>(person.getResidences())
        );
    }
}
