package cz.ambrogenea.familyvision.model.command;

import cz.ambrogenea.familyvision.enums.Sex;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonCreateCommand {
    private String gedcomId;
    private String firstName;
    private String surname;
    private Sex sex;
    private boolean living;
    private String occupation;
    private DatePlaceCreateCommand birthDatePlace;
    private DatePlaceCreateCommand deathDatePlace;
    private List<ResidenceCreateCommand> residenceList;

    public String getGedcomId() {
        return gedcomId;
    }

    public void setGedcomId(String gedcomId) {
        this.gedcomId = gedcomId;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public DatePlaceCreateCommand getBirthDatePlace() {
        return birthDatePlace;
    }

    public void setBirthDatePlace(DatePlaceCreateCommand birthDatePlace) {
        this.birthDatePlace = birthDatePlace;
    }

    public DatePlaceCreateCommand getDeathDatePlace() {
        return deathDatePlace;
    }

    public void setDeathDatePlace(DatePlaceCreateCommand deathDatePlace) {
        this.deathDatePlace = deathDatePlace;
    }

    public List<ResidenceCreateCommand> getResidenceList() {
        return residenceList;
    }

    public void setResidenceList(List<ResidenceCreateCommand> residenceList) {
        this.residenceList = residenceList;
    }
}
