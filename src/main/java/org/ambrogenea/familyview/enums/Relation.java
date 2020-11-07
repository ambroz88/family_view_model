package org.ambrogenea.familyview.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Relation {

    DIRECT, SIDE;

    public int getInt() {
        if (this.equals(DIRECT)) {
            return 2;
        } else {
            return 1;
        }
    }
}
