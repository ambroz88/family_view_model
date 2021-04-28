package org.ambrogenea.familyview.dto;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DescendentTreeInfo {

    private int maxCouplesCount;
    private int maxSinglesCount;
    private int maxGenerationsCount;

    public DescendentTreeInfo() {
        maxCouplesCount = 0;
        maxSinglesCount = 0;
        maxGenerationsCount = 0;
    }

    public int getMaxCouplesCount() {
        return maxCouplesCount;
    }

    public void setMaxCouplesCount(int maxCouplesCount) {
        this.maxCouplesCount = maxCouplesCount;
    }

    public void increaseMaxCouplesCount() {
        this.maxCouplesCount++;
    }

    public int getMaxSinglesCount() {
        return maxSinglesCount;
    }

    public void setMaxSinglesCount(int maxSinglesCount) {
        this.maxSinglesCount = maxSinglesCount;
    }

    public void increaseMaxSinglesCount() {
        this.maxSinglesCount++;
    }

    public int getMaxGenerationsCount() {
        return maxGenerationsCount;
    }

    public void setMaxGenerationsCount(int maxGenerationsCount) {
        this.maxGenerationsCount = maxGenerationsCount;
    }

    public void increaseMaxGenerationsCount() {
        this.maxGenerationsCount++;
    }

    public void addChildGenerations(int childGenerations) {
        this.maxGenerationsCount = Math.max(getMaxGenerationsCount(), childGenerations);
    }

    public void validateMaxCounts(int singlesCount, int couplesCount) {
        if ((singlesCount * 2 + couplesCount) > (getMaxSinglesCount() * 2 + getMaxCouplesCount())) {
            setMaxCouplesCount(couplesCount);
            setMaxSinglesCount(singlesCount);
        }
    }
}
