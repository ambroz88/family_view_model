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
    private Person wife;
    private Person husband;

    public Couple() {
        children = new ArrayList<>();
    }

    public Couple(Person husband, Person wife) {
        if (husband != null) {
            this.husband = new Person(husband);
        }
        if (wife != null) {
            this.wife = new Person(wife);
        }
        children = new ArrayList<>();
    }

    public Couple(Person person) {
        children = new ArrayList<>();
        addSpouse(person);
    }

    public void addSpouse(Person person) {
        if (person.getSex().equals(MALE)) {
            setHusband(person);
        } else if (person.getSex().equals(FEMALE)) {
            setWife(person);
        }
    }

    public void setWife(Person wife) {
        this.wife = wife;
    }

    public void setHusband(Person husband) {
        this.husband = husband;
    }

    public Person getSpouse(String sex) {
        if (sex.equals(MALE)) {
            return getWife();
        } else {
            return getHusband();
        }
    }

    public Person getWife() {
        return wife;
    }

    public Person getHusband() {
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
