package cz.ambrogenea.familyvision.dto.tree;

import cz.ambrogenea.familyvision.enums.Relation;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record Line(
        int startX,
        int startY,
        int endX,
        int endY,
        Relation relation
) {

    public static final int LINEAGE = 2;
    public static final int SIBLINGS = 1;

}
