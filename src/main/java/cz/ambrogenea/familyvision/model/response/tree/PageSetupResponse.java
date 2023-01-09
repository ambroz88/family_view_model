package cz.ambrogenea.familyvision.model.response.tree;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record PageSetupResponse(
        PositionResponse startPosition,
        int pictureWidth,
        int pictureHeight
) {
}
