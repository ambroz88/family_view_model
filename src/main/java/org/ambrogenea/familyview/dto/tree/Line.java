package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.enums.Relation;

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
