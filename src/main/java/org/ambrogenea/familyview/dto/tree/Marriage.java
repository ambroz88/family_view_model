package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.enums.LabelType;

public record Marriage(
        Position position,
        String date,
        LabelType labelType
) {
}
