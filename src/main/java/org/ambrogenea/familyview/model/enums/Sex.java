package org.ambrogenea.familyview.model.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Sex {
    MALE("M"), FEMALE("F"), UNKNOWN("U");

    private final String type;

    Sex(String sex) {
        type = sex;
    }

    public static Sex getSex(String sexValue) {
        Sex sex;
        switch (sexValue) {
            case "M":
                sex = MALE;
                break;
            case "F":
                sex = FEMALE;
                break;
            case "U":
                sex = UNKNOWN;
                break;
            default:
                sex = MALE;
                break;
        }
        return sex;
    }

    @Override
    public String toString() {
        return type;
    }
}
