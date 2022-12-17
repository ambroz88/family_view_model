package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.enums.Relation;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record Arc(
        Position leftUpperCorner,
        int startAngle,
        Relation relation
) {

    public static final int RADIUS = Spaces.HORIZONTAL_GAP;
    public static final int ANGLE_SIZE = 90;

}
