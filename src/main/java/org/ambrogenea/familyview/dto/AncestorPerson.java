package org.ambrogenea.familyview.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.ambrogenea.familyview.domain.DatePlace;
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

    private final String firstName;
    private final String surname;
    private Sex sex;
    private DatePlace birthDatePlace;
    private DatePlace deathDatePlace;
    private final String occupation;
    private boolean living;
    private boolean directLineage;

    private final ArrayList<Residence> residenceList;

    private AncestorCouple parents;
    private List<AncestorCouple> spouses;

    private ArrayList<Integer> ancestorLine;
    private LinkedList<AncestorPerson> youngerSiblings;
    private LinkedList<AncestorPerson> olderSiblings;
    private int maxYoungerSiblingsSpouse;
    private int maxOlderSiblingsSpouse;
    private int ancestorGenerations;
    private int fatherGenerations;
    private int motherGenerations;
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
            this.birthDatePlace = person.getBirthDatePlace();
            this.deathDatePlace = person.getDeathDatePlace();
            this.occupation = person.getOccupation();
            this.residenceList = new ArrayList<>(person.getResidenceList());

            initSiblings();
            directLineage = person.isDirectLineage();
            innerParentsCount = person.getInnerParentsCount();
            this.ancestorLine = new ArrayList<>(person.getAncestorLine());
            this.ancestorGenerations = person.getAncestorGenerations();
            this.fatherGenerations = person.getFatherGenerations();
            this.motherGenerations = person.getMotherGenerations();
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
            firstName = "";
            surname = "";
            birthDatePlace = new DatePlace();
            deathDatePlace = new DatePlace();
            occupation = "";
            residenceList = new ArrayList<>();
            initEmpty();
        }
    }

    public AncestorPerson(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        if (person.getMarriageName() != null && person.getSurname() == null) {
            this.surname = person.getMarriageName();
        } else {
            this.surname = person.getSurname();
        }
        this.sex = person.getSex();
        this.living = person.isLiving();
        this.birthDatePlace = person.getBirthDatePlace();
        this.deathDatePlace = person.getDeathDatePlace();
        this.occupation = person.getOccupation();
        this.residenceList = new ArrayList<>(person.getResidenceList());

        initEmpty();
        fillAncestorLine();
    }

    private void initEmpty() {
        living = false;
        directLineage = true;

        ancestorGenerations = 0;
        fatherGenerations = 0;
        motherGenerations = 0;
        lastParentsCount = 0;
        innerParentsCount = 0;
        ancestorLine = new ArrayList<>();
        spouses = new LinkedList<>();

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

    public boolean hasNoParents() {
        return getFather() == null && getMother() == null;
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

    public int getSpouseCount() {
        int count = 0;
        for (AncestorCouple spouse : spouses) {
            if (!spouse.isSingle()) {
                count++;
            }
        }
        return count;
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

    public void setSpouseCouples(List<AncestorCouple> spouses) {
        this.spouses = new ArrayList<>(spouses);
    }

//    public void addSpouseCouple(AncestorCouple spouse) {
//        if (spouse != null) {
//            if (!this.spouses.isEmpty()) {
//                AncestorCouple lastCouple = this.spouses.get(spouses.size() - 1);
//                if (spouse.getDatePlace().getDate().isBefore(lastCouple.getDatePlace().getDate())) {
//                    this.spouses.add(this.spouses.size() - 1, new AncestorCouple(spouse));
//                } else {
//                    this.spouses.add(new AncestorCouple(spouse));
//                }
//            } else {
//                this.spouses.add(new AncestorCouple(spouse));
//            }
//        }
//    }
    public int getChildrenCount(int wifeNumber) {
        int count = 0;
        if (getSpouseCouple(wifeNumber) != null) {
            count = getSpouseCouple(wifeNumber).getChildren().size();
        }
        return count;
    }

    public int getAllChildrenCount() {
        int count = 0;
        for (AncestorCouple spouse : getSpouseCouples()) {
            count = count + spouse.getChildren().size();
        }
        return count;
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

    public ArrayList<Residence> getResidenceList() {
        return residenceList;
    }

    @Override
    public String toString() {
        return getName() + " *" + getBirthDatePlace().getDate() + "; parents: " + getParents();
    }

}
