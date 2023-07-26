package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.utils.IdGenerator;

import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    final private Long id;
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
    final private List<Long> spouseId;
    private Long parentId;
    private Long fatherId;
    private Long motherId;

    public Person(String gedcomId, Long familyTreeId, String firstName, String surname, Sex sex, boolean living,
                  String birthDate, String birthPlace, String deathDate, String deathPlace, String occupation,
                  List<Residence> residences, List<Long> spouseId) {
        this.id = IdGenerator.generate(Person.class.getSimpleName());
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

    public Long getId() {
        return id;
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

    public List<Long> getSpouseId() {
        return spouseId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Long getMotherId() {
        return motherId;
    }

    public void setMotherId(Long motherId) {
        this.motherId = motherId;
    }

    @Override
    public String toString() {
        return getName();
    }

}
