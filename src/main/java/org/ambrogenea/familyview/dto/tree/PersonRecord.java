package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.utils.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonRecord {

    private final String id;
    private final Sex sex;
    private final boolean directLineage;
    private final ArrayList<Residence> residences;

    private String firstName;
    private String surname;
    private String birthDate;
    private String birthPlace;
    private String simpleBirthPlace;
    private String deathDate;
    private String deathPlace;
    private String simpleDeathPlace;
    private String occupation;
    private boolean living;
    private Position position;

    public PersonRecord(Sex sex, boolean directLineage) {
        this.id = "N/A";
        this.sex = sex;
        this.firstName = "";
        this.surname = "";
        this.birthDate = "";
        this.birthPlace = "";
        this.simpleBirthPlace = "";
        this.deathDate = "";
        this.deathPlace = "";
        this.simpleDeathPlace = "";
        this.occupation = "";
        this.directLineage = directLineage;
        this.residences = new ArrayList<>();
    }

    public PersonRecord(AncestorPerson person, Position position) {
        this.position = position;
        id = person.getId();
        firstName = person.getFirstName();
        surname = person.getSurname();
        sex = person.getSex();
        birthDate = person.getBirthDate();
        birthPlace = person.getBirthPlace();
        simpleBirthPlace = person.getSimpleBirthPlace();
        deathDate = person.getDeathDate();
        deathPlace = person.getDeathPlace();
        simpleDeathPlace = person.getSimpleDeathPlace();
        occupation = person.getOccupation();
        living = person.isLiving();
        directLineage = person.isDirectLineage();
        residences = new ArrayList<>(person.getResidenceList());
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        if (getFirstName().isEmpty()) {
            return getSurname();
        } else if (getSurname().isEmpty()) {
            return getFirstName();
        }
        return getFirstName() + " " + getSurname();
    }

    public Sex getSex() {
        return sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBirthDateCzech() {
        return Tools.translateDateToCzech(birthDate);
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getSimpleBirthPlace() {
        return simpleBirthPlace;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getDeathDateCzech() {
        return Tools.translateDateToCzech(deathDate);
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public String getSimpleDeathPlace() {
        return simpleDeathPlace;
    }

    public String getOccupation() {
        return occupation;
    }

    public boolean isLiving() {
        return living;
    }

    public boolean isDirectLineage() {
        return directLineage;
    }

    public Position getPosition() {
        return position;
    }

    public List<Residence> getResidences() {
        return residences;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public void setSimpleBirthPlace(String simpleBirthPlace) {
        this.simpleBirthPlace = simpleBirthPlace;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public void setSimpleDeathPlace(String simpleDeathPlace) {
        this.simpleDeathPlace = simpleDeathPlace;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
