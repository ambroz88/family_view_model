package org.ambrogenea.familyview.model;

import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorModel extends DataModel {

    private final Scene scene;
    private final Configuration config;
    private int generation;

    public AncestorModel(DataModel model, Configuration configuration) {
        super(model);
        this.generation = 0;
        this.config = configuration;
        this.scene = new Scene();
    }

    public Scene generateFatherLineage(int rowIndex) {
        Person person = new Person(getRecordList().get(rowIndex));
        person.setDirectLineage(true);
        generation++;

        Couple parents = findParents(person);
        completeRootGeneration(person, parents);

        if (parents != null && !parents.isEmpty()) {
            addManParentsWithSiblings(parents);
        }

        return scene;
    }

    public Scene generateMotherLineage(int rowIndex) {
        Person person = new Person(getRecordList().get(rowIndex));
        person.setDirectLineage(true);
        generation++;

        Couple parents = findParents(person);
        completeRootGeneration(person, parents);

        if (parents != null && !parents.isEmpty()) {
            addWomanParentsWithSiblings(parents);
        }
        return scene;
    }

    public Scene generateParentsLineage(int rowIndex) {
        Person person = new Person(getRecordList().get(rowIndex));
        person.setDirectLineage(true);
        generation++;

        Couple parents = findParents(person);
        completeRootGeneration(person, parents);

        addManParentsWithSiblings(parents);

        generation = 1;
        addWomanParentsWithSiblings(parents);
        return scene;
    }

    public Scene generateCloseFamily(int rowIndex) {
        Person person = new Person(getRecordList().get(rowIndex));
        person.setDirectLineage(true);
        generation++;

        if (config.isShowParents()) {
            Couple parents = findParents(person);
            completeRootGeneration(person, parents);
            addParents(parents);
        } else {
            addSpouse(person);
        }

        return scene;
    }

    private void addParents(Couple parents) {
        generation++;
        if (parents.getHusband() != null) {
            Person father = parents.getHusband();
            Person mother = parents.getWife();

            father.getPosition().setAdultOrder(-1);
            father.getPosition().setGeneration(generation);
            scene.addPerson(father);

            mother.setDirectLineage(true);
            mother.setPosition(father.getPosition());
            mother.getPosition().setAdultOrder(father.getPosition().getAdultOrder() + 1);
            mother.getPosition().setSpouseGaps(1);

            scene.addPerson(mother);
            scene.addMarriage(new Marriage(parents.getMarriageDate(), mother.getPosition()));

        } else {
            Person mother = parents.getWife();
            mother.getPosition().setAdultOrder(1);
            mother.getPosition().setGeneration(generation);
            scene.addPerson(mother);
        }
    }

    public Scene generateAncestors(int rowIndex) {
        Person person = new Person(getRecordList().get(rowIndex));
        person.setDirectLineage(true);
        generation++;

        Couple parents = findParents(person);

        if (parents != null && !parents.isEmpty()) {
            scene.addLine(new Line(person.getPosition(), person.getPosition().getNextGeneration()));
            addAllParents(parents);
        }
        return scene;
    }

    private void completeRootGeneration(Person person, Couple parents) {
        if (person != null) {
            person.getPosition().setAdultOrder(1);
            person.getPosition().setGeneration(generation);

            if (parents != null && !parents.isEmpty()) {

                scene.addLine(new Line(person.getPosition(), person.getPosition().getNextGeneration()));

                if (config.isShowSiblings()) {
                    //addOlder
                }

                if (config.isShowSpouses()) {
                    addSpouse(person);

                }

                if (config.isShowSiblings()) {
                    //addYounger
                }

            } else if (config.isShowSpouses()) {
                addSpouse(person);

            }
        }
    }

    private void addSpouse(Person person) {
        for (int i = 0; i < person.getSpouseID().size(); i++) {
            Couple couple = getSpouseMap().get(person.getSpouseID().get(i));
            if (couple != null) {
                Person spouse = new Person(couple.getSpouse(person.getSex()));
                spouse.setDirectLineage(true);
                spouse.setPosition(person.getPosition());
                spouse.getPosition().setAdultOrder(person.getPosition().getAdultOrder() + i + 1);
                spouse.getPosition().setSpouseGaps(i + 1);

                scene.addPerson(spouse);
                scene.addMarriage(new Marriage(couple.getMarriageDate(), spouse.getPosition()));

                if (config.isShowChildren()) {
                    addChildren(couple);
                }
            }
        }
    }

    /**
     * TODO: finish it
     *
     * @param couple
     */
    private void addChildren(Couple couple) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addAllParents(Couple parents) {
        if (generation < config.getGenerationCount()) {
            generation++;

            if (parents.getHusband() != null) {
                Person father = parents.getHusband();
                Person mother = parents.getWife();

                father.getPosition().setAdultOrder(-1);
                father.getPosition().setGeneration(generation);
                scene.addPerson(father);

                mother.setDirectLineage(true);
                mother.setPosition(father.getPosition());
                mother.getPosition().setAdultOrder(father.getPosition().getAdultOrder() + 1);
                mother.getPosition().setSpouseGaps(1);

                scene.addPerson(mother);
                scene.addMarriage(new Marriage(parents.getMarriageDate(), mother.getPosition()));

                int originalGeneration = generation;
                Couple fathersParents = findParents(father);
                if (fathersParents != null && !fathersParents.isEmpty()) {
                    scene.addLine(new Line(father.getPosition(), father.getPosition().getNextGeneration()));
                    addAllParents(fathersParents);
                }

                generation = originalGeneration;
                Couple mothersParents = findParents(mother);
                if (mothersParents == null || mothersParents.isEmpty()) {
                    scene.addLine(new Line(mother.getPosition(), mother.getPosition().getNextGeneration()));
                    addAllParents(mothersParents);
                }

            } else {
                Person mother = parents.getWife();
                mother.getPosition().setAdultOrder(1);
                mother.getPosition().setGeneration(generation);
                scene.addPerson(mother);

                Couple mothersParents = findParents(mother);
                if (mothersParents == null || mothersParents.isEmpty()) {
                    scene.addLine(new Line(mother.getPosition(), mother.getPosition().getNextGeneration()));
                    addAllParents(mothersParents);
                }
            }

        }

    }

    private void addManParentsWithSiblings(Couple parents) {
        if (generation < config.getGenerationCount()) {
            generation++;

            if (parents.getHusband() != null) {
                Person father = parents.getHusband();
                father.getPosition().setAdultOrder(1);
                father.getPosition().setGeneration(generation);
                scene.addPerson(father);

                if (config.isShowSiblings()) {
                    //addOlder
                }

                Person mother = parents.getWife();
                mother.setDirectLineage(true);
                mother.setPosition(father.getPosition());
                mother.getPosition().setAdultOrder(father.getPosition().getAdultOrder() + 1);
                mother.getPosition().setSpouseGaps(1);

                scene.addPerson(mother);
                scene.addMarriage(new Marriage(parents.getMarriageDate(), mother.getPosition()));

                if (config.isShowSiblings()) {
                    //addYounger
                }

                Couple fathersParents = findParents(father);
                if (fathersParents != null && !fathersParents.isEmpty()) {
                    scene.addLine(new Line(father.getPosition(), father.getPosition().getNextGeneration()));
                    addManParentsWithSiblings(fathersParents);
                }

            } else {
                Person mother = parents.getWife();
                mother.getPosition().setAdultOrder(1);
                mother.getPosition().setGeneration(generation);
                scene.addPerson(mother);

                Couple mothersParents = findParents(mother);
                if (mothersParents == null || mothersParents.isEmpty()) {
                    scene.addLine(new Line(mother.getPosition(), mother.getPosition().getNextGeneration()));
                    addManParentsWithSiblings(mothersParents);
                }

                if (config.isShowSiblings()) {
                    //addOlder

                    //addYounger
                }

            }

        }
    }

    private void addWomanParentsWithSiblings(Couple parents) {
        if (generation < config.getGenerationCount()) {
            generation++;

            if (config.isShowSiblings()) {
                //addOlder
            }

            Person mother = parents.getWife();
            if (parents.getHusband() != null) {
                Person father = parents.getHusband();
                father.setDirectLineage(true);
                father.getPosition().setAdultOrder(1);
                father.getPosition().setGeneration(generation);
                mother.setPosition(father.getPosition());
                mother.getPosition().setSpouseGaps(1);
                mother.getPosition().setAdultOrder(father.getPosition().getAdultOrder() + 1);

                scene.addPerson(father);
                scene.addMarriage(new Marriage(parents.getMarriageDate(), father.getPosition()));
            } else {
                mother.getPosition().setGeneration(generation);
                mother.getPosition().setAdultOrder(1);
            }

            scene.addPerson(mother);
            if (config.isShowSiblings()) {
                //addYounger
            }

            Couple mothersParents = findParents(mother);
            if (mothersParents != null && !mothersParents.isEmpty()) {
                scene.addLine(new Line(mother.getPosition(), mother.getPosition().getNextGeneration()));
                addManParentsWithSiblings(mothersParents);
            }

        }
    }

    private void addSiblings(Couple parents, Person person) {
        if (person != null && parents != null) {
            ArrayList<String> children = parents.getChildrenIndexes();
            int position = 0;
            Person sibling;
            while (!children.get(position).equals(person.getTreeID())) {
                sibling = new Person(getIndividualMap().get(children.get(position)));
                sibling.setDirectLineage(false);
                sibling.getPosition().setGeneration(generation);
                sibling.getPosition().setSiblingOrder(-position);
                scene.addPerson(sibling);
                position++;
            }

            int rootPosition = position;
            position++;
            while (position < children.size()) {
                sibling = new Person(getIndividualMap().get(children.get(position)));
                sibling.setDirectLineage(false);
                sibling.getPosition().setGeneration(generation);
                sibling.getPosition().setSiblingOrder(position - rootPosition);
                sibling.getPosition().setAdultOrder(1 + person.getSpouseID().size());
                sibling.getPosition().setSpouseGaps(person.getSpouseID().size());
                scene.addPerson(sibling);
                position++;
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

}
