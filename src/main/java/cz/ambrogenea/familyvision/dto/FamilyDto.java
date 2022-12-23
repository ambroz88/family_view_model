package cz.ambrogenea.familyvision.dto;

import cz.ambrogenea.familyvision.dto.tree.Position;

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

    public Position maxChildrenPosition(){
        return new Position(maxX(), lastChildrenPosition().y());
    }
}
