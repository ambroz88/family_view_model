package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.model.response.PersonResponse;

public class PersonResponseMapper {

    public static PersonResponse map(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getGedcomId(),
                person.getFirstName(),
                person.getSurname(),
                person.getSex(),
                DatePlaceResponseMapper.map(person.getBirthDate(), person.getBirthPlaceId()),
                DatePlaceResponseMapper.map(person.getDeathDate(), person.getDeathPlaceId()),
                person.getOccupation()
        );
    }

}
