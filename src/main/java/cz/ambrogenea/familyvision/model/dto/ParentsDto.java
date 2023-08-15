package cz.ambrogenea.familyvision.model.dto;

import cz.ambrogenea.familyvision.model.dto.tree.Position;

public record ParentsDto(
        Position husbandPosition,
        Position wifePosition,
        int nextHeraldryY
) {
}
