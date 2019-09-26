package org.ambrogenea.familyview.model;

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

    public Person generateAncestors(int rowIndex) {
        Person person = getRecordList().get(rowIndex);
        Couple parents = findParents(person);

        person = addParents(person, parents);
        System.out.println(person.getAncestorGenerations());
        return person;
    }

    private Person addParents(Person person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {
            Couple fathersParents = findParents(parents.getHusband());
            Couple mothersParents = findParents(parents.getWife());

            System.out.println("-----add parents---------");
            System.out.println("Child: " + person);
            System.out.println("Child children count: " + person.getAncestorLine().size());
            Person father = parents.getHusband();
            System.out.println("Father: " + father);
            if (father != null) {
                father.addChildrenCode(person.getAncestorLine());
                System.out.println("Fathers children count: " + father.getAncestorLine().size());
            }
            person.setFather(addParents(father, fathersParents));

            Person mother = parents.getWife();
            System.out.println("Mother: " + mother);
            System.out.println("Child children count: " + person.getAncestorLine().size());
            if (mother != null) {
                System.out.println("Mothers children count: " + mother.getAncestorLine().size());
                mother.addChildrenCode(person.getAncestorLine());
                System.out.println("Mothers children count: " + mother.getAncestorLine().size());
            }
            person.setMother(addParents(mother, mothersParents));
            System.out.println("-------------------------");
        }
        return person;
    }

    private Couple findParents(Person person) {
        Couple parents = null;
        if (person != null) {
            parents = getSpouseMap().get(person.getParentID());
            if (parents != null) {
                Person father = null;
                Person mother = null;

                if (parents.getHusband() != null) {
                    father = getIndividualMap().get(parents.getHusband().getId());
                }
                if (parents.getWife() != null) {
                    mother = getIndividualMap().get(parents.getWife().getId());
                }

                parents.setHusband(father);
                parents.setWife(mother);
            }
        }
        return parents;
    }

}
