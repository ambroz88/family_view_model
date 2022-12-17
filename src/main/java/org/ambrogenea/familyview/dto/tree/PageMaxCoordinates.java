package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageMaxCoordinates {
    int minX = 0;
    int minY = 0;
    int maxX = 0;
    int maxY = 0;
    PageSetup pageSetup;

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

    public PageSetup getPageSetup(ConfigurationService config) {
        if (pageSetup == null) {
            int horizontalBorder = config.getAdultImageWidth() / 2 + Spaces.SIBLINGS_GAP;
            int verticalBorder = config.getAdultImageHeight() / 2 + Spaces.SIBLINGS_GAP;
            maxX += horizontalBorder;
            minX -= horizontalBorder;
            maxY += verticalBorder + Spaces.TITLE_HEIGHT;
            minY -= verticalBorder;
            pageSetup = new PageSetup(
                    new Position(
                            getMinX(),
                            getMinY()
                    ),
                    getMaxX() - getMinX(),
                    getMaxY() - getMinY()
            );
        }
        return pageSetup;
    }
}
