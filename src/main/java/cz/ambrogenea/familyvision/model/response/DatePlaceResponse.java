package cz.ambrogenea.familyvision.model.response;

import cz.ambrogenea.familyvision.enums.DateSpecification;

import java.time.LocalDate;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record DatePlaceResponse(
        long id,
        LocalDate date,
        DateSpecification dateNote,
        CityResponse city,
        String datePattern
) {
}
