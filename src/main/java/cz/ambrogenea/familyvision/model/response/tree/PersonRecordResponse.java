package cz.ambrogenea.familyvision.model.response.tree;

import cz.ambrogenea.familyvision.model.dto.DatePlaceDto;
import cz.ambrogenea.familyvision.enums.Sex;

public record PersonRecordResponse(
        PositionResponse position,
        String id,
        String firstName,
        String surname,
        Sex sex,
        DatePlaceDto birthDatePlace,
        DatePlaceDto deathDatePlace,
        String occupation,
        boolean living,
        boolean directLineage
) {
}
