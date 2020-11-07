package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.enums.Relation;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Line {

    public static final int LINEAGE = 2;
    public static final int SIBLINGS = 1;

    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private Relation relation;

    public Line(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.relation = Relation.DIRECT;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setType(Relation relation) {
        this.relation = relation;
    }

}
