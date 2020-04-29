package org.ambrogenea.familyview.model;

import java.util.ArrayList;

import org.ambrogenea.familyview.model.enums.Sex;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Couple {

    private ArrayList<String> childrenID;
//    private ArrayList<AncestorPerson> children;

    private Person wife;
    private Person husband;
    private String marriageDate;
    private String marriagePlace;

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

    public Couple(Couple couple) {
        if (couple != null) {
            if (couple.getHusband() != null) {
                this.husband = couple.getHusband();
            }

            if (couple.getWife() != null) {
                this.wife = couple.getWife();
            }
            this.childrenID = couple.getChildrenIndexes();
            this.marriageDate = couple.getMarriageDateEnglish();
            this.marriagePlace = couple.getMarriagePlace();
        } else {
            initEmpty();
        }
    }

    private void initEmpty() {
        childrenID = new ArrayList<>();
        marriageDate = "";
        marriagePlace = "";
    }

    public void addSpouse(Person person) {
        if (person.getSex().equals(Sex.MALE)) {
            setHusband(new Person(person));
            Person emptyWife = new Person("-999");
            emptyWife.setSex(Sex.FEMALE);
            setWife(emptyWife);
        } else if (person.getSex().equals(Sex.FEMALE)) {
            setWife(new Person(person));
        }
    }

    public void setWife(Person wife) {
        this.wife = wife;
    }

    public Person getWife() {
        return wife;
    }

    public void setHusband(Person husband) {
        this.husband = husband;
    }

    public Person getHusband() {
        return husband;
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

    public Person getSpouse(Sex sex) {
        if (sex.equals(Sex.MALE)) {
            return getWife();
        } else {
            return getHusband();
        }
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

    public boolean isEmpty() {
        return husband == null && wife == null;
    }

}
