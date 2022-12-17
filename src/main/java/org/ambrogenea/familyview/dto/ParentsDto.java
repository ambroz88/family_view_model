package org.ambrogenea.familyview.dto;

import org.ambrogenea.familyview.dto.tree.Position;

public record ParentsDto(
        Position husbandPosition,
        Position wifePosition,
        int nextHeraldryY
) {
}
