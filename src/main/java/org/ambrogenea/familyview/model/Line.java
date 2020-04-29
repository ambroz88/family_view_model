package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Line {

    public static final int LINEAGE = 2;
    public static final int SIBLINGS = 1;

    private Position startPosition;
    private Position endPosition;
    private int type;

    public Line() {
        this.startPosition = new Position();
        this.endPosition = new Position();
        this.type = LINEAGE;
    }

    public Line(Position startPosition, Position endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.type = LINEAGE;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
