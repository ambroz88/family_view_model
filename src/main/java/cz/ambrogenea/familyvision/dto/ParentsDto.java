package cz.ambrogenea.familyvision.dto;

import cz.ambrogenea.familyvision.dto.tree.Position;

public record ParentsDto(
        Position husbandPosition,
        Position wifePosition,
        int nextHeraldryY
) {
}
