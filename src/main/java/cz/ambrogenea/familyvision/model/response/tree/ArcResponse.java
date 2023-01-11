package cz.ambrogenea.familyvision.model.response.tree;

import cz.ambrogenea.familyvision.constant.Spaces;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record ArcResponse(
        PositionResponse leftUpperCorner,
        int startAngle
) {

    public static final int RADIUS = Spaces.HORIZONTAL_GAP;

}
