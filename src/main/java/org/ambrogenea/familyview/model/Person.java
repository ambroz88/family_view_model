package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.Date;

import org.ambrogenea.familyview.model.enums.InfoType;
import org.ambrogenea.familyview.model.enums.Sex;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    private final String treeID;
    private int dbPosition;

    private String firstName;
    private String surname;
    private Sex sex;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String occupation;
    private boolean living;
    private boolean directLineage;
    private String parentID;
    private ArrayList<String> spouseID;
    private ArrayList<Residence> residenceList;
    private Position position;

    public Person(String treeID) {
        this.treeID = treeID;
        initEmpty();
    }

    public Person(Person person) {
        if (person != null) {
            this.treeID = person.getTreeID();
            this.dbPosition = person.getDbPosition();

            this.firstName = person.getFirstName();
            this.surname = person.getSurname();
            this.sex = person.getSex();
            this.living = person.isLiving();
            this.directLineage = person.isDirectLineage();
            this.birthDate = person.getBirthDate();
            this.birthPlace = person.getBirthPlace();
            this.deathDate = person.getDeathDate();
            this.deathPlace = person.getDeathPlace();
            this.occupation = person.getOccupation();
            this.residenceList = person.getResidenceList();
            this.parentID = person.getParentID();
            this.spouseID = person.getSpouseID();
            this.position = person.getPosition();

        } else {
            this.treeID = "";
            initEmpty();
        }
    }

    private void initEmpty() {
        dbPosition = -1;

        firstName = "";
        surname = "";
        sex = Sex.MALE;
        birthDate = "";
        birthPlace = "";
        deathDate = "";
        deathPlace = "";
        occupation = "";
        living = true;

        parentID = "";
        spouseID = new ArrayList<>();
        residenceList = new ArrayList<>();
        position = new Position();
    }

    public String getTreeID() {
        return treeID;
    }

    public int getDbPosition() {
        return dbPosition;
    }

    public void setDbPosition(int dbPosition) {
        this.dbPosition = dbPosition;
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
        } else {
            slashPosition = name.indexOf("/");
            if (slashPosition != -1) {
                surname = name.substring(slashPosition + 1, name.length() - 1);
            } else {
                firstName = name;
            }
        }
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
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

    public String getSimpleBirthPlace() {
        return birthPlace.split(",")[0];
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public boolean isLiving() {
        return living;
    }

    public boolean isDirectLineage() {
        return directLineage;
    }

    public void setDirectLineage(boolean directLineage) {
        this.directLineage = directLineage;
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

    public String getSimpleDeathPlace() {
        return deathPlace.split(",")[0];
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public ArrayList<Residence> getResidenceList() {
        return residenceList;
    }

    public Residence getLastResidence() {
        if (!residenceList.isEmpty()) {
            return residenceList.get(residenceList.size() - 1);
        } else {
            return null;
        }
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

    public boolean isChild() {
        return isYoungerThan(8);
    }

    public boolean isTeenager() {
        return isYoungerThan(18);
    }

    public int getAge() {
        double ageInYears = -1;
        if (!getBirthDate().isEmpty() && !getDeathDate().isEmpty()) {
            Date birth = Tools.convertDateString(getBirthDate());
            Date death = Tools.convertDateString(getDeathDate());

            if (birth != null && death != null) {
                long ageInMillis = death.getTime() - birth.getTime();
                ageInYears = ageInMillis / 1000.0 / 3600.0 / 24.0 / 365.0;
            }
        }
        return (int) ageInYears;
    }

    private boolean isYoungerThan(int ageLimit) {
        int deathAge = getAge();
        return deathAge < ageLimit && deathAge != -1;
    }

    public void setInformation(Information info, InfoType lastType) {
        if (info.getType().equals(InfoType.FIRST_NAME)) {
            setFirstName(info.getValue());
        } else if (info.getType().equals(InfoType.SURNAME)) {
            setSurname(info.getValue());
        } else if (info.getType().equals(InfoType.NAME)) {
            setName(info.getValue());
        } else if (info.getType().equals(InfoType.SEX)) {
            setSex(Sex.getSex(info.getValue()));
        } else if (info.getType().equals(InfoType.DEATH)) {
            setLiving(false);
        } else if (info.getType().equals(InfoType.DATE)) {
            if (lastType.equals(InfoType.BIRTH)) {
                setBirthDate(info.getValue());
            } else if (lastType.equals(InfoType.DEATH)) {
                setDeathDate(info.getValue());
            } else if (lastType.equals(InfoType.RESIDENCE)) {
                getLastResidence().setDate(info.getValue());
            }
        } else if (info.getType().equals(InfoType.PLACE)) {
            if (lastType.equals(InfoType.BIRTH)) {
                setBirthPlace(info.getValue());
            } else if (lastType.equals(InfoType.DEATH)) {
                setDeathPlace(info.getValue());
            }
        } else if (info.getType().equals(InfoType.OCCUPATION)) {
            setOccupation(info.getValue());
        } else if (info.getType().equals(InfoType.RESIDENCE)) {
            getResidenceList().add(new Residence());
            getLastResidence().setCity(info.getValue());
        } else if (info.getType().equals(InfoType.CITY)) {
            if (lastType.equals(InfoType.RESIDENCE)) {
                getLastResidence().setCity(info.getValue());
            }
        } else if (info.getType().equals(InfoType.PARENTS)) {
            setParentID(info.getValue());
        } else if (info.getType().equals(InfoType.SPOUSE)) {
            addSpouseID(info.getValue());
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return getName() + "; born: " + getBirthDateCzech();
    }

}
