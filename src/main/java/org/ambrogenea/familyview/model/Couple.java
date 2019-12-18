package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Couple {

    private final ArrayList<String> children;
    private AncestorPerson wife;
    private AncestorPerson husband;
    private String marriageDate;
    private String marriagePlace;

    public Couple() {
        children = new ArrayList<>();
    }

    public Couple(AncestorPerson husband, AncestorPerson wife) {
        this.husband = husband;
        this.wife = wife;
        children = new ArrayList<>();
        marriageDate = "";
        marriagePlace = "";
    }

    public Couple(AncestorPerson person) {
        children = new ArrayList<>();
        addSpouse(person);
        marriageDate = "";
        marriagePlace = "";
    }

    public Couple(Couple couple) {
        if (couple != null) {
            if (couple.getHusband() != null) {
                this.husband = new AncestorPerson(couple.getHusband());
            }

            if (couple.getWife() != null) {
                this.wife = new AncestorPerson(couple.getWife());
            }
            this.children = couple.getChildrenIndexes();
            this.marriageDate = couple.getMarriageDate();
            this.marriagePlace = couple.getMarriagePlace();
        } else {
            children = new ArrayList<>();
            marriageDate = "";
            marriagePlace = "";
        }
    }

    public void addSpouse(AncestorPerson person) {
        if (person.getSex().equals(Information.VALUE_MALE)) {
            setHusband(person);
        } else if (person.getSex().equals(Information.VALUE_FEMALE)) {
            setWife(person);
        }
    }

    public void setWife(AncestorPerson wife) {
        this.wife = wife;
    }

    public void setHusband(AncestorPerson husband) {
        this.husband = husband;
    }

    public String getMarriageDate() {
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

    public Person getSpouse(String sex) {
        if (sex.equals(Information.VALUE_MALE)) {
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
        return children;
    }

    public void addChildrenIndex(String childID) {
        this.children.add(childID);
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
