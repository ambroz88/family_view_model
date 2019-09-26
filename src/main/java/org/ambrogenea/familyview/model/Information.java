package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Information {

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
