package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorModel extends DataModel {

    public AncestorModel(DataModel model) {
        super(model);
    }

    public AncestorPerson generateAncestors(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        person = addAllParents(person, parents);
        return person;
    }

    public AncestorPerson generateFatherLineage(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        person = addManParentsWithSiblings(person, parents);
        return person;
    }

    public AncestorPerson generateMotherLineage(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        addWomanParentsWithSiblings(person, parents);
        return person;
    }

    public AncestorPerson generateCloseFamily(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex));
        Couple parents = findParents(person);

        person.setParents(parents);
        addSpouse(person);
        addSiblings(parents, person);
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
                if (fathersParents == null || fathersParents.isEmpty()) {
                    addSpouse(father);
                }
                person.setFather(addManParentsWithSiblings(father, fathersParents));
            }

            addSiblings(parents, person);
            addSpouse(person);
        }
        return person;
    }

    private AncestorPerson addWomanParentsWithSiblings(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            if (parents.getWife() != null) {
                AncestorPerson mother = new AncestorPerson(parents.getWife());
                Couple mothersParents = findParents(mother);
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(addManParentsWithSiblings(mother, mothersParents));
            }

            addSiblings(parents, person);
            addSpouse(person);
        }
        return person;
    }

    private void addSiblings(Couple parents, AncestorPerson person) {
        if (person != null && parents != null) {
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
    }

    private void addSpouse(AncestorPerson person) {
        if (person != null) {
            for (String coupleID : person.getSpouseID()) {
                Couple spouse = getSpouseMap().get(coupleID);
                if (spouse != null) {
                    spouse = new Couple(spouse);
                    addChildren(spouse);
                    person.addSpouseCouple(spouse);
                }
            }
        }
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

    private void addChildren(Couple spouse) {
        for (String index : spouse.getChildrenIndexes()) {
            Person child = getIndividualMap().get(index);
            if (child != null) {
                spouse.addChildren(new Person(child));
            }
        }
    }

}
