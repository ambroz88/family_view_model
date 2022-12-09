package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.enums.Relation;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Arc {

    public static final int RADIUS = Spaces.HORIZONTAL_GAP;
    public static final int ANGLE_SIZE = 90;

    private final Position leftUpperCorner;
    private final int startAngle;
    private Relation relation;

    public Arc(Position leftUpperCorner, int startAngle, Relation relation) {
        this.leftUpperCorner = leftUpperCorner;
        this.startAngle = startAngle;
        this.relation = relation;
    }

    public Position getLeftUpperCorner() {
        return leftUpperCorner;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

}
