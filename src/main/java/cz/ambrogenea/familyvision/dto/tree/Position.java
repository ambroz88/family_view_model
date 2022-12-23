package cz.ambrogenea.familyvision.dto.tree;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record Position(
        int x,
        int y
) {

    public Position() {
        this(0, 0);
    }

    public Position addXAndY(int addXDistance, int addYDistance) {
        return new Position(x + addXDistance, y + addYDistance);
    }

}
