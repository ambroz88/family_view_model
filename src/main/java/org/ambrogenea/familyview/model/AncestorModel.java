package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorModel extends DataModel {

    public static final int CODE_MALE = -1;
    public static final int CODE_FEMALE = 1;

    public AncestorModel(DataModel model) {
        super(model);
    }

    public AncestorPerson generateAncestors(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        person = addAllParents(person, parents);
        return person;
    }

    public AncestorPerson generateManParents(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        person = addManParents(person, parents);
        return person;
    }

    public AncestorPerson generateFathersFamily(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        person = addManParentsWithSiblings(person, parents);
        return person;
    }

    private AncestorPerson addManParents(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            if (parents.getHusband() != null) {
                AncestorPerson father = new AncestorPerson(parents.getHusband());
                Couple fathersParents = findParents(father);
                father.addChildrenCode(person.getAncestorLine());
                person.setFather(addManParents(father, fathersParents));
            }

            if (parents.getWife() != null) {
                AncestorPerson mother = new AncestorPerson(parents.getWife());
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(mother);
            }

        }
        return person;
    }

    private AncestorPerson addAllParents(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            if (parents.getHusband() != null) {
                AncestorPerson father = new AncestorPerson(parents.getHusband());
                Couple fathersParents = findParents(father);
                father.addChildrenCode(person.getAncestorLine());
                person.setFather(addAllParents(father, fathersParents));
            }

            if (parents.getWife() != null) {
                AncestorPerson mother = new AncestorPerson(parents.getWife());
                Couple mothersParents = findParents(mother);
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(addAllParents(mother, mothersParents));
            }

        }
        return person;
    }

    private AncestorPerson addManParentsWithSiblings(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            if (parents.getHusband() != null) {
                AncestorPerson father = new AncestorPerson(parents.getHusband());
                Couple fathersParents = findParents(father);
                father.addChildrenCode(person.getAncestorLine());
                person.setFather(addManParentsWithSiblings(father, fathersParents));
            }

            if (parents.getWife() != null) {
                AncestorPerson mother = new AncestorPerson(parents.getWife());
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(mother);
            }

            ArrayList<String> children = parents.getChildrenIndexes();
            int position = 0;
            while (!children.get(position).equals(person.getId())) {
                Person sibling = getIndividualMap().get(children.get(position));
                person.addOlderSibling(sibling);
                position++;
            }

            position++;
            while (position < children.size()) {
                Person sibling = getIndividualMap().get(children.get(position));
                person.addYoungerSibling(sibling);
                position++;
            }

        }
        return person;
    }

    private Couple findParents(Person person) {
        Couple parents = null;
        if (person != null) {
            parents = getSpouseMap().get(person.getParentID());
            if (parents != null) {
                parents = new Couple(parents);
            }
        }
        return parents;
    }

}
