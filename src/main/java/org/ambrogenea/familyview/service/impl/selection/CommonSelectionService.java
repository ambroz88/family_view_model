package org.ambrogenea.familyview.service.impl.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.ambrogenea.familyview.domain.Couple;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;

public class CommonSelectionService {

    private FamilyData familyData;
    private int generationLimit;
    private ConfigurationService configuration;

    public CommonSelectionService(ConfigurationService configuration) {
        this.configuration = configuration;
    }

    public CommonSelectionService(FamilyData familyData) {
        this.familyData = familyData;
    }

    protected List<AncestorCouple> addSpouseWithChildren(ArrayList<String> spouseId) {
        return spouseId.stream()
                .map(coupleID -> {
                    Couple dbCouple = familyData.getSpouseMap().get(coupleID);
                    if (dbCouple != null) {
                        AncestorCouple couple = new AncestorCouple(dbCouple, true);
                        if (configuration.isShowChildren()) {
                            addChildren(couple);
                        }
                        return couple;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected List<AncestorCouple> addSpouse(ArrayList<String> spouseId, boolean isDirectLineage) {
        return spouseId.stream()
                .map(coupleID -> {
                    Couple dbCouple = familyData.getSpouseMap().get(coupleID);
                    if (dbCouple != null) {
                        return new AncestorCouple(dbCouple, isDirectLineage);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected void addSiblings(AncestorPerson person, String parentId) {
        if (configuration.isShowSiblings()) {
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

                    if (configuration.isShowSiblingSpouses()) {
                        sibling.setSpouseCouples(addSpouse(dbPerson.getSpouseID(), false));
                    }
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

                    if (configuration.isShowSiblingSpouses()) {
                        sibling.setSpouseCouples(addSpouse(dbPerson.getSpouseID(), false));
                    }
                    person.addYoungerSibling(sibling);
                    if (sibling.getSpouse() != null) {
                        person.addYoungerSiblingsSpouse();
                    }
                    position++;
                }
            }
        }
    }

    protected AncestorPerson fromPersonWithManParents(Person person, int generation) {
        AncestorPerson ancestorPerson = new AncestorPerson(person);
        ancestorPerson.setDirectLineage(true);
        if (generation > 1 || (generation == 1 && configuration.isShowSpouses())) {
            ancestorPerson.setSpouseCouples(addSpouseWithChildren(person.getSpouseID()));
        }

        if (generation + 1 <= generationLimit) {
            addSiblings(ancestorPerson, person.getParentID());
            addManParents(ancestorPerson, person.getParentID(), generation + 1);
        }
        return ancestorPerson;
    }

    protected AncestorPerson fromPersonWithWomanParents(Person person) {
        AncestorPerson ancestorPerson = new AncestorPerson(person);
        ancestorPerson.setDirectLineage(true);
        if (configuration.isShowSpouses()) {
            ancestorPerson.setSpouseCouples(addSpouseWithChildren(person.getSpouseID()));
        }
        if (2 <= generationLimit) {
            addSiblings(ancestorPerson, person.getParentID());
            addWomanParents(ancestorPerson, person.getParentID());
        }
        return ancestorPerson;
    }

    protected AncestorPerson fromPersonWithAllDescendents(Person person, int generation) {
        AncestorPerson ancestorPerson = new AncestorPerson(person);
        ancestorPerson.setDirectLineage(true);
        ancestorPerson.setSpouseCouples(addSpouse(person.getSpouseID(), true));
        if (generation + 1 <= generationLimit) {
            ancestorPerson.getSpouseCouples()
                    .forEach(spouseCouple -> addChildrenToCouple(spouseCouple, generation + 1));
        }
        return ancestorPerson;
    }

    private void addChildrenToCouple(AncestorCouple spouseCouple, int generation) {
        List<AncestorPerson> children = spouseCouple.getChildrenIndexes()
                .stream()
                .map(childId -> {
                    Person dbChild = getFamilyData().getPersonById(childId);
                    if (dbChild != null) {
                        return fromPersonWithAllDescendents(dbChild, generation);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        spouseCouple.setChildren(children);
    }

    private void addManParents(AncestorPerson person, String parentId, int generation) {
        Couple parents = getFamilyData().getSpouseMap().get(parentId);

        if (person != null && parents != null) {
            person.setParents(new AncestorCouple(parents, true));

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
    }

    private void addWomanParents(AncestorPerson person, String parentId) {
        Couple parents = getFamilyData().getSpouseMap().get(parentId);

        if (person != null && parents != null) {
            person.setParents(new AncestorCouple(parents, true));

            if (parents.getWife() != null) {
                AncestorPerson mother = fromPersonWithManParents(parents.getWife(), 2);
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
                if (configuration.isShowSiblingSpouses()) {
                    childAncestor.setSpouseCouples(addSpouse(child.getSpouseID(), true));
                }
                childAncestor.setDirectLineage(true);
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
