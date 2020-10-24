package org.ambrogenea.familyview.domain;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position newPosition) {
        this.x = newPosition.x;
        this.y = newPosition.y;
    }

    public Position addXAndY(int addXDistance, int addYDistance) {
        return new Position(x + addXDistance, y + addYDistance);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
