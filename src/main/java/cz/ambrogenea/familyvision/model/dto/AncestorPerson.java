package cz.ambrogenea.familyvision.model.dto;

import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.enums.Sex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiri Ambroz
 */
public class AncestorPerson {

    private final Long id;
    private final String gedcomId;
    private final String firstName;
    private final String surname;
    private final Sex sex;
    private final boolean living;
    private final DatePlaceDto birthDatePlace;
    private final DatePlaceDto deathDatePlace;
    private final String occupation;
    private final List<Residence> residences;
    private final LinkedList<AncestorPerson> youngerSiblings;
    private final LinkedList<AncestorPerson> olderSiblings;

    private boolean directLineage;
    private List<AncestorCouple> spouses;
    private AncestorCouple parents;
    private int ancestorGenerations;
    private int fatherGenerations;
    private int motherGenerations;
    private double lastParentsCount;
    private double innerParentsCount;
    private int maxYoungerSiblingsSpouse;
    private int maxOlderSiblingsSpouse;
    private int maxOlderSiblings;
    private int maxYoungerSiblings;

    public AncestorPerson(Long id, String gedcomId, String firstName, String surname, Sex sex, boolean living,
                          DatePlaceDto birthDatePlace, DatePlaceDto deathDatePlace, String occupation,
                          List<Residence> residences) {
        this.id = id;
        this.gedcomId = gedcomId;
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.living = living;
        this.birthDatePlace = birthDatePlace;
        this.deathDatePlace = deathDatePlace;
        this.occupation = occupation;
        this.residences = residences;
        this.spouses = new ArrayList<>();
        this.youngerSiblings = new LinkedList<>();
        this.olderSiblings = new LinkedList<>();
        initEmpty();
    }

    private void initEmpty() {
        directLineage = true;
        parents = new AncestorCouple();
        ancestorGenerations = 0;
        fatherGenerations = 0;
        motherGenerations = 0;
        lastParentsCount = 0;
        innerParentsCount = 0;

        maxYoungerSiblingsSpouse = 0;
        maxOlderSiblingsSpouse = 0;
        maxOlderSiblings = 0;
        maxYoungerSiblings = 0;
    }


    public void setFather(AncestorPerson father) {
        parents.setHusband(father);

        if (getFather() != null) {
            double fatherParentsCount = getFather().getLastParentsCount();
            if (fatherParentsCount == 0) {
                addLastParentsCount(0.5);
            } else {
                addLastParentsCount(fatherParentsCount);
            }

            if (getSex().equals(Sex.FEMALE)) {
                innerParentsCount = getFather().getLastParentsCount();
            }

            fatherGenerations = fatherGenerations + Math.max(1, father.getFatherGenerations());
            if (getMother() == null) {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
            } else if (getMother() != null) {

                if (getFather().getAncestorGenerations() >= getMother().getAncestorGenerations()) {
                    ancestorGenerations = getFather().getAncestorGenerations() + 1;
                }
                motherGenerations = motherGenerations + Math.max(1, getMother().getMotherGenerations());
            }

            setMaxOlderSiblings(getFather().getMaxOlderSiblings());
            setMaxOlderSiblingsSpouse(getFather().getMaxOlderSiblingsSpouse());
            setMaxYoungerSiblings(getFather().getMaxYoungerSiblings());
            setMaxYoungerSiblingsSpouse(getFather().getMaxYoungerSiblingsSpouse());
        }
    }

    public void setMother(AncestorPerson mother) {
        parents.setWife(mother);

        if (getMother() != null) {
            double motherParentsCount = getMother().getLastParentsCount();
            if (motherParentsCount == 0) {
                if (getFather() == null) {
                    addLastParentsCount(1);
                } else {
                    addLastParentsCount(0.5);
                }
            } else {
                addLastParentsCount(motherParentsCount);
            }

            if (getSex().equals(Sex.MALE)) {
                innerParentsCount = getMother().getLastParentsCount();
            }

            motherGenerations = motherGenerations + Math.max(1, mother.getMotherGenerations());
            if (getFather() == null) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else if (getFather() != null) {

                if (getMother().getAncestorGenerations() >= getFather().getAncestorGenerations()) {
                    ancestorGenerations = getMother().getAncestorGenerations() + 1;
                }
                fatherGenerations = fatherGenerations + Math.max(1, getFather().getFatherGenerations());
            }
        }
    }

    public AncestorCouple getParents() {
        return parents;
    }

    public void setParents(AncestorCouple parents) {
        this.parents = parents;
        if (parents != null && !parents.isEmpty()) {
            if (getFather() == null) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else if (getMother() != null) {
                if (getMother().getAncestorGenerations() >= getFather().getAncestorGenerations()) {
                    ancestorGenerations = getMother().getAncestorGenerations() + 1;
                } else {
                    ancestorGenerations = getFather().getAncestorGenerations() + 1;
                }
            }
        }
    }

    public AncestorPerson getFather() {
        if (parents == null) {
            return null;
        }

        return parents.getHusband();
    }

    public AncestorPerson getMother() {
        if (parents == null) {
            return null;
        }
        return parents.getWife();
    }

    public boolean hasMinOneParent() {
        return getFather() != null || getMother() != null;
    }

    public boolean hasBothParents() {
        return getFather() != null && getMother() != null;
    }

    public boolean isDirectLineage() {
        return directLineage;
    }

    public void setDirectLineage(boolean directLineage) {
        this.directLineage = directLineage;
    }

    public int getAncestorGenerations() {
        return ancestorGenerations;
    }

    public int getFatherGenerations() {
        return fatherGenerations;
    }

    public int getMotherGenerations() {
        return motherGenerations;
    }

    public double getLastParentsCount() {
        return lastParentsCount;
    }

    public void addLastParentsCount(double lastParentsCount) {
        this.lastParentsCount = getLastParentsCount() + lastParentsCount;
    }

    public double getInnerParentsCount() {
        if (innerParentsCount == 0) {
            return 0.5;
        }
        return innerParentsCount;
    }

    public LinkedList<AncestorPerson> getYoungerSiblings() {
        return youngerSiblings;
    }

    public void addYoungerSibling(AncestorPerson youngerSibling) {
        this.youngerSiblings.add(youngerSibling);
    }

    public LinkedList<AncestorPerson> getOlderSiblings() {
        return olderSiblings;
    }

    public void addOlderSibling(AncestorPerson olderSibling) {
        this.olderSiblings.add(olderSibling);
    }

    public int getMaxYoungerSiblingsSpouse() {
        return maxYoungerSiblingsSpouse;
    }

    public void setMaxYoungerSiblingsSpouse(int maxYoungerSiblingsSpouse) {
        this.maxYoungerSiblingsSpouse = Math.max(maxYoungerSiblingsSpouse, getMaxYoungerSiblingsSpouse());
    }

    public void addYoungerSiblingsSpouse() {
        maxYoungerSiblingsSpouse++;
    }

    public int getMaxOlderSiblingsSpouse() {
        return maxOlderSiblingsSpouse;
    }

    public void setMaxOlderSiblingsSpouse(int maxOlderSiblingsSpouse) {
        this.maxOlderSiblingsSpouse = Math.max(maxOlderSiblingsSpouse, getMaxOlderSiblingsSpouse());
    }

    public void addOlderSiblingsSpouse() {
        maxOlderSiblingsSpouse++;
    }

    public int getMaxOlderSiblings() {
        return maxOlderSiblings;
    }

    public void setMaxOlderSiblings(int maxOlderSiblings) {
        this.maxOlderSiblings = Math.max(maxOlderSiblings, getOlderSiblings().size());
    }

    public int getMaxYoungerSiblings() {
        return maxYoungerSiblings;
    }

    public void setMaxYoungerSiblings(int maxYoungerSiblings) {
        this.maxYoungerSiblings = Math.max(maxYoungerSiblings, getYoungerSiblings().size());
    }

    public void moveOlderSiblingsToYounger() {
        getYoungerSiblings().addAll(0, getOlderSiblings());
        getOlderSiblings().clear();
        setMaxYoungerSiblings(getYoungerSiblings().size());
        setMaxYoungerSiblingsSpouse(getMaxYoungerSiblingsSpouse() + getMaxOlderSiblingsSpouse());
    }

    public void moveYoungerSiblingsToOlder() {
        getOlderSiblings().addAll(getYoungerSiblings());
        getYoungerSiblings().clear();
        setMaxOlderSiblings(getOlderSiblings().size());
        setMaxOlderSiblingsSpouse(getMaxOlderSiblingsSpouse() + getMaxYoungerSiblingsSpouse());
    }

    public AncestorPerson getSpouse() {
        if (getSpouseCouple() != null) {
            if (getSex().equals(Sex.MALE)) {
                return getSpouseCouple().getWife();
            } else {
                return getSpouseCouple().getHusband();
            }
        } else {
            return null;
        }
    }

    public AncestorPerson getSpouse(int index) {
        if (getSpouseCouple(index) != null) {
            if (getSex().equals(Sex.MALE)) {
                return getSpouseCouple(index).getWife();
            } else {
                return getSpouseCouple(index).getHusband();
            }
        } else {
            return null;
        }
    }

    public void setSpouses(List<AncestorCouple> spouses) {
        this.spouses = spouses;
    }

    public AncestorCouple getSpouseCouple() {
        if (spouses.isEmpty()) {
            return null;
        } else {
            return spouses.get(0);
        }
    }

    public AncestorCouple getSpouseCouple(int index) {
        if (spouses.isEmpty() || index >= spouses.size()) {
            return null;
        } else {
            return spouses.get(index);
        }
    }

    public List<AncestorCouple> getSpouseCouples() {
        return spouses;
    }

    public int getAllChildrenCount() {
        int count = 0;
        for (AncestorCouple spouse : getSpouseCouples()) {
            count = count + spouse.getChildren().size();
        }
        return count;
    }

    public Long getId() {
        return id;
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

    public DatePlaceDto getBirthDatePlace() {
        return birthDatePlace;
    }

    public DatePlaceDto getDeathDatePlace() {
        return deathDatePlace;
    }

    public String getOccupation() {
        return occupation;
    }

    public boolean isLiving() {
        return living;
    }

    public List<Residence> getResidences() {
        return residences;
    }

    @Override
    public String toString() {
        return getName() + " *" + getBirthDatePlace().getDate() + "; parents: " + getParents();
    }

}
