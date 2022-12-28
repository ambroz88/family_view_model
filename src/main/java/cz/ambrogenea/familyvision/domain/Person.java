package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.enums.Sex;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    final private String gedcomId;
    final private String firstName;
    final private String surname;
    final private Sex sex;
    final private boolean living;
    final private DatePlace birthDatePlace;
    final private DatePlace deathDatePlace;
    final private String occupation;
    final private List<Residence> residences;
    final private List<String> spouseId;
    private String parentId;
    private String fatherId;
    private String motherId;


    public Person(String gedcomId, String firstName, String surname, Sex sex, boolean living,
                  DatePlace birthDatePlace, DatePlace deathDatePlace, String occupation,
                  List<Residence> residences, List<String> spouseId) {
        this.gedcomId = gedcomId;
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.living = living;
        this.birthDatePlace = birthDatePlace;
        this.deathDatePlace = deathDatePlace;
        this.occupation = occupation;
        this.residences = residences;
        this.spouseId = spouseId;
    }

    public String getName() {
        if (getFirstName().isEmpty()) {
            return getSurname();
        } else if (getSurname().isEmpty()) {
            return getFirstName();
        }
        return getFirstName() + " " + getSurname();
    }

    public String getGedcomId() {
        return gedcomId;
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

    public boolean isLiving() {
        return living;
    }

    public DatePlace getBirthDatePlace() {
        return birthDatePlace;
    }

    public DatePlace getDeathDatePlace() {
        return deathDatePlace;
    }

    public String getOccupation() {
        return occupation;
    }

    public List<Residence> getResidences() {
        return residences;
    }

    public List<String> getSpouseId() {
        return spouseId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    @Override
    public String toString() {
        return getName();
    }

}
