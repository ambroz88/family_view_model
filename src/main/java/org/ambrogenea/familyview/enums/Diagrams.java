package org.ambrogenea.familyview.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Diagrams {
    SCROLL, HERALDRY, STICKY_NOTES, WAVE, DOUBLE_WAVE;

    private static final String SCROLL_STRING = "scroll";
    private static final String HERALDRY_STRING = "heraldry";
    private static final String STICKY_NOTES_STRING = "stickyNotes";
    private static final String WAVE_STRING = "wave";
    private static final String DOUBLE_WAVE_STRING = "doubleWave";

    public static Diagrams fromString(String name) {
        switch (name) {
            case HERALDRY_STRING:
                return HERALDRY;
            case STICKY_NOTES_STRING:
                return STICKY_NOTES;
            case WAVE_STRING:
                return WAVE;
            case DOUBLE_WAVE_STRING:
                return DOUBLE_WAVE;
            default:
                return SCROLL;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case SCROLL:
                return SCROLL_STRING;
            case HERALDRY:
                return HERALDRY_STRING;
            case STICKY_NOTES:
                return STICKY_NOTES_STRING;
            case WAVE:
                return WAVE_STRING;
            case DOUBLE_WAVE:
                return DOUBLE_WAVE_STRING;
            default:
                return "None";
        }

    }

}
