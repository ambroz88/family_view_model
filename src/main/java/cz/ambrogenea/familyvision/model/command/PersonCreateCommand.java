package cz.ambrogenea.familyvision.model.command;

import cz.ambrogenea.familyvision.enums.Sex;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonCreateCommand {
    final private Long familyTreeId;
    private String gedcomId;
    private String firstName;
    private String surname;
    private Sex sex;
    private boolean living;
    private String occupation;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private List<ResidenceCreateCommand> residenceList;

    public PersonCreateCommand(Long familyTreeId) {
        this.familyTreeId = familyTreeId;
    }

    public Long getFamilyTreeId() {
        return familyTreeId;
    }

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

    public List<ResidenceCreateCommand> getResidenceList() {
        return residenceList;
    }

    public void setResidenceList(List<ResidenceCreateCommand> residenceList) {
        this.residenceList = residenceList;
    }
}
