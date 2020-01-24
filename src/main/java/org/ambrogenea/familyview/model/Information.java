package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Information {

    public final static String TYPE_CHILD = "CHIL";
    public final static String TYPE_NAME = "NAME";
    public final static String TYPE_FIRST_NAME = "GIVN";
    public final static String TYPE_SURNAME = "SURN";
    public final static String TYPE_SEX = "SEX";
    public final static String TYPE_BIRTH = "BIRT";
    public final static String TYPE_DEATH = "DEAT";
    public final static String TYPE_DATE = "DATE";
    public final static String TYPE_PLACE = "PLAC";
    public final static String TYPE_MARRIAGE = "MARR";
    public final static String TYPE_SPOUSE = "FAMS";
    public final static String TYPE_PARENTS = "FAMC";
    public final static String TYPE_RESIDENCE = "RESI";
    public final static String TYPE_ADDRESS = "ADDR";
    public final static String TYPE_CITY = "CITY";

    public final static String VALUE_INDIVIDUAL = "INDI";
    public final static String VALUE_FAMILY = "FAM";
    public static final String VALUE_MALE = "M";
    public static final String VALUE_FEMALE = "F";

    public final static String MARKER = "@";

    private int code;
    private final String type;
    private final String value;

    public Information(String information) {
        String[] data = information.split(" ", 3);
        try {
            code = Integer.valueOf(data[0]);
        } catch (NumberFormatException e) {
            code = -1;
        }
        if (data.length == 2) {
            type = data[1];
            value = "";
        } else if (data.length == 3) {
            type = data[1];
            value = data[2];
        } else {
            type = "";
            value = "";
        }
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
