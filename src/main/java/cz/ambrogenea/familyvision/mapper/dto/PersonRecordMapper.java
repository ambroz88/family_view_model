package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.tree.PersonRecord;
import cz.ambrogenea.familyvision.model.dto.tree.Position;

import java.util.ArrayList;

public class PersonRecordMapper {

    public static PersonRecord map(AncestorPerson person, Position position) {
        return new PersonRecord(
                position,
                person.getGedcomId(),
                person.getFirstName(),
                person.getSurname(),
                person.getSex(),
                person.getBirthDatePlace(),
                person.getDeathDatePlace(),
                person.getOccupation(),
                person.isLiving(),
                person.isDirectLineage(),
                new ArrayList<>(person.getResidences())
        );
    }
}
