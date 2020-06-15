package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorModel extends DataModel {

    private final int generationLimit;

    public AncestorModel(DataModel model, int generationLimit) {
        super(model);
        this.generationLimit = generationLimit;
    }

    public AncestorPerson generateAncestors(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex), true);
        Couple parents = findParents(person);

        person = addAllParents(person, parents);
        return person;
    }

    public AncestorPerson generateFatherLineage(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex), true);
        Couple parents = findParents(person);

        person = addManParentsWithSiblings(person, parents);
        return person;
    }

    public AncestorPerson generateMotherLineage(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex), true);
        Couple parents = findParents(person);

        addSiblings(parents, person);
        addSpouse(person);
        addWomanParentsWithSiblings(person, parents);
        return person;
    }

    public AncestorPerson generateParentsLineage(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex), true);
        Couple parents = findParents(person);

        addManParentsWithSiblings(person, parents);
        addWomanParentsWithSiblings(person, parents);
        return person;
    }

    public AncestorPerson generateCloseFamily(int rowIndex) {
        AncestorPerson person = new AncestorPerson(getRecordList().get(rowIndex), true);
        Couple parents = findParents(person);

        person.setParents(parents);
        addSpouse(person);
        addSiblings(parents, person);
        return person;
    }

    private AncestorPerson addAllParents(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            if (person.getAncestorLine().size() < generationLimit) {
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
            } else {
                person.addLastParentsCount(1);
            }

        }
        return person;
    }

    private AncestorPerson addManParentsWithSiblings(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.setParents(parents);

            addSiblings(parents, person);
            addSpouse(person);

            if (person.getAncestorLine().size() < generationLimit) {

                if (parents.getHusband() != null) {
                    AncestorPerson father = new AncestorPerson(parents.getHusband());
                    Couple fathersParents = findParents(father);
                    father.addChildrenCode(person.getAncestorLine());
                    if (fathersParents == null || fathersParents.isEmpty()) {
                        addSpouse(father);
                    }
                    person.setFather(addManParentsWithSiblings(father, fathersParents));
                    person.setMaxOlderSiblings(person.getFather().getMaxOlderSiblings());
                    person.setMaxOlderSiblingsSpouse(person.getFather().getMaxOlderSiblingsSpouse());
                    person.setMaxYoungerSiblings(person.getFather().getMaxYoungerSiblings());
                    person.setMaxYoungerSiblingsSpouse(person.getFather().getMaxYoungerSiblingsSpouse());

                } else {
                    AncestorPerson mother = new AncestorPerson(parents.getWife());
                    Couple mothersParents = findParents(mother);
                    mother.addChildrenCode(person.getAncestorLine());
                    if (mothersParents == null || mothersParents.isEmpty()) {
                        addSpouse(mother);
                    }
                    person.setMother(addManParentsWithSiblings(mother, mothersParents));
                    person.setMaxOlderSiblings(person.getMother().getMaxOlderSiblings());
                    person.setMaxOlderSiblingsSpouse(person.getMother().getMaxOlderSiblingsSpouse());
                    person.setMaxYoungerSiblings(person.getMother().getMaxYoungerSiblings());
                    person.setMaxYoungerSiblingsSpouse(person.getMother().getMaxYoungerSiblingsSpouse());
                }

            }
        }
        return person;
    }

    private AncestorPerson addWomanParentsWithSiblings(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            person.getParents().setMarriageDate(parents.getMarriageDateEnglish());
            person.getParents().setMarriagePlace(parents.getMarriagePlace());
            if (person.getFather() == null) {
                person.setFather(parents.getHusband());
            }

            if (parents.getWife() != null) {
                AncestorPerson mother = new AncestorPerson(parents.getWife());
                Couple mothersParents = findParents(mother);
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(addManParentsWithSiblings(mother, mothersParents));
                person.setMaxOlderSiblings(Math.max(person.getMaxOlderSiblings(), person.getMother().getOlderSiblings().size()));
                person.setMaxOlderSiblingsSpouse(Math.max(person.getMaxOlderSiblingsSpouse(), person.getMother().getMaxOlderSiblingsSpouse()));
                person.setMaxYoungerSiblings(Math.max(person.getMaxYoungerSiblings(), person.getMother().getYoungerSiblings().size()));
                person.setMaxYoungerSiblingsSpouse(Math.max(person.getMaxYoungerSiblingsSpouse(), person.getMother().getMaxYoungerSiblingsSpouse()));
            }

        }
        return person;
    }

    private void addSiblings(Couple parents, AncestorPerson person) {
        if (person != null && parents != null) {
            ArrayList<String> children = parents.getChildrenIndexes();
            int position = 0;
            AncestorPerson sibling;
            while (!children.get(position).equals(person.getId())) {
                sibling = new AncestorPerson(getIndividualMap().get(children.get(position)), false);
                addSpouse(sibling);
                person.addOlderSibling(sibling);
                if (sibling.getSpouse() != null) {
                    person.addOlderSiblingsSpouse();
                }
                position++;
            }

            position++;
            while (position < children.size()) {
                sibling = new AncestorPerson(getIndividualMap().get(children.get(position)), false);
                addSpouse(sibling);
                person.addYoungerSibling(sibling);
                if (sibling.getSpouse() != null) {
                    person.addYoungerSiblingsSpouse();
                }
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
        if (person != null && person.getParentID() != null) {
            parents = getSpouseMap().get(person.getParentID());
            if (parents != null) {
                parents = new Couple(parents);
            }
        }
        return parents;
    }

    private void addChildren(Couple spouse) {
        AncestorPerson childAncestor;
        for (String index : spouse.getChildrenIndexes()) {
            Person child = getIndividualMap().get(index);
            if (child != null) {
                childAncestor = new AncestorPerson(child, false);
                addSpouse(childAncestor);
                spouse.addChildren(childAncestor);
            }
        }
    }

}
