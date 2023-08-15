package cz.ambrogenea.familyvision.model.dto;

import cz.ambrogenea.familyvision.model.dto.tree.Position;

import java.util.Objects;

public record FamilyDto(
        Position lastParentPosition,
        Position lastChildrenPosition
) {

    public int maxX(){
        return Math.max(
                Objects.requireNonNullElse(lastChildrenPosition(), lastParentPosition()).x(),
                lastParentPosition().x()
        );
    }

}
