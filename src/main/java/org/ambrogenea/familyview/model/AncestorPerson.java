package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz
 */
public class AncestorPerson extends Person {

    private final ArrayList<Integer> ancestorLine;
    private AncestorPerson father;
    private AncestorPerson mother;
    private int ancestorGenerations;
    private int xPosition;

    public AncestorPerson(AncestorPerson person) {
        super(person);
        xPosition = 1;

        if (person != null) {
            this.ancestorLine = new ArrayList<>(person.getAncestorLine());
            this.ancestorGenerations = person.getAncestorGenerations();
            this.father = person.getFather();
            this.mother = person.getMother();
        } else {
            ancestorGenerations = 0;
            ancestorLine = new ArrayList<>();
        }
    }

    public AncestorPerson(Person person) {
        super(person);
        ancestorLine = new ArrayList<>();
        ancestorGenerations = 0;
        xPosition = 1;
        setSex(super.getSex());
    }

    @Override
    public void setSex(String sex) {
        super.setSex(sex);
        ancestorLine.clear();
        if (sex.equals(Couple.MALE)) {
            ancestorLine.add(AncestorModel.CODE_MALE);
        } else {
            ancestorLine.add(AncestorModel.CODE_FEMALE);
        }
    }

    public AncestorPerson getFather() {
        return father;
    }

    public void setFather(AncestorPerson father) {
        this.father = father;

        if (getMother() == null) {
            if (father != null) {
                ancestorGenerations = father.getAncestorGenerations() + 1;
                this.father.setxPosition(2 * father.getxPosition() - 1);
            }
        } else {
            if (father != null && father.getAncestorGenerations() >= mother.getAncestorGenerations()) {
                ancestorGenerations = father.getAncestorGenerations() + 1;
                this.father.setxPosition(2 * father.getxPosition() - 1);
            }
        }
    }

    public AncestorPerson getMother() {
        return mother;
    }

    public void setMother(AncestorPerson mother) {
        this.mother = mother;
        if (getFather() == null) {
            if (mother != null) {
                ancestorGenerations = mother.getAncestorGenerations() + 1;
                this.mother.setxPosition(2 * mother.getxPosition());
            }
        } else {
            if (mother != null && mother.getAncestorGenerations() >= father.getAncestorGenerations()) {
                ancestorGenerations = mother.getAncestorGenerations() + 1;
                this.mother.setxPosition(2 * mother.getxPosition());
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

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

}
