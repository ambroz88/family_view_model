package org.ambrogenea.familyview.dto;

import java.util.ArrayList;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.utils.Tools;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorCouple {

    private ArrayList<String> childrenID;
    private ArrayList<AncestorPerson> children;

    private AncestorPerson wife;
    private AncestorPerson husband;
    private String marriageDate;
    private String marriagePlace;

    public AncestorCouple() {
        initEmpty();
    }

    public AncestorCouple(AncestorPerson husband, AncestorPerson wife) {
        this.husband = husband;
        this.wife = wife;
        initEmpty();
    }

    public AncestorCouple(AncestorPerson person) {
        if (person.getSex().equals(Sex.MALE)) {
            husband = person;
        } else if (person.getSex().equals(Sex.FEMALE)) {
            wife = person;
        }
        initEmpty();
    }

    public AncestorCouple(Couple couple) {
        if (couple != null) {
            if (couple.getHusband() != null) {
                this.husband = new AncestorPerson(couple.getHusband());
                this.husband.setDirectLineage(true);
            }

            if (couple.getWife() != null) {
                this.wife = new AncestorPerson(couple.getWife());
                this.wife.setDirectLineage(true);
            }
            this.childrenID = couple.getChildrenIndexes();
            this.children = new ArrayList<>(couple.getChildren());
            this.marriageDate = couple.getMarriageDateEnglish();
            this.marriagePlace = couple.getMarriagePlace();
        } else {
            initEmpty();
        }
    }

    public AncestorCouple(AncestorCouple couple) {
        if (couple != null) {
            if (couple.getHusband() != null) {
                this.husband = new AncestorPerson(couple.getHusband());
            }

            if (couple.getWife() != null) {
                this.wife = new AncestorPerson(couple.getWife());
            }
            this.childrenID = couple.getChildrenIndexes();
            this.children = new ArrayList<>(couple.getChildren());
            this.marriageDate = couple.getMarriageDateEnglish();
            this.marriagePlace = couple.getMarriagePlace();
        } else {
            initEmpty();
        }
    }

    private void initEmpty() {
        childrenID = new ArrayList<>();
        children = new ArrayList<>();
        marriageDate = "";
        marriagePlace = "";
    }

    public void setWife(AncestorPerson wife) {
        this.wife = wife;
    }

    public void setHusband(AncestorPerson husband) {
        this.husband = husband;
    }

    public String getMarriageDate() {
        return Tools.translateDateToCzech(marriageDate);
    }

    public String getMarriageDateEnglish() {
        return marriageDate;
    }

    public void setMarriageDate(String date) {
        this.marriageDate = date;
    }

    public String getMarriagePlace() {
        return marriagePlace;
    }

    public void setMarriagePlace(String place) {
        this.marriagePlace = place;
    }

    public AncestorPerson getSpouse(Sex sex) {
        if (sex.equals(Sex.MALE)) {
            return getWife();
        } else {
            return getHusband();
        }
    }

    public AncestorPerson getWife() {
        return wife;
    }

    public AncestorPerson getHusband() {
        return husband;
    }

    public boolean hasHusband() {
        return husband != null;
    }

    public boolean hasWife() {
        return wife != null;
    }

    public ArrayList<String> getChildrenIndexes() {
        return childrenID;
    }

    public void addChildrenIndex(String childID) {
        this.childrenID.add(childID);
    }

    public ArrayList<AncestorPerson> getChildren() {
        return children;
    }

    public void addChildren(AncestorPerson child) {
        this.children.add(child);
    }

    public boolean isEmpty() {
        return husband == null && wife == null;
    }

    @Override
    public String toString() {
        if (getHusband() != null && getWife() == null) {
            return getHusband().getName();
        } else if (getHusband() == null && getWife() != null) {
            return getWife().getName();
        } else if (isEmpty()) {
            return "None";
        } else {
            return getHusband().getName() + " and " + getWife().getName();
        }

    }

}