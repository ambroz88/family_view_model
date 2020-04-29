package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Arc {

    public static final int RADIUS = 20;
    public static final int ANGLE_SIZE = 90;

    private final int leftUpperX;
    private final int leftUpperY;
    private final int startAngle;

    public Arc(int leftUpperX, int leftUpperY, int startAngle) {
        this.leftUpperX = leftUpperX;
        this.leftUpperY = leftUpperY;
        this.startAngle = startAngle;
    }

    public int getLeftUpperX() {
        return leftUpperX;
    }

    public int getLeftUpperY() {
        return leftUpperY;
    }

    public int getStartAngle() {
        return startAngle;
    }

}
