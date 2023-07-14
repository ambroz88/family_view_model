package cz.ambrogenea.familyvision.model.command;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class MarriageCreateCommand {
    final private Long familyTreeId;
    private String gedcomFamilyId;
    private String gedcomWifeId;
    private String gedcomHusbandId;
    private DatePlaceCreateCommand datePlace;
    private List<String> childrenGedcomIds;

    public MarriageCreateCommand(Long familyTreeId) {
        this.familyTreeId = familyTreeId;
    }

    public Long getFamilyTreeId() {
        return familyTreeId;
    }

    public String getGedcomFamilyId() {
        return gedcomFamilyId;
    }

    public void setGedcomFamilyId(String gedcomFamilyId) {
        this.gedcomFamilyId = gedcomFamilyId;
    }

    public String getGedcomWifeId() {
        return gedcomWifeId;
    }

    public void setGedcomWifeId(String gedcomWifeId) {
        this.gedcomWifeId = gedcomWifeId;
    }

    public String getGedcomHusbandId() {
        return gedcomHusbandId;
    }

    public void setGedcomHusbandId(String gedcomHusbandId) {
        this.gedcomHusbandId = gedcomHusbandId;
    }

    public DatePlaceCreateCommand getDatePlace() {
        return datePlace;
    }

    public void setDatePlace(DatePlaceCreateCommand datePlace) {
        this.datePlace = datePlace;
    }

    public List<String> getChildrenGedcomIds() {
        return childrenGedcomIds;
    }

    public void setChildrenGedcomIds(List<String> childrenGedcomIds) {
        this.childrenGedcomIds = childrenGedcomIds;
    }
}
