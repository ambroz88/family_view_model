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
    final private Long birthPlaceId;
    final private String deathDate;
    final private Long deathPlaceId;
    final private String occupation;
    final private List<Residence> residences;
    final private List<Long> spouseId;
    private Long parentId;
    private Long fatherId;
    private Long motherId;

    public Person(String gedcomId, Long familyTreeId, String firstName, String surname, Sex sex, boolean living,
                  String birthDate, Long birthPlaceId, String deathDate, Long deathPlaceId, String occupation,
                  List<Residence> residences, List<Long> spouseId) {
        this.id = IdGenerator.generate(Person.class.getSimpleName());
        this.gedcomId = gedcomId;
        this.familyTreeId = familyTreeId;
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.living = living;
        this.birthDate = birthDate;
        this.birthPlaceId = birthPlaceId;
        this.deathDate = deathDate;
        this.deathPlaceId = deathPlaceId;
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

    public Long getBirthPlaceId() {
        return birthPlaceId;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public Long getDeathPlaceId() {
        return deathPlaceId;
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
