package org.ambrogenea.familyview.dto;

import java.util.ArrayList;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.DatePlace;
import org.ambrogenea.familyview.enums.Sex;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorCouple {

    private ArrayList<String> childrenID;
    private ArrayList<AncestorPerson> children;

    private AncestorPerson wife;
    private AncestorPerson husband;
    private DatePlace datePlace;

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
            this.datePlace = couple.getDatePlace();
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
            this.datePlace = couple.getDatePlace();
        } else {
            initEmpty();
        }
    }

    private void initEmpty() {
        childrenID = new ArrayList<>();
        children = new ArrayList<>();
        datePlace = new DatePlace();
    }

    public void setWife(AncestorPerson wife) {
        this.wife = wife;
    }

    public void setHusband(AncestorPerson husband) {
        this.husband = husband;
    }

    public DatePlace getDatePlace() {
        return datePlace;
    }

    public void setDatePlace(DatePlace datePlace) {
        this.datePlace = datePlace;
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
