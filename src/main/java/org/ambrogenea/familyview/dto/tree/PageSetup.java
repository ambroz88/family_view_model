package org.ambrogenea.familyview.dto.tree;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetup {

    private final Position startPosition;
    private final int pictureWidth;
    private final int pictureHeight;

    public PageSetup(Position startPosition, int pictureWidth, int pictureHeight) {
        this.startPosition = startPosition;
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public int getWidth() {
        return pictureWidth;
    }

    public int getHeight() {
        return pictureHeight;
    }

    public Position getStartPosition() {
        return startPosition;
    }
}
