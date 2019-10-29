package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    public final static String TYPE_NAME = "GIVN";
    public final static String TYPE_SURNAME = "SURN";
    public final static String TYPE_SEX = "SEX";
    public final static String TYPE_BIRTH = "BIRT";
    public final static String TYPE_DEATH = "DEAT";
    public final static String TYPE_DATE = "DATE";
    public final static String TYPE_PLACE = "PLAC";
    public final static String TYPE_SPOUSE = "FAMS";
    public final static String TYPE_PARENTS = "FAMC";
    private final static String MARKER = "@";

    private final ArrayList<Person> children;
    private final String id;

    private String firstName;
    private String surname;
    private String sex;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String parentID;
    private String spouseID;

    private Couple parents;
    private Person spouse;

    public Person(String id) {
        this.id = id.replace(MARKER, "");
        children = new ArrayList<>();

        firstName = "";
        surname = "";
        birthDate = "";
        birthPlace = "";
        deathDate = "";
        deathPlace = "";
    }

    public Person(Person person) {
        if (person != null) {
            this.id = person.getId();
            this.children = new ArrayList<>(person.getChildren());

            this.firstName = person.getFirstName();
            this.surname = person.getSurname();
            this.sex = person.getSex();
            this.birthDate = person.getBirthDate();
            this.birthPlace = person.getBirthPlace();
            this.deathDate = person.getDeathDate();
            this.deathPlace = person.getDeathPlace();
            this.parentID = person.getParentID();
            this.spouseID = person.getSpouseID();
            this.parents = person.getParents();
            this.spouse = person.getSpouse();
        } else {
            this.id = "";
            children = new ArrayList<>();
        }
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        if (getFirstName().isEmpty()) {
            return getSurname();
        } else if (getSurname().isEmpty()) {
            return getFirstName();
        }
        return getFirstName() + " " + getSurname();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID.replace(MARKER, "");
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID.replace(MARKER, "");
    }

    public Couple getParents() {
        return parents;
    }

    public void setParents(Couple parents) {
        this.parents = parents;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public void setSpouse(Couple spouse) {
        if (getSex().equals(Couple.MALE)) {
            this.spouse = spouse.getWife();
        } else if (getSex().equals(Couple.FEMALE)) {
            this.spouse = spouse.getHusband();
        }
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    public void addChildren(Person child) {
        this.children.add(child);
    }

    public void setInformation(Information info, String lastType) {
        if (info.getType().equals(TYPE_NAME)) {
            setFirstName(info.getValue());
        }
        if (info.getType().equals(TYPE_SURNAME)) {
            setSurname(info.getValue());
        }
        if (info.getType().equals(TYPE_SEX)) {
            setSex(info.getValue());
        }
        if (info.getType().equals(TYPE_DATE)) {
            if (lastType.equals(TYPE_BIRTH)) {
                setBirthDate(info.getValue());
            } else if (lastType.equals(TYPE_DEATH)) {
                setDeathDate(info.getValue());
            }
        }
        if (info.getType().equals(TYPE_PLACE)) {
            if (lastType.equals(TYPE_BIRTH)) {
                setBirthPlace(info.getValue());
            } else if (lastType.equals(TYPE_DEATH)) {
                setDeathPlace(info.getValue());
            }
        }
        if (info.getType().equals(TYPE_PARENTS)) {
            setParentID(info.getValue());
        }
        if (info.getType().equals(TYPE_SPOUSE)) {
            if (getSpouseID() == null) {
                setSpouseID(info.getValue());
            }
        }
    }

    @Override
    public String toString() {
        return "Person{" + "name: " + getName() + "; parents: " + parents + ", spouse: " + spouse + '}';
    }

}
