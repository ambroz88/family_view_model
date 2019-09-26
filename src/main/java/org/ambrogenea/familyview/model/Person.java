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

    private ArrayList<Integer> ancestorLine;
    private int ancestorGenerations;

    private Couple parents;
    private Person spouse;
    private Person father;
    private Person mother;

    public Person(String id) {
        this.id = id.replace(MARKER, "");
        ancestorGenerations = 0;
        children = new ArrayList<>();
        ancestorLine = new ArrayList<>();
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
        return getFirstName() + " " + getSurname();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        ancestorLine.clear();
        if (sex.equals(Couple.MALE)) {
            ancestorLine.add(AncestorModel.CODE_MALE);
        } else {
            ancestorLine.add(AncestorModel.CODE_FEMALE);
        }
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

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
        if (getMother() == null) {
            if (father != null) {
                ancestorGenerations = father.getAncestorGenerations() + 1;
            }
        } else {
            if (father != null && father.getAncestorGenerations() >= mother.getAncestorGenerations()) {
                ancestorGenerations = father.getAncestorGenerations() + 1;
            }
        }
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
        if (getFather() == null) {
            if (mother != null) {
                ancestorGenerations = mother.getAncestorGenerations() + 1;
            }
        } else {
            if (mother != null && mother.getAncestorGenerations() >= father.getAncestorGenerations()) {
                ancestorGenerations = mother.getAncestorGenerations() + 1;
            }
        }
    }

    public void setSpouse(Couple spouse) {
        if (getSex().equals(Couple.MALE)) {
            this.spouse = spouse.getWife();
        } else if (getSex().equals(Couple.FEMALE)) {
            this.spouse = spouse.getHusband();
        }
    }

    public int getAncestorGenerations() {
        return ancestorGenerations;
    }

    public ArrayList<Integer> getAncestorLine() {
        return ancestorLine;
    }

    public void addChildrenCode(ArrayList<Integer> childrenCodes) {
        ancestorLine.addAll(0, childrenCodes);
    }

    public void addAncestorCode(int code) {
        ancestorLine.add(code);
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
            setSpouseID(info.getValue());
        }
    }

    @Override
    public String toString() {
        return "Person{" + "name: " + getName() + "; parents: " + parents + ", spouse: " + spouse + '}';
    }

}
