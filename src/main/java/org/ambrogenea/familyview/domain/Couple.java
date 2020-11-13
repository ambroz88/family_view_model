package org.ambrogenea.familyview.domain;

import java.util.ArrayList;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.enums.Sex;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Couple {

    private ArrayList<String> childrenID;
    private ArrayList<AncestorPerson> children;

    private Person wife;
    private Person husband;
    private DatePlace datePlace;

    public Couple() {
        initEmpty();
    }

    public Couple(Person husband, Person wife) {
        this.husband = husband;
        this.wife = wife;
        initEmpty();
    }

    public Couple(Person person) {
        addSpouse(person);
        initEmpty();
    }

    private void initEmpty() {
        childrenID = new ArrayList<>();
        children = new ArrayList<>();
        datePlace = new DatePlace();
    }

    public void addSpouse(Person person) {
        if (person.getSex().equals(Sex.MALE)) {
            setHusband(person);
        } else if (person.getSex().equals(Sex.FEMALE)) {
            setWife(person);
        }
    }

    public void setWife(Person wife) {
        this.wife = wife;
    }

    public void setHusband(Person husband) {
        this.husband = husband;
    }

    public DatePlace getDatePlace() {
        return datePlace;
    }

    public void setDatePlace(DatePlace datePlace) {
        this.datePlace = datePlace;
    }

    public Person getSpouse(Sex sex) {
        if (sex.equals(Sex.MALE)) {
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
