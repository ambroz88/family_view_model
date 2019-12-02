package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Couple {

    public static final String MALE = "M";
    public static final String FEMALE = "F";

    private final ArrayList<String> children;
    private AncestorPerson wife;
    private AncestorPerson husband;

    public Couple() {
        children = new ArrayList<>();
    }

    public Couple(AncestorPerson husband, AncestorPerson wife) {
        if (husband != null) {
            this.husband = husband;
        }
        if (wife != null) {
            this.wife = wife;
        }
        children = new ArrayList<>();
    }

    public Couple(AncestorPerson person) {
        children = new ArrayList<>();
        addSpouse(person);
    }

    public void addSpouse(AncestorPerson person) {
        if (person.getSex().equals(MALE)) {
            setHusband(person);
        } else if (person.getSex().equals(FEMALE)) {
            setWife(person);
        }
    }

    public void setWife(AncestorPerson wife) {
        this.wife = wife;
    }

    public void setHusband(AncestorPerson husband) {
        this.husband = husband;
    }

    public Person getSpouse(String sex) {
        if (sex.equals(MALE)) {
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

    public ArrayList<String> getChildren() {
        return children;
    }

    public void addChildren(String childID) {
        this.children.add(childID);
    }

    public boolean isEmpty() {
        return husband == null && wife == null;
    }

    @Override
    public String toString() {
        return "Father: " + getHusband().getFirstName() + " and Mother: " + getWife().getFirstName();
    }

}
