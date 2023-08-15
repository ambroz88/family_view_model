package cz.ambrogenea.familyvision.model.dto.tree;

import cz.ambrogenea.familyvision.enums.LabelType;

public record Marriage(
        Position position,
        String date,
        int boysCount,
        int girlsCount,
        LabelType labelType
) {
}
