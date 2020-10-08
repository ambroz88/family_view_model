package org.ambrogenea.familyview.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum InfoType {
    NAME("NAME"), FIRST_NAME("GIVN"), SURNAME("SURN"), SEX("SEX"), BIRTH("BIRT"), DEATH("DEAT"), DATE("DATE"),
    PLACE("PLAC"), OCCUPATION("OCCU"), MARRIAGE("MARR"), RESIDENCE("RESI"), ADDRESS("ADDR"), CITY("CITY"),
    SPOUSE("FAMS"), PARENTS("FAMC"), CHILD("CHIL"), INDIVIDUAL("INDI"), FAMILY("FAM"), NONE("NONE");

    private final String info;

    InfoType(String type) {
        this.info = type;
    }

    public static InfoType getInfo(String infoType) {
        InfoType type;
        switch (infoType) {
            case "NAME":
                type = NAME;
                break;
            case "GIVN":
                type = FIRST_NAME;
                break;
            case "SURN":
                type = SURNAME;
                break;
            case "SEX":
                type = SEX;
                break;
            case "BIRT":
                type = BIRTH;
                break;
            case "DEAT":
                type = DEATH;
                break;
            case "DATE":
                type = DATE;
                break;
            case "PLAC":
                type = PLACE;
                break;
            case "OCCU":
                type = OCCUPATION;
                break;
            case "MARR":
                type = MARRIAGE;
                break;
            case "RESI":
                type = RESIDENCE;
                break;
            case "ADDR":
                type = ADDRESS;
                break;
            case "CITY":
                type = CITY;
                break;
            case "FAMS":
                type = SPOUSE;
                break;
            case "FAMC":
                type = PARENTS;
                break;
            case "CHIL":
                type = CHILD;
                break;
            case "INDI":
                type = INDIVIDUAL;
                break;
            case "FAM":
                type = FAMILY;
                break;
            default:
                type = NONE;
                break;
        }
        return type;
    }

    @Override
    public String toString() {
        return info;
    }
}
