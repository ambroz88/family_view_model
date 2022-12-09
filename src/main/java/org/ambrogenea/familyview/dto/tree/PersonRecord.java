package org.ambrogenea.familyview.dto.tree;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.ambrogenea.familyview.domain.DatePlace;
import org.ambrogenea.familyview.domain.Personalize;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.enums.Sex;

public class PersonRecord implements Personalize {

    private final String id;
    private final Sex sex;
    private final boolean directLineage;
    private final ArrayList<Residence> residences;

    private String firstName;
    private String surname;
    private DatePlace birthDatePlace;
    private DatePlace deathDatePlace;
    private String occupation;
    private boolean living;
    private Position position;

    public PersonRecord(Sex sex, boolean directLineage) {
        this.id = "N/A";
        this.sex = sex;
        this.firstName = "";
        this.surname = "";
        this.birthDatePlace = new DatePlace();
        this.deathDatePlace = new DatePlace();
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
        birthDatePlace = person.getBirthDatePlace();
        deathDatePlace = person.getDeathDatePlace();
        occupation = person.getOccupation();
        living = person.isLiving();
        directLineage = person.isDirectLineage();
        residences = new ArrayList<>(person.getResidenceList());
    }

    public String getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getSurname() {
        return surname;
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

    @Override
    public Sex getSex() {
        return sex;
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
        int ageInYears = -1;
        if (getBirthDatePlace().getDate() != null && getDeathDatePlace().getDate() != null) {
            ageInYears = Period.between(getBirthDatePlace().getDate(),
                    getDeathDatePlace().getDate()).getYears();
        }
        return ageInYears;
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
