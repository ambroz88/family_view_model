package org.ambrogenea.familyview.dto;

import org.ambrogenea.familyview.dto.tree.Position;

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
