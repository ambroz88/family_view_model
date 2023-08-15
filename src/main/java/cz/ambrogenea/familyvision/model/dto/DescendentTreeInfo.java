package cz.ambrogenea.familyvision.model.dto;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DescendentTreeInfo {

    private int maxCouplesCount;
    private int maxSinglesCount;
    private int couplesCount;
    private int singlesCount;
    private int maxGenerationsCount;

    public DescendentTreeInfo() {
        maxCouplesCount = 0;
        maxSinglesCount = 0;
        couplesCount = 0;
        singlesCount = 0;
        maxGenerationsCount = 0;
    }

    public int getCouplesCount() {
        return couplesCount;
    }

    public void setCouplesCount(int couplesCount) {
        this.couplesCount = couplesCount;
        if (couplesCount > maxCouplesCount) {
            setMaxCouplesCount(couplesCount);
        }
    }

    public int getSinglesCount() {
        return singlesCount;
    }

    public void setSinglesCount(int singlesCount) {
        this.singlesCount = singlesCount;
        if (singlesCount > maxSinglesCount) {
            setMaxSinglesCount(singlesCount);
        }
    }

    public void increaseCouplesCount() {
        setCouplesCount(getCouplesCount() + 1);
    }

    public void increaseSinglesCount() {
        setSinglesCount(getSinglesCount() + 1);
    }

    public int getMaxCouplesCount() {
        return maxCouplesCount;
    }

    public void setMaxCouplesCount(int maxCouplesCount) {
        this.maxCouplesCount = maxCouplesCount;
    }

    public int getMaxSinglesCount() {
        return maxSinglesCount;
    }

    public void setMaxSinglesCount(int maxSinglesCount) {
        this.maxSinglesCount = maxSinglesCount;
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
