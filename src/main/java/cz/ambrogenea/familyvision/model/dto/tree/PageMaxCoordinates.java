package cz.ambrogenea.familyvision.model.dto.tree;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageMaxCoordinates {
    int minX = 0;
    int minY = 0;
    int maxX = 0;
    int maxY = 0;

    public void verifyExtremes(Position position) {
        setMaxX(position.x());
        setMinX(position.x());

        setMaxY(position.y());
        setMinY(position.y());
    }

    public int getMinX() {
        return minX;
    }

    private void setMinX(int minX) {
        if (minX < getMinX()) {
            this.minX = minX;
        }
    }

    public int getMinY() {
        return minY;
    }

    private void setMinY(int minY) {
        if (minY < getMinY()) {
            this.minY = minY;
        }
    }

    public int getMaxX() {
        return maxX;
    }

    private void setMaxX(int maxX) {
        if (maxX > getMaxX()) {
            this.maxX = maxX;
        }
    }

    public int getMaxY() {
        return maxY;
    }

    private void setMaxY(int maxY) {
        if (maxY > getMaxY()) {
            this.maxY = maxY;
        }
    }


}
