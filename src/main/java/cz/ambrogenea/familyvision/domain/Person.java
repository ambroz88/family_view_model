package cz.ambrogenea.familyvision.domain;

import java.util.ArrayList;

import cz.ambrogenea.familyvision.dto.parsing.Information;
import cz.ambrogenea.familyvision.enums.InfoType;
import cz.ambrogenea.familyvision.enums.Sex;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person implements Personalize {

    private final String id;

    private String firstName;
    private String surname;
    private String marriageName;
    private Sex sex;
    private DatePlace birthDatePlace;
    private DatePlace deathDatePlace;
    private String occupation;
    private String parentID;
    private boolean living;
    private ArrayList<String> spouseID;
    private ArrayList<Residence> residenceList;

    private String fatherId;
    private String motherId;

    public Person(String id) {
        this.id = id;
        initEmpty();
    }

    public Person(Person person) {
        if (person != null) {
            this.id = person.getId();

            this.firstName = person.getFirstName();
            this.surname = person.getSurname();
            this.sex = person.getSex();
            this.living = person.isLiving();
            this.birthDatePlace = person.getBirthDatePlace();
            this.deathDatePlace = person.getDeathDatePlace();
            this.occupation = person.getOccupation();
            this.residenceList = new ArrayList<>(person.getResidenceList());
            this.fatherId = person.getFatherId();
            this.motherId = person.getMotherId();
            this.parentID = person.getParentID();
            this.spouseID = person.getSpouseID();

        } else {
            this.id = "";
            initEmpty();
        }
    }

    private void initEmpty() {
        spouseID = new ArrayList<>();
        residenceList = new ArrayList<>();
        living = true;

        firstName = "";
        surname = "";
        birthDatePlace = new DatePlace();
        deathDatePlace = new DatePlace();
        occupation = "";

        fatherId = "";
        motherId = "";
    }

    public String getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMarriageName() {
        return marriageName;
    }

    public void setMarriageName(String marriageName) {
        this.marriageName = marriageName;
    }

    @Override
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

    @Override
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public DatePlace getBirthDatePlace() {
        return birthDatePlace;
    }

    public void setBirthDatePlace(DatePlace birthDatePlace) {
        this.birthDatePlace = birthDatePlace;
    }

    public DatePlace getDeathDatePlace() {
        return deathDatePlace;
    }

    public void setDeathDatePlace(DatePlace deathDatePlace) {
        this.deathDatePlace = deathDatePlace;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public boolean isLiving() {
        return living;
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
        this.parentID = parentID;
    }

    public ArrayList<String> getSpouseID() {
        return spouseID;
    }

    public void addSpouseID(String spouseID) {
        getSpouseID().add(spouseID);
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public void setInformation(Information info, InfoType lastType) {
        if (info.getType().equals(InfoType.FIRST_NAME)) {
            setFirstName(info.getValue());
        } else if (info.getType().equals(InfoType.SURNAME)) {
            setSurname(info.getValue());
        } else if (info.getType().equals(InfoType.NAME)) {
            setName(info.getValue());
        } else if (info.getType().equals(InfoType.MARRIAGE_NAME)) {
            setMarriageName(info.getValue());
        } else if (info.getType().equals(InfoType.SEX)) {
            setSex(Sex.getSex(info.getValue()));
        } else if (info.getType().equals(InfoType.DEATH)) {
            setLiving(false);
        } else if (info.getType().equals(InfoType.DATE)) {
            if (lastType.equals(InfoType.BIRTH)) {
                getBirthDatePlace().parseDateText(info.getValue());
            } else if (lastType.equals(InfoType.DEATH)) {
                if (!info.getValue().equals("DECEASED")) {
                    getDeathDatePlace().parseDateText(info.getValue());
                }
            } else if (lastType.equals(InfoType.RESIDENCE)) {
                getLastResidence().setDate(info.getValue());
            }
        } else if (info.getType().equals(InfoType.PLACE)) {
            if (lastType.equals(InfoType.BIRTH)) {
                getBirthDatePlace().setPlace(info.getValue());
            } else if (lastType.equals(InfoType.DEATH)) {
                getDeathDatePlace().setPlace(info.getValue());
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
            setParentID(info.getValue().replace(Information.MARKER, ""));
        } else if (info.getType().equals(InfoType.SPOUSE)) {
            addSpouseID(info.getValue().replace(Information.MARKER, ""));
        }
    }

    @Override
    public String toString() {
        return getName();
    }

}
