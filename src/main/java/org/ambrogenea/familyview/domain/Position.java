package org.ambrogenea.familyview.domain;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position newPosition) {
        this.x = newPosition.x;
        this.y = newPosition.y;
    }

    public Position addX(int addDistance) {
        return new Position(x + addDistance, y);
    }

    public void addY(int addDistance) {
        this.y = y + addDistance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
