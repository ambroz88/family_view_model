package org.ambrogenea.familyview.service.impl.selection;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.FamilyData;
import org.ambrogenea.familyview.model.Person;

import java.util.ArrayList;

public class CommonSelectionService {

    private FamilyData familyData;
    private int generationLimit;

    public CommonSelectionService(FamilyData familyData) {
        this.familyData = familyData;
    }

    protected void addSpouse(AncestorPerson person) {
        if (person != null) {
            for (String coupleID : person.getSpouseID()) {
                Couple spouse = familyData.getSpouseMap().get(coupleID);
                if (spouse != null) {
                    spouse = new Couple(spouse);
                    addChildren(spouse);
                    person.addSpouseCouple(spouse);
                }
            }
        }
    }

    protected Couple findParents(Person person) {
        Couple parents = null;
        if (person != null && person.getParentID() != null) {
            parents = familyData.getSpouseMap().get(person.getParentID());
            if (parents != null) {
                parents = new Couple(parents);
            }
        }
        return parents;
    }

    protected AncestorPerson addManParentsWithSiblings(AncestorPerson person, Couple parents) {
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

            } else {
                person.setMaxOlderSiblings(person.getFather().getMaxOlderSiblings());
                person.setMaxOlderSiblingsSpouse(person.getFather().getMaxOlderSiblingsSpouse());
                person.setMaxYoungerSiblings(person.getFather().getMaxYoungerSiblings());
                person.setMaxYoungerSiblingsSpouse(person.getFather().getMaxYoungerSiblingsSpouse());
            }
        }
        return person;
    }

    protected void addWomanParentsWithSiblings(AncestorPerson person, Couple parents) {
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
    }

    protected void addSiblings(Couple parents, AncestorPerson person) {
        if (person != null && parents != null) {
            ArrayList<String> children = parents.getChildrenIndexes();
            int position = 0;
            AncestorPerson sibling;
            while (!children.get(position).equals(person.getId())) {
                sibling = new AncestorPerson(getFamilyData().getIndividualMap().get(children.get(position)), false);
                addSpouse(sibling);
                person.addOlderSibling(sibling);
                if (sibling.getSpouse() != null) {
                    person.addOlderSiblingsSpouse();
                }
                position++;
            }

            position++;
            while (position < children.size()) {
                sibling = new AncestorPerson(getFamilyData().getIndividualMap().get(children.get(position)), false);
                addSpouse(sibling);
                person.addYoungerSibling(sibling);
                if (sibling.getSpouse() != null) {
                    person.addYoungerSiblingsSpouse();
                }
                position++;
            }
        }
    }

    private void addChildren(Couple spouse) {
        AncestorPerson childAncestor;
        for (String index : spouse.getChildrenIndexes()) {
            Person child = familyData.getIndividualMap().get(index);
            if (child != null) {
                childAncestor = new AncestorPerson(child, false);
                addSpouse(childAncestor);
                spouse.addChildren(childAncestor);
            }
        }
    }

    public FamilyData getFamilyData() {
        return familyData;
    }

    public void setFamilyData(FamilyData familyData) {
        this.familyData = familyData;
    }

    public int getGenerationLimit() {
        return generationLimit;
    }

    public void setGenerationLimit(int generationLimit) {
        this.generationLimit = generationLimit;
    }

}
