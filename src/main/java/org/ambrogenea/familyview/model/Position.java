package org.ambrogenea.familyview.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Position {

    private int generation;
    private int adultOrder;
    private int siblingOrder;
    private int spouseGaps;

    public Position() {
        generation = 0;
        adultOrder = 0;
        siblingOrder = 0;
        spouseGaps = 0;
    }

    public Position(Position position) {
        generation = position.getGeneration();
        adultOrder = position.getAdultOrder();
        siblingOrder = position.getSiblingOrder();
        spouseGaps = position.getSpouseGaps();
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getAdultOrder() {
        return adultOrder;
    }

    public void setAdultOrder(int adultOrder) {
        this.adultOrder = adultOrder;
    }

    public int getSiblingOrder() {
        return siblingOrder;
    }

    public void setSiblingOrder(int siblingOrder) {
        this.siblingOrder = siblingOrder;
    }

    public int getSpouseGaps() {
        return spouseGaps;
    }

    public void setSpouseGaps(int spouseGaps) {
        this.spouseGaps = spouseGaps;
    }

    public Position getNextGeneration() {
        Position newPosition = new Position(this);
        newPosition.setGeneration(getGeneration() + 1);
        return newPosition;
    }
}
