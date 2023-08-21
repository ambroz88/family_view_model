package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Person {

    final private Long id;
    private String gedcomId;
    private Long familyTreeId;
    private String firstName;
    private String surname;
    private Sex sex;
    private boolean living;
    private String birthDate;
    private Long birthPlaceId;
    private String deathDate;
    private Long deathPlaceId;
    private String occupation;
    private List<Residence> residences;
    private List<Long> spouseId;
    private Long parentId;

    public Person() {
        this.id = IdGenerator.generate(Person.class.getSimpleName());
        residences = new ArrayList<>();
        spouseId = new ArrayList<>();
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

    public void setGedcomId(String gedcomId) {
        this.gedcomId = gedcomId;
    }

    public Long getFamilyTreeId() {
        return familyTreeId;
    }

    public void setFamilyTreeId(Long familyTreeId) {
        this.familyTreeId = familyTreeId;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Long getBirthPlaceId() {
        return birthPlaceId;
    }

    public void setBirthPlaceId(Long birthPlaceId) {
        this.birthPlaceId = birthPlaceId;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public Long getDeathPlaceId() {
        return deathPlaceId;
    }

    public void setDeathPlaceId(Long deathPlaceId) {
        this.deathPlaceId = deathPlaceId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<Residence> getResidences() {
        return residences;
    }

    public void setResidences(List<Residence> residences) {
        this.residences = residences;
    }

    public List<Long> getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(List<Long> spouseId) {
        this.spouseId = spouseId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return getName();
    }

}
