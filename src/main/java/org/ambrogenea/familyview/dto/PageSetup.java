package org.ambrogenea.familyview.dto;

import org.ambrogenea.familyview.dto.tree.Position;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetup {

    private final int pictureWidth;
    private final int pictureHeight;
    private final int originalY;
    private final Position startPosition;

    public PageSetup(int pictureWidth, int pictureHeight, int originalY, Position startPosition) {
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
        this.originalY = originalY;
        this.startPosition = startPosition;
    }

    public int getWidth() {
        return pictureWidth;
    }

    public int getHeight() {
        return pictureHeight;
    }

    public int getOriginalY() {
        return originalY;
    }

    public Position getRootPosition() {
        return startPosition;
    }
}
