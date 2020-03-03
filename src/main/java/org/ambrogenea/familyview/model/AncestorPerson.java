package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.Collections;

import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class AncestorPerson extends Person {

    public static final int CODE_MALE = -1;
    public static final int CODE_FEMALE = 1;

    private ArrayList<Integer> ancestorLine;
    private ArrayList<Person> youngerSiblings;
    private ArrayList<Person> olderSiblings;
    private ArrayList<Couple> spouses;
    private int ancestorGenerations;
    private double lastParentsCount;
    private double innerParentsCount;
    private int maxOlderSiblings;
    private int maxYoungerSiblings;

    public AncestorPerson(AncestorPerson person) {
        super(person);

        if (person != null) {
            initSiblings();
            innerParentsCount = person.getInnerParentsCount();
            this.ancestorLine = new ArrayList<>(person.getAncestorLine());
            this.ancestorGenerations = person.getAncestorGenerations();
            Collections.copy(person.getYoungerSiblings(), youngerSiblings);
            Collections.copy(person.getOlderSiblings(), olderSiblings);
            if (person.getSpouseCouple() != null) {
                this.spouses = new ArrayList(person.getSpouseCouples());
            } else {
                this.spouses = new ArrayList<>();
            }
        } else {
            initEmpty();
        }
    }

    public AncestorPerson(Person person) {
        super(person);
        initEmpty();
        this.setSex(person.getSex());
    }

    public AncestorPerson(String id) {
        super(id);
        initEmpty();
    }

    private void initEmpty() {
        ancestorGenerations = 0;
        lastParentsCount = 0;
        innerParentsCount = 0;
        ancestorLine = new ArrayList<>();
        spouses = new ArrayList<>();
        initSiblings();
    }

    private void initSiblings() {
        youngerSiblings = new ArrayList<>();
        olderSiblings = new ArrayList<>();
        maxOlderSiblings = 0;
        maxYoungerSiblings = 0;
    }

    @Override
    public void setSex(String sex) {
        super.setSex(sex);
        ancestorLine.clear();
        if (sex.equals(Information.VALUE_MALE)) {
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

            if (getSex().equals(Information.VALUE_FEMALE)) {
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

            if (getSex().equals(Information.VALUE_MALE)) {
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
                setMother(new AncestorPerson("000"));
            } else if (getMother().getAncestorGenerations() >= getFather().getAncestorGenerations()) {
                ancestorGenerations = getMother().getAncestorGenerations() + 1;
            } else {
                ancestorGenerations = getFather().getAncestorGenerations() + 1;
            }
        }
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

    public ArrayList<Person> getYoungerSiblings() {
        return youngerSiblings;
    }

    public void addYoungerSibling(Person youngerSibling) {
        this.youngerSiblings.add(youngerSibling);
    }

    public ArrayList<Person> getOlderSiblings() {
        return olderSiblings;
    }

    public void addOlderSibling(Person olderSibling) {
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

    public Person getSpouse() {
        if (getSpouseCouple() != null) {
            if (getSex().equals(Information.VALUE_MALE)) {
                return getSpouseCouple().getWife();
            } else {
                return getSpouseCouple().getHusband();
            }
        } else {
            return null;
        }
    }

    public Person getSpouse(int index) {
        if (getSpouseCouple(index) != null) {
            if (getSex().equals(Information.VALUE_MALE)) {
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

    public ArrayList<Couple> getSpouseCouples() {
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
