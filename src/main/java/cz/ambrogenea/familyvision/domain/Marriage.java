package cz.ambrogenea.familyvision.domain;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Marriage {
    private String gedcomFamilyId;
    private Long familyTreeId;
    private String husbandId;
    private String wifeId;

    private String weddingDate;
    private String weddingPlace;
    private List<String> childrenIds;

    public String getGedcomFamilyId() {
        return gedcomFamilyId;
    }

    public void setGedcomFamilyId(String gedcomFamilyId) {
        this.gedcomFamilyId = gedcomFamilyId;
    }

    public Long getFamilyTreeId() {
        return familyTreeId;
    }

    public void setFamilyTreeId(Long familyTreeId) {
        this.familyTreeId = familyTreeId;
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

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public String getWeddingPlace() {
        return weddingPlace;
    }

    public void setWeddingPlace(String weddingPlace) {
        this.weddingPlace = weddingPlace;
    }

    public List<String> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<String> childrenIds) {
        this.childrenIds = childrenIds;
    }

}
