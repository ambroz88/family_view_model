package cz.ambrogenea.familyvision.model.response.tree;

import cz.ambrogenea.familyvision.domain.DatePlace;
import cz.ambrogenea.familyvision.enums.Sex;

public record PersonRecordResponse(
        PositionResponse position,
        String id,
        String firstName,
        String surname,
        Sex sex,
        DatePlace birthDatePlace,
        DatePlace deathDatePlace,
        String occupation,
        boolean living,
        boolean directLineage
) {
}
