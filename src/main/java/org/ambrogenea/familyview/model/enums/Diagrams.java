package org.ambrogenea.familyview.model.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Diagrams {
    PERGAMEN, HERALDRY, STICKY_NOTES, STICKY, WAVE, DOUBLE_WAVE;

    private static final String PERGAMEN_STRING = "pergamen";
    private static final String HERALDRY_STRING = "heraldry";
    private static final String STICKY_NOTES_STRING = "stickynotes";
    private static final String STICKY_STRING = "sticky";
    private static final String WAVE_STRING = "wave";
    private static final String DOUBLE_WAVE_STRING = "doublewave";

    public static Diagrams fromString(String name) {
        switch (name) {
            case PERGAMEN_STRING:
                return PERGAMEN;
            case HERALDRY_STRING:
                return HERALDRY;
            case STICKY_NOTES_STRING:
                return STICKY_NOTES;
            case STICKY_STRING:
                return STICKY;
            case WAVE_STRING:
                return WAVE;
            case DOUBLE_WAVE_STRING:
                return DOUBLE_WAVE;
            default:
                return PERGAMEN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case PERGAMEN:
                return PERGAMEN_STRING;
            case HERALDRY:
                return HERALDRY_STRING;
            case STICKY_NOTES:
                return STICKY_NOTES_STRING;
            case STICKY:
                return STICKY_STRING;
            case WAVE:
                return WAVE_STRING;
            case DOUBLE_WAVE:
                return DOUBLE_WAVE_STRING;
            default:
                return "None";
        }

    }

}
