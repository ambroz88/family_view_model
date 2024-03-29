package cz.ambrogenea.familyvision.model.response.tree;

import cz.ambrogenea.familyvision.enums.LabelType;

public record MarriageResponse(
        PositionResponse position,
        String date,
        int boysCount,
        int girlsCount,
        LabelType labelType
) {
}
