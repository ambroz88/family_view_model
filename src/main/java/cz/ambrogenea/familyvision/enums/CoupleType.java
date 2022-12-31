package cz.ambrogenea.familyvision.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum CoupleType {
    HORIZONTAL, VERTICAL;

    public static String[] getStrings() {
        return new String[]{HORIZONTAL.name(), VERTICAL.name()};
    }
}
