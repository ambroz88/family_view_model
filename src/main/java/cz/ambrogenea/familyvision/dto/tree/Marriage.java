package cz.ambrogenea.familyvision.dto.tree;

import cz.ambrogenea.familyvision.enums.LabelType;

public record Marriage(
        Position position,
        String date,
        int boysCount,
        int girlsCount,
        LabelType labelType
) {
}
