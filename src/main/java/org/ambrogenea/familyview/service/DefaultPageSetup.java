package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.tree.Position;

public class DefaultPageSetup implements PageSetup {

    private final int pictureWidth;
    private final int pictureHeight;
    private final Position startPosition;

    public DefaultPageSetup(int pictureWidth, int pictureHeight, Position startPosition) {
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
        this.startPosition = startPosition;
    }

    @Override
    public Position getRootPosition() {
        return startPosition;
    }

    @Override
    public int getWidth() {
        return pictureWidth;
    }

    @Override
    public int getHeight() {
        return pictureHeight;
    }

}
