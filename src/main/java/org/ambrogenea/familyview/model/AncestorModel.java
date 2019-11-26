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

    private AncestorPerson addManParents(AncestorPerson person, Couple parents) {
        if (person != null && parents != null && !parents.isEmpty()) {

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

                parents = new Couple(father, mother);
            }
        }
        return parents;
    }

}
