package cz.ambrogenea.familyvision.service.impl.selection;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.dto.AncestorCouple;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.mapper.dto.AncestorCoupleMapper;
import cz.ambrogenea.familyvision.mapper.dto.AncestorPersonMapper;
import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.Services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonSelectionService {

    private int generationLimit;
    private final ConfigurationService configuration;

    public CommonSelectionService(ConfigurationService configuration) {
        this.configuration = configuration;
    }

    protected List<AncestorCouple> addSpouseWithChildren(List<String> spouseId) {
        return spouseId.stream()
                .map(coupleID -> {
                    Marriage dbCouple = Services.marriage().getMarriageByGedcomId(coupleID);
                    if (dbCouple != null) {
                        AncestorCouple couple = AncestorCoupleMapper.mapWithoutKids(dbCouple, true);
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

    protected List<AncestorCouple> addSpouse(List<String> spouseId, boolean isDirectLineage) {
        return spouseId.stream()
                .map(coupleID -> {
                    Marriage dbCouple = Services.marriage().getMarriageByGedcomId(coupleID);
                    if (dbCouple != null) {
                        return AncestorCoupleMapper.mapWithoutKids(dbCouple, isDirectLineage);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected void addSiblings(AncestorPerson person, String parentId) {
        if (configuration.isShowSiblings()) {
            Marriage parents = Services.marriage().getMarriageByGedcomId(parentId);
            if (parents != null) {
                List<String> children = parents.getChildrenIds();
                int position = 0;
                AncestorPerson sibling;
                Person dbPerson;

                while (!children.get(position).equals(person.getGedcomId())) {
                    dbPerson = Services.person().getPersonByGedcomId(children.get(position));
                    if (dbPerson != null) {
                        sibling = AncestorPersonMapper.map(dbPerson);
                        sibling.setDirectLineage(false);

                        if (configuration.isShowSiblingSpouses()) {
                            sibling.setSpouses(addSpouse(dbPerson.getSpouseId(), false));
                        }
                        person.addOlderSibling(sibling);
                        if (sibling.getSpouse() != null) {
                            person.addOlderSiblingsSpouse();
                        }
                    }
                    position++;
                }

                position++;
                while (position < children.size()) {
                    dbPerson = Services.person().getPersonByGedcomId(children.get(position));
                    if (dbPerson != null) {
                        sibling = AncestorPersonMapper.map(dbPerson);
                        sibling.setDirectLineage(false);

                        if (configuration.isShowSiblingSpouses()) {
                            sibling.setSpouses(addSpouse(dbPerson.getSpouseId(), false));
                        }
                        person.addYoungerSibling(sibling);
                        if (sibling.getSpouse() != null) {
                            person.addYoungerSiblingsSpouse();
                        }
                    }
                    position++;
                }
            }
        }
    }

    protected AncestorPerson fromPersonWithManParents(Person person, int generation) {
        if (person != null) {
            AncestorPerson ancestorPerson = AncestorPersonMapper.map(person);
            ancestorPerson.setDirectLineage(true);
            if (generation > 1 || (generation == 1 && configuration.isShowSpouses())) {
                ancestorPerson.setSpouses(addSpouseWithChildren(person.getSpouseId()));
            }

            if (generation + 1 <= generationLimit) {
                addSiblings(ancestorPerson, person.getParentId());
                addManParents(ancestorPerson, person.getParentId(), generation + 1);
            }
            return ancestorPerson;
        }
        return null;
    }

    protected AncestorPerson fromPersonWithWomanParents(Person person) {
        if (person != null) {
            AncestorPerson ancestorPerson = AncestorPersonMapper.map(person);
            ancestorPerson.setDirectLineage(true);
            if (configuration.isShowSpouses()) {
                ancestorPerson.setSpouses(addSpouseWithChildren(person.getSpouseId()));
            }
            if (2 <= generationLimit) {
                addSiblings(ancestorPerson, person.getParentId());
                addWomanParents(ancestorPerson, person.getParentId());
            }
            return ancestorPerson;
        }
        return null;
    }

    protected AncestorPerson fromPersonWithAllDescendents(Person person, int generation) {
        if (person != null) {
            AncestorPerson ancestorPerson = AncestorPersonMapper.map(person);
            ancestorPerson.setDirectLineage(true);
            ancestorPerson.setSpouses(addSpouse(person.getSpouseId(), true));
            if (generation + 1 <= generationLimit) {
                ancestorPerson.getSpouseCouples()
                        .forEach(spouseCouple -> addChildrenToCouple(spouseCouple, generation + 1));
            }
            return ancestorPerson;
        }
        return null;
    }

    private void addChildrenToCouple(AncestorCouple spouseCouple, int generation) {
        List<AncestorPerson> children = spouseCouple.getChildrenIndexes()
                .stream()
                .map(childId -> {
                    Person dbChild = Services.person().getPersonByGedcomId(childId);
                    return fromPersonWithAllDescendents(dbChild, generation);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        spouseCouple.setChildren(children);
    }

    private void addManParents(AncestorPerson person, String parentId, int generation) {
        MarriageDto parents = Services.marriage().getMarriageDtoByGedcomId(parentId);

        if (person != null && parents != null) {
            person.setParents(AncestorCoupleMapper.mapWithoutKids(parents, true));

            if (parents.husband() != null) {
                AncestorPerson father = fromPersonWithManParents(parents.husband(), generation);
                person.setFather(father);
            } else {
                AncestorPerson mother = fromPersonWithManParents(parents.wife(), generation);
                person.setMother(mother);

                person.setMaxOlderSiblings(mother.getMaxOlderSiblings());
                person.setMaxOlderSiblingsSpouse(mother.getMaxOlderSiblingsSpouse());
                person.setMaxYoungerSiblings(mother.getMaxYoungerSiblings());
                person.setMaxYoungerSiblingsSpouse(mother.getMaxYoungerSiblingsSpouse());
            }

        }
    }

    private void addWomanParents(AncestorPerson person, String parentId) {
        MarriageDto parents = Services.marriage().getMarriageDtoByGedcomId(parentId);

        if (person != null && parents != null) {
            person.setParents(AncestorCoupleMapper.mapWithoutKids(parents, true));

            if (parents.wife() != null) {
                AncestorPerson mother = fromPersonWithManParents(parents.wife(), 2);
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
            Person child = Services.person().getPersonByGedcomId(childId);
            if (child != null) {
                childAncestor = AncestorPersonMapper.map(child);
                if (configuration.isShowSiblingSpouses()) {
                    childAncestor.setSpouses(addSpouse(child.getSpouseId(), true));
                }
                childAncestor.setDirectLineage(true);
                spouse.addChildren(childAncestor);
            }
        }
    }

    public int getGenerationLimit() {
        return generationLimit;
    }

    public void setGenerationLimit(int generationLimit) {
        this.generationLimit = generationLimit;
    }

}
