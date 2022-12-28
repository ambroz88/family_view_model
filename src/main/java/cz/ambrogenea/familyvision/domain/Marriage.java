package cz.ambrogenea.familyvision.domain;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Marriage {
    private String gedcomFamilyId;
    private String husbandId;
    private String wifeId;

    private DatePlace weddingDatePlace;
    private List<String> childrenIds;

    public String getGedcomFamilyId() {
        return gedcomFamilyId;
    }

    public void setGedcomFamilyId(String gedcomFamilyId) {
        this.gedcomFamilyId = gedcomFamilyId;
    }

    public void setHusbandId(String husbandId) {
        this.husbandId = husbandId;
    }

    public void setWifeId(String wifeId) {
        this.wifeId = wifeId;
    }

    public String getHusbandId() {
        return husbandId;
    }

    public String getWifeId() {
        return wifeId;
    }


    public DatePlace getWeddingDatePlace() {
        return weddingDatePlace;
    }

    public void setWeddingDatePlace(DatePlace weddingDatePlace) {
        this.weddingDatePlace = weddingDatePlace;
    }

    public List<String> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<String> childrenIds) {
        this.childrenIds = childrenIds;
    }

}
