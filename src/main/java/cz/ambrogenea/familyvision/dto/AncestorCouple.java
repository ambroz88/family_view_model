package cz.ambrogenea.familyvision.dto;

import java.util.ArrayList;
import java.util.List;

import cz.ambrogenea.familyvision.domain.DatePlace;
import cz.ambrogenea.familyvision.enums.Sex;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorCouple {

    private ArrayList<String> childrenID;
    private ArrayList<AncestorPerson> children;

    private AncestorPerson wife;
    private AncestorPerson husband;
    private DatePlace datePlace;
    private DescendentTreeInfo descendentTreeInfo;

    public AncestorCouple() {
        children = new ArrayList<>();
        datePlace = new DatePlace();
        descendentTreeInfo = new DescendentTreeInfo();
    }

    public AncestorCouple(AncestorPerson husband, AncestorPerson wife) {
        this.husband = husband;
        this.wife = wife;
        initEmpty();
    }

    private void initEmpty() {
        childrenID = new ArrayList<>();
        children = new ArrayList<>();
        datePlace = new DatePlace();
        descendentTreeInfo = new DescendentTreeInfo();
    }

    public void setWife(AncestorPerson wife) {
        this.wife = wife;
    }

    public void setHusband(AncestorPerson husband) {
        this.husband = husband;
    }

    public DatePlace getDatePlace() {
        return datePlace;
    }

    public void setDatePlace(DatePlace datePlace) {
        this.datePlace = datePlace;
    }

    public AncestorPerson getSpouse(Sex sex) {
        if (sex.equals(Sex.MALE)) {
            return getWife();
        } else {
            return getHusband();
        }
    }

    public AncestorPerson getWife() {
        return wife;
    }

    public AncestorPerson getHusband() {
        return husband;
    }

    public boolean hasHusband() {
        return husband != null;
    }

    public boolean hasWife() {
        return wife != null;
    }

    public boolean isNotSingle() {
        return hasHusband() && hasWife();
    }

    public ArrayList<String> getChildrenIndexes() {
        return childrenID;
    }

    public void setChildrenID(ArrayList<String> childrenID) {
        this.childrenID = childrenID;
    }

    public void addChildrenIndex(String childID) {
        this.childrenID.add(childID);
    }

    public ArrayList<AncestorPerson> getChildren() {
        return children;
    }

    public void setChildren(List<AncestorPerson> children) {
        this.children = new ArrayList<>();
        int maxSingles = 0;
        int maxCouples = 0;
        int actualSingles = 0;
        int actualCouples = 0;
        for (AncestorPerson child : children) {
            this.children.add(child);
            int generationsCount;

            if (child.getSpouseCouples().isEmpty()) {
                actualSingles++;
            } else {
                actualCouples++;
                maxCouples += child.getSpouseCouple().getDescendentTreeInfo().getMaxCouplesCount();
                maxSingles += child.getSpouseCouple().getDescendentTreeInfo().getMaxSinglesCount();
            }

            if (child.getSpouseCouple() == null) {
                generationsCount = 1;
            } else {
                generationsCount = 1 + child.getSpouseCouple().getDescendentTreeInfo().getMaxGenerationsCount();
            }
            getDescendentTreeInfo().addChildGenerations(generationsCount);
        }

        getDescendentTreeInfo().setSinglesCount(actualSingles);
        getDescendentTreeInfo().setCouplesCount(actualCouples);
        getDescendentTreeInfo().validateMaxCounts(maxSingles, maxCouples);
    }

    public void addChildren(AncestorPerson child) {
        this.children.add(child);
        int generationsCount;
        if (child.getSpouseCouples().isEmpty()) {
            generationsCount = 1;
            getDescendentTreeInfo().increaseSinglesCount();
        } else {
            generationsCount = 1 + child.getSpouseCouple().getDescendentTreeInfo().getMaxGenerationsCount();
            getDescendentTreeInfo().increaseCouplesCount();
        }
        getDescendentTreeInfo().addChildGenerations(generationsCount);
    }

    public DescendentTreeInfo getDescendentTreeInfo() {
        return descendentTreeInfo;
    }

    public boolean isEmpty() {
        return husband == null && wife == null;
    }

    @Override
    public String toString() {
        if (getHusband() != null && getWife() == null) {
            return getHusband().getName();
        } else if (getHusband() == null && getWife() != null) {
            return getWife().getName();
        } else if (isEmpty()) {
            return "None";
        } else {
            return getHusband().getName() + " and " + getWife().getName();
        }

    }

}
