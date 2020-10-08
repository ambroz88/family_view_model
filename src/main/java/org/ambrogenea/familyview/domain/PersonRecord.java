package org.ambrogenea.familyview.domain;

import org.ambrogenea.familyview.model.Person;
import org.ambrogenea.familyview.enums.Sex;

public class PersonRecord {

    private final String id;
    private final String firstName;
    private final String surname;
    private final Sex sex;
    private final String birthDate;
    private final String birthPlace;
    private final String simpleBirthPlace;
    private final String deathDate;
    private final String deathPlace;
    private final String simpleDeathPlace;
    private final String occupation;
    private final boolean living;
    private final Position position;

    public PersonRecord(Person person, Position position) {
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

    public Sex getSex() {
        return sex;
    }

    public String getBirthDate() {
        return birthDate;
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

    public Position getPosition() {
        return position;
    }
}
