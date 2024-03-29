package cz.ambrogenea.familyvision.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum InfoType {
    NAME("NAME"), FIRST_NAME("GIVN"), SURNAME("SURN"), MARRIAGE_NAME("_MARNM"), SEX("SEX"), BIRTH("BIRT"), DEATH("DEAT"),
    DATE("DATE"), PLACE("PLAC"), OCCUPATION("OCCU"), MARRIAGE("MARR"), RESIDENCE("RESI"), ADDRESS("ADDR"), CITY("CITY"),
    SPOUSE("FAMS"), PARENTS("FAMC"), CHILD("CHIL"), INDIVIDUAL("INDI"), FAMILY("FAM"), NONE("NONE");

    private final String info;

    InfoType(String type) {
        this.info = type;
    }

    @Override
    public String toString() {
        return info;
    }
}
