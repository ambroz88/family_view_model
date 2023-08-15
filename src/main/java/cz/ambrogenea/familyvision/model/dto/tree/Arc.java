package cz.ambrogenea.familyvision.model.dto.tree;

import cz.ambrogenea.familyvision.constant.Spaces;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record Arc(
        Position leftUpperCorner,
        int startAngle
) {

    public static final int RADIUS = Spaces.HORIZONTAL_GAP;

}
