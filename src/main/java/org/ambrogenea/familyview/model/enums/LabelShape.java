package org.ambrogenea.familyview.model.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum LabelShape {
    OVAL, RECTANGLE;

    public static String[] getStrings() {
        return new String[]{OVAL.name(), RECTANGLE.name()};
    }
}
