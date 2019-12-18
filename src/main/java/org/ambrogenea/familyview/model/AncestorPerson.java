package org.ambrogenea.familyview.model;

import java.util.ArrayList;
import java.util.Collections;

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
        } else {
            ancestorGenerations = 0;
            ancestorLine = new ArrayList<>();
        }
    }

    public AncestorPerson(Person person) {
        super(person);
        ancestorLine = new ArrayList<>();
        ancestorGenerations = 0;
        youngerSiblings = new ArrayList<>();
        olderSiblings = new ArrayList<>();
        this.setSex(person.getSex());
    }

    public AncestorPerson(String id) {
        super(id);
        ancestorGenerations = 0;
        ancestorLine = new ArrayList<>();
        youngerSiblings = new ArrayList<>();
        olderSiblings = new ArrayList<>();
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

    @Override
    public String toString() {
        return getName() + " *" + getBirthDate() + "; parents: " + getParents();
    }

}
