package org.ambrogenea.familyview.service.impl.selection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;

public class CommonSelectionService {

    private FamilyData familyData;
    private int generationLimit;

    public CommonSelectionService() {
    }

    public CommonSelectionService(FamilyData familyData) {
        this.familyData = familyData;
    }

    protected LinkedList<AncestorCouple> addSpouse(List<String> spouseId) {
        LinkedList<AncestorCouple> spouses = new LinkedList<>();
        spouseId.stream()
                .map(coupleID -> familyData.getSpouseMap().get(coupleID))
                .filter(dbSpouse -> (dbSpouse != null))
                .map(dbSpouse -> new AncestorCouple(dbSpouse))
                .map(spouse -> {
                    addChildren(spouse);
                    return spouse;
                })
                .forEachOrdered(spouse -> {
                    spouses.add(spouse);
                });
        return spouses;
    }

    protected void addSiblings(AncestorPerson person, String parentId) {
        Couple parents = getFamilyData().getSpouseMap().get(parentId);
        if (parents != null) {
            ArrayList<String> children = parents.getChildrenIndexes();
            int position = 0;
            AncestorPerson sibling;
            Person dbPerson;

            while (!children.get(position).equals(person.getId())) {
                dbPerson = familyData.getPersonById(children.get(position));
                sibling = new AncestorPerson(dbPerson);
                sibling.setDirectLineage(false);
                sibling.setSpouseCouples(addSpouse(dbPerson.getSpouseID()));

                person.addOlderSibling(sibling);
                if (sibling.getSpouse() != null) {
                    person.addOlderSiblingsSpouse();
                }
                position++;
            }

            position++;
            while (position < children.size()) {
                dbPerson = familyData.getPersonById(children.get(position));
                sibling = new AncestorPerson(dbPerson);
                sibling.setDirectLineage(false);
                sibling.setSpouseCouples(addSpouse(dbPerson.getSpouseID()));

                person.addYoungerSibling(sibling);
                if (sibling.getSpouse() != null) {
                    person.addYoungerSiblingsSpouse();
                }
                position++;
            }
        }
    }

    protected AncestorPerson fromPersonWithManParents(Person person, int generation) {
        AncestorPerson ancestorPerson = new AncestorPerson(person);
        ancestorPerson.setDirectLineage(true);
        ancestorPerson.setSpouseCouples(addSpouse(person.getSpouseID()));

        if (generation + 1 <= generationLimit) {
            addSiblings(ancestorPerson, person.getParentID());
            addManParents(ancestorPerson, person.getParentID(), generation + 1);
        }
        return ancestorPerson;
    }

    protected AncestorPerson fromPersonWithWomanParents(Person person) {
        AncestorPerson ancestorPerson = new AncestorPerson(person);
        ancestorPerson.setDirectLineage(true);
        ancestorPerson.setSpouseCouples(addSpouse(person.getSpouseID()));
        if (2 <= generationLimit) {
            addSiblings(ancestorPerson, person.getParentID());
            addWomanParents(ancestorPerson, person.getParentID(), 2);
        }
        return ancestorPerson;
    }

    private AncestorPerson addManParents(AncestorPerson person, String parentId, int generation) {
        Couple parents = getFamilyData().getSpouseMap().get(parentId);

        if (person != null && parents != null) {
            person.setParents(new AncestorCouple(parents));

            if (parents.getHusband() != null) {
                AncestorPerson father = fromPersonWithManParents(parents.getHusband(), generation);
                father.addChildrenCode(person.getAncestorLine());
                person.setFather(father);
            } else {
                AncestorPerson mother = fromPersonWithManParents(parents.getWife(), generation);
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(mother);

                person.setMaxOlderSiblings(mother.getMaxOlderSiblings());
                person.setMaxOlderSiblingsSpouse(mother.getMaxOlderSiblingsSpouse());
                person.setMaxYoungerSiblings(mother.getMaxYoungerSiblings());
                person.setMaxYoungerSiblingsSpouse(mother.getMaxYoungerSiblingsSpouse());
            }

        }
        return person;
    }

    private void addWomanParents(AncestorPerson person, String parentId, int generation) {
        Couple parents = getFamilyData().getSpouseMap().get(parentId);

        if (person != null && parents != null) {
            person.setParents(new AncestorCouple(parents));

            if (parents.getWife() != null) {
                AncestorPerson mother = fromPersonWithManParents(parents.getWife(), generation);
                mother.addChildrenCode(person.getAncestorLine());
                person.setMother(mother);

                person.setMaxOlderSiblings(Math.max(person.getMaxOlderSiblings(), mother.getOlderSiblings().size()));
                person.setMaxOlderSiblingsSpouse(Math.max(person.getMaxOlderSiblingsSpouse(), mother.getMaxOlderSiblingsSpouse()));
                person.setMaxYoungerSiblings(Math.max(person.getMaxYoungerSiblings(), mother.getYoungerSiblings().size()));
                person.setMaxYoungerSiblingsSpouse(Math.max(person.getMaxYoungerSiblingsSpouse(), mother.getMaxYoungerSiblingsSpouse()));
            }

        }
    }

    private void addChildren(AncestorCouple spouse) {
        AncestorPerson childAncestor;
        for (String childId : spouse.getChildrenIndexes()) {
            Person child = familyData.getPersonById(childId);
            if (child != null) {
                childAncestor = new AncestorPerson(child);
                childAncestor.setSpouseCouples(addSpouse(child.getSpouseID()));
                childAncestor.setDirectLineage(false);
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
