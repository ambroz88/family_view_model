package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.ambrogenea.familyview.model.enums.Sex;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class AncestorPerson extends Person {

    public static final int CODE_MALE = -1;
    public static final int CODE_FEMALE = 1;

    private ArrayList<Integer> ancestorLine;
    private LinkedList<AncestorPerson> youngerSiblings;
    private LinkedList<AncestorPerson> olderSiblings;
    private LinkedList<Couple> spouses;
    private boolean directLineage;
    private int ancestorGenerations;
    private double lastParentsCount;
    private double innerParentsCount;
    private int maxOlderSiblings;
    private int maxYoungerSiblings;

    public AncestorPerson(AncestorPerson person) {
        super(person);

        if (person != null) {
            initSiblings();
            directLineage = person.isDirectLineage();
            innerParentsCount = person.getInnerParentsCount();
            this.ancestorLine = new ArrayList<>(person.getAncestorLine());
            this.ancestorGenerations = person.getAncestorGenerations();
            Collections.copy(person.getYoungerSiblings(), youngerSiblings);
            Collections.copy(person.getOlderSiblings(), olderSiblings);
            if (person.getSpouseCouple() != null) {
                this.spouses = new LinkedList(person.getSpouseCouples());
            } else {
                this.spouses = new LinkedList<>();
            }
        } else {
            directLineage = false;
            initEmpty();
        }
    }

    public AncestorPerson(Person person, boolean lineage) {
        super(person);
        this.directLineage = lineage;
        initEmpty();
        this.setSex(person.getSex());
    }

    public AncestorPerson(String id, boolean lineage) {
        super(id);
        this.directLineage = lineage;
        initEmpty();
    }

    private void initEmpty() {
        ancestorGenerations = 0;
        lastParentsCount = 0;
        innerParentsCount = 0;
        ancestorLine = new ArrayList<>();
        spouses = new LinkedList<>();
        initSiblings();
    }

    private void initSiblings() {
        youngerSiblings = new LinkedList<>();
        olderSiblings = new LinkedList<>();
        maxOlderSiblings = 0;
        maxYoungerSiblings = 0;
    }

    @Override
    public void setSex(Sex sex) {
        super.setSex(sex);
        ancestorLine.clear();
        if (sex.equals(Sex.MALE)) {
            ancestorLine.add(CODE_MALE);
        } else {
            ancestorLine.add(CODE_FEMALE);
        }
    }

    @Override
    public void setFather(AncestorPerson father) {
        super.setFather(father);

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
        }
    }

    @Override
    public void setMother(AncestorPerson mother) {
        super.setMother(mother);

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

    @Override
    public void setParents(Couple parents) {
        super.setParents(parents);
        if (parents != null && !parents.isEmpty()) {
            if (getFather() == null) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else if (getMother() == null) {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
                AncestorPerson mother = new AncestorPerson("000", true);
                mother.setSex(Sex.FEMALE);
                setMother(mother);
            } else if (getMother().getAncestorGenerations() >= getFather().getAncestorGenerations()) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
            }
        }
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

    public Couple getSpouseCouple() {
        if (spouses.isEmpty()) {
            return null;
        } else {
            return spouses.get(0);
        }
    }

    public Couple getSpouseCouple(int index) {
        if (spouses.isEmpty() || index >= spouses.size()) {
            return null;
        } else {
            return spouses.get(index);
        }
    }

    public LinkedList<Couple> getSpouseCouples() {
        return spouses;
    }

    public void addSpouseCouple(Couple spouse) {
        if (spouse != null) {
            if (!this.spouses.isEmpty()) {
                Couple lastCouple = this.spouses.get(spouses.size() - 1);
                if (Tools.isEarlier(spouse.getMarriageDateEnglish(), lastCouple.getMarriageDateEnglish())) {
                    this.spouses.add(this.spouses.size() - 1, new Couple(spouse));
                } else {
                    this.spouses.add(new Couple(spouse));
                }
            } else {
                this.spouses.add(new Couple(spouse));
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
        for (Couple spouse : getSpouseCouples()) {
            count = count + spouse.getChildrenIndexes().size();
        }
        return count;
    }

    @Override
    public String toString() {
        return getName() + " *" + getBirthDate() + "; parents: " + getParents();
    }

}
