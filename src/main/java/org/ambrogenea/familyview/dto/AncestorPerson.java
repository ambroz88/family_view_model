package org.ambrogenea.familyview.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.domain.Personalize;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class AncestorPerson implements Personalize {

    public static final int CODE_MALE = -1;
    public static final int CODE_FEMALE = 1;

    private final String id;

    private String firstName;
    private String surname;
    private Sex sex;
    private String birthDate;
    private String birthPlace;
    private String deathDate;
    private String deathPlace;
    private String occupation;
    private boolean living;
    private boolean directLineage;

    private ArrayList<Residence> residenceList;

    private AncestorCouple parents;
    private LinkedList<AncestorCouple> spouses;

    private ArrayList<Integer> ancestorLine;
    private LinkedList<AncestorPerson> youngerSiblings;
    private LinkedList<AncestorPerson> olderSiblings;
    private int maxYoungerSiblingsSpouse;
    private int maxOlderSiblingsSpouse;
    private int ancestorGenerations;
    private double lastParentsCount;
    private double innerParentsCount;
    private int maxOlderSiblings;
    private int maxYoungerSiblings;

    public AncestorPerson(AncestorPerson person) {
        if (person != null) {
            this.id = person.getId();
            this.firstName = person.getFirstName();
            this.surname = person.getSurname();
            this.sex = person.getSex();
            this.living = person.isLiving();
            this.birthDate = person.getBirthDate();
            this.birthPlace = person.getBirthPlace();
            this.deathDate = person.getDeathDate();
            this.deathPlace = person.getDeathPlace();
            this.occupation = person.getOccupation();
            this.residenceList = person.getResidenceList();

            initSiblings();
            directLineage = person.isDirectLineage();
            innerParentsCount = person.getInnerParentsCount();
            this.ancestorLine = new ArrayList<>(person.getAncestorLine());
            this.ancestorGenerations = person.getAncestorGenerations();
            Collections.copy(person.getYoungerSiblings(), youngerSiblings);
            Collections.copy(person.getOlderSiblings(), olderSiblings);
            maxYoungerSiblingsSpouse = person.getMaxYoungerSiblingsSpouse();
            maxOlderSiblingsSpouse = person.getMaxOlderSiblingsSpouse();
            if (person.getSpouseCouple() != null) {
                this.spouses = new LinkedList<>(person.getSpouseCouples());
            } else {
                this.spouses = new LinkedList<>();
            }
        } else {
            this.directLineage = false;
            this.id = "";
            initEmpty();
        }
    }

    public AncestorPerson(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.surname = person.getSurname();
        this.sex = person.getSex();
        this.living = person.isLiving();
        this.birthDate = person.getBirthDate();
        this.birthPlace = person.getBirthPlace();
        this.deathDate = person.getDeathDate();
        this.deathPlace = person.getDeathPlace();
        this.occupation = person.getOccupation();
        this.residenceList = person.getResidenceList();

        fillAncestorLine();
        initEmpty();
    }

    private void initEmpty() {
        residenceList = new ArrayList<>();
        living = false;
        directLineage = true;

        firstName = "";
        surname = "";
        birthDate = "";
        birthPlace = "";
        deathDate = "";
        deathPlace = "";
        occupation = "";

        ancestorGenerations = 0;
        lastParentsCount = 0;
        innerParentsCount = 0;
        ancestorLine = new ArrayList<>();
        spouses = new LinkedList<>();
        residenceList = new ArrayList<>();
        initSiblings();
    }

    private void initSiblings() {
        youngerSiblings = new LinkedList<>();
        olderSiblings = new LinkedList<>();
        maxYoungerSiblingsSpouse = 0;
        maxOlderSiblingsSpouse = 0;
        maxOlderSiblings = 0;
        maxYoungerSiblings = 0;
    }

    private void fillAncestorLine() {
        ancestorLine.clear();
        if (sex.equals(Sex.MALE)) {
            ancestorLine.add(CODE_MALE);
        } else {
            ancestorLine.add(CODE_FEMALE);
        }
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

            if (getMother() == null) {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
            } else if (getMother() != null && getFather().getAncestorGenerations() >= getMother().getAncestorGenerations()) {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
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

            if (getFather() == null) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else if (getFather() != null && getMother().getAncestorGenerations() >= getFather().getAncestorGenerations()) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            }
        }
    }

    public void setParents(AncestorCouple parents) {
        this.parents = parents;
        if (parents != null && !parents.isEmpty()) {
            if (getFather() == null) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else if (getMother() == null) {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
                setMother(Tools.createEmtpyWoman());
            } else if (getMother().getAncestorGenerations() >= getFather().getAncestorGenerations()) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
            }
        }
    }

    public AncestorCouple getParents() {
        return parents;
    }

    public AncestorPerson getFather() {
        return parents.getHusband();
    }

    public AncestorPerson getMother() {
        return parents.getWife();
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

    public void setInnerParentsCount(double parentsCount) {
        this.innerParentsCount = parentsCount;
    }

    public ArrayList<Integer> getAncestorLine() {
        return ancestorLine;
    }

    public void addChildrenCode(ArrayList<Integer> childrenCodes) {
        ancestorLine.addAll(0, childrenCodes);
    }

    public void addAncestorCode(int code) {
        ancestorLine.add(code);
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

    public int getAllSiblingsCount() {
        return this.getOlderSiblings().size() + this.getYoungerSiblings().size();
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

    public LinkedList<AncestorCouple> getSpouseCouples() {
        return spouses;
    }

    public void setSpouseCouples(LinkedList<AncestorCouple> spouses) {
        this.spouses = spouses;
    }

    public void addSpouseCouple(AncestorCouple spouse) {
        if (spouse != null) {
            if (!this.spouses.isEmpty()) {
                AncestorCouple lastCouple = this.spouses.get(spouses.size() - 1);
                if (Tools.isEarlier(spouse.getMarriageDateEnglish(), lastCouple.getMarriageDateEnglish())) {
                    this.spouses.add(this.spouses.size() - 1, new AncestorCouple(spouse));
                } else {
                    this.spouses.add(new AncestorCouple(spouse));
                }
            } else {
                this.spouses.add(new AncestorCouple(spouse));
            }
        }
    }

    public int getChildrenCount(int wifeNumber) {
        int count = 0;
        if (getSpouseCouple(wifeNumber) != null) {
            count = getSpouseCouple(wifeNumber).getChildrenIndexes().size();
        }
        return count;
    }

    public int getAllChildrenCount() {
        int count = 0;
        for (AncestorCouple spouse : getSpouseCouples()) {
            count = count + spouse.getChildrenIndexes().size();
        }
        return count;
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

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getSimpleBirthPlace() {
        return birthPlace.split(",")[0];
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public String getSimpleDeathPlace() {
        return deathPlace.split(",")[0];
    }

    public String getOccupation() {
        return occupation;
    }

    public boolean isLiving() {
        return living;
    }

    public ArrayList<Residence> getResidenceList() {
        return residenceList;
    }

    @Override
    public String toString() {
        return getName() + " *" + getBirthDate() + "; parents: " + getParents();
    }

}
