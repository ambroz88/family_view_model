package cz.ambrogenea.familyvision.dto.parsing;

import cz.ambrogenea.familyvision.enums.InfoType;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Information {

    public final static String MARKER = "@";

    private int code;
    private InfoType type;
    private String value;

    public Information(String information) {
        String[] data = information.split(" ", 3);
        try {
            code = Integer.parseInt(data[0]);
        } catch (NumberFormatException e) {
            code = -1;
        }
        if (data.length == 2) {
            type = InfoType.getInfo(data[1]);
            value = "";
        } else if (data.length == 3) {
            type = InfoType.getInfo(data[1]);
            value = data[2];
            if (type.equals(InfoType.NONE)) {
                //in definition of Individual and Family type in GEDCOM are information switched
                type = InfoType.getInfo(data[2]);
                value = data[1];
            }
        } else {
            type = InfoType.getInfo("");
            value = "";
        }
    }

    public int getCode() {
        return code;
    }

    public InfoType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
