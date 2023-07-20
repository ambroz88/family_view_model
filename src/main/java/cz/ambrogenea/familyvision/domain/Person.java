package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.enums.Sex;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    final private String gedcomId;
    final private Long familyTreeId;
    final private String firstName;
    final private String surname;
    final private Sex sex;
    final private boolean living;
    final private String birthDate;
    final private String birthPlace;
    final private String deathDate;
    final private String deathPlace;
    final private String occupation;
    final private List<Residence> residences;
    final private List<String> spouseId;
    private String parentId;
    private String fatherId;
    private String motherId;


    public Person(String gedcomId, Long familyTreeId, String firstName, String surname, Sex sex, boolean living,
                  String birthDate, String birthPlace, String deathDate, String deathPlace, String occupation,
                  List<Residence> residences, List<String> spouseId) {
        this.gedcomId = gedcomId;
        this.familyTreeId = familyTreeId;
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.living = living;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.deathDate = deathDate;
        this.deathPlace = deathPlace;
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

    public Long getFamilyTreeId() {
        return familyTreeId;
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

    public String getBirthDate() {
        return birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getDeathPlace() {
        return deathPlace;
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
