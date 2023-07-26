package cz.ambrogenea.familyvision.model.response;

import cz.ambrogenea.familyvision.enums.Sex;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record PersonResponse(
        Long id,
        String gedcomId,
        String firstName,
        String surname,
        Sex sex,
        DatePlaceResponse birthDatePlace,
        DatePlaceResponse deathDatePlace,
        String occupation
) {
}
