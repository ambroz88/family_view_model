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

    private final ArrayList<Integer> ancestorLine;
    private final ArrayList<Person> youngerSiblings;
    private final ArrayList<Person> olderSiblings;
    private final ArrayList<Couple> spouses;
    private int ancestorGenerations;

    public AncestorPerson(AncestorPerson person) {
        super(person);
        youngerSiblings = new ArrayList<>();
        olderSiblings = new ArrayList<>();

        if (person != null) {
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
            ancestorGenerations = 0;
            ancestorLine = new ArrayList<>();
            this.spouses = new ArrayList<>();
        }
    }

    public AncestorPerson(Person person) {
        super(person);
        ancestorLine = new ArrayList<>();
        ancestorGenerations = 0;
        youngerSiblings = new ArrayList<>();
        olderSiblings = new ArrayList<>();
        spouses = new ArrayList<>();
        this.setSex(person.getSex());
    }

    public AncestorPerson(String id) {
        super(id);
        ancestorGenerations = 0;
        ancestorLine = new ArrayList<>();
        youngerSiblings = new ArrayList<>();
        olderSiblings = new ArrayList<>();
        spouses = new ArrayList<>();
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
