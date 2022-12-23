package cz.ambrogenea.familyvision.mapper;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.PersonRecord;
import cz.ambrogenea.familyvision.dto.tree.Position;

import java.util.ArrayList;

public class PersonRecordMapper {

    public static PersonRecord map(AncestorPerson person, Position position) {
        return new PersonRecord(
                position,
                person.getId(),
                person.getFirstName(),
                person.getSurname(),
                person.getSex(),
                person.getBirthDatePlace(),
                person.getDeathDatePlace(),
                person.getOccupation(),
                person.isLiving(),
                person.isDirectLineage(),
                new ArrayList<>(person.getResidenceList())
        );
    }
}
