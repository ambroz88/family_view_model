package cz.ambrogenea.familyvision.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Relation {

    DIRECT, SIDE;

    public int getInt() {
        if (this.equals(DIRECT)) {
            return 3;
        } else {
            return 2;
        }
    }
}
