package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.Date;

import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    private final String id;

    private String firstName;
    private String surname;
    private String sex;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String parentID;
    private ArrayList<String> spouseID;

    private Couple parents;

    public Person(String id) {
        this.id = id.replace(Information.MARKER, "");
        initEmpty();
    }

    public Person(Person person) {
        if (person != null) {
            this.id = person.getId();

            this.firstName = person.getFirstName();
            this.surname = person.getSurname();
            this.sex = person.getSex();
            this.birthDate = person.getBirthDate();
            this.birthPlace = person.getBirthPlace();
            this.deathDate = person.getDeathDate();
            this.deathPlace = person.getDeathPlace();
            this.parentID = person.getParentID();
            this.spouseID = person.getSpouseID();
            if (person.getParents() != null) {
                this.parents = new Couple(person.getParents());
            }

        } else {
            this.id = "";
            initEmpty();
        }
    }

    private void initEmpty() {
        spouseID = new ArrayList<>();
        parents = new Couple();

        firstName = "";
        surname = "";
        birthDate = "";
        birthPlace = "";
        deathDate = "";
        deathPlace = "";
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

    public void setName(String name) {
        int slashPosition = name.indexOf(" /");
        if (slashPosition != -1) {
            firstName = name.substring(0, slashPosition);
            surname = name.substring(slashPosition + 2, name.length() - 1);
        }
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

    public String getBirthDateCzech() {
        return Tools.translateDateToCzech(birthDate);
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

    public String getDeathDateCzech() {
        return Tools.translateDateToCzech(deathDate);
    }

    public void setDeathDate(String deathDate) {
        if (!"DECEASED".equals(deathDate)) {
            this.deathDate = deathDate;
        }
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
        this.parentID = parentID.replace(Information.MARKER, "");
    }

    public ArrayList<String> getSpouseID() {
        return spouseID;
    }

    public void addSpouseID(String spouseID) {
        getSpouseID().add(spouseID.replace(Information.MARKER, ""));
    }

    public Couple getParents() {
        return parents;
    }

    public void setFather(AncestorPerson father) {
        getParents().setHusband(father);
    }

    public AncestorPerson getFather() {
        return getParents().getHusband();
    }

    public void setMother(AncestorPerson mother) {
        getParents().setWife(mother);
    }

    public AncestorPerson getMother() {
        return getParents().getWife();
    }

    public void setParents(Couple parents) {
        this.parents = new Couple(parents);
    }

    public boolean isChild() {
        return isYoungerThan(8);
    }

    public boolean isTeenager() {
        return isYoungerThan(18);
    }

    private boolean isYoungerThan(int age) {
        if (!getBirthDate().isEmpty() && !getDeathDate().isEmpty()) {
            Date birth = Tools.convertDateString(getBirthDate());
            Date death = Tools.convertDateString(getDeathDate());

            long ageInMillis = death.getTime() - birth.getTime();
            double ageInYears = ageInMillis / 1000 / 3600 / 24 / 365;

            if (ageInYears < age) {
                return true;
            }
        }
        return false;
    }

    public void setInformation(Information info, String lastType) {
        if (info.getType().equals(Information.TYPE_FIRST_NAME)) {
            setFirstName(info.getValue());
        }
        if (info.getType().equals(Information.TYPE_SURNAME)) {
            setSurname(info.getValue());
        }
        if (info.getType().equals(Information.TYPE_NAME)) {
            setName(info.getValue());
        }
        if (info.getType().equals(Information.TYPE_SEX)) {
            setSex(info.getValue());
        }
        if (info.getType().equals(Information.TYPE_DATE)) {
            if (lastType.equals(Information.TYPE_BIRTH)) {
                setBirthDate(info.getValue());
            } else if (lastType.equals(Information.TYPE_DEATH)) {
                setDeathDate(info.getValue());
            }
        }
        if (info.getType().equals(Information.TYPE_PLACE)) {
            if (lastType.equals(Information.TYPE_BIRTH)) {
                setBirthPlace(info.getValue());
            } else if (lastType.equals(Information.TYPE_DEATH)) {
                setDeathPlace(info.getValue());
            }
        }
        if (info.getType().equals(Information.TYPE_PARENTS)) {
            setParentID(info.getValue());
        }
        if (info.getType().equals(Information.TYPE_SPOUSE)) {
            addSpouseID(info.getValue());
        }
    }

    @Override
    public String toString() {
        return getName() + "; parents: " + parents;
    }

}
