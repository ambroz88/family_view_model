package org.ambrogenea.familyview.service.impl.paging;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.Paging;

public class MotherLineagePageSetup implements PageSetup {

    private final Position startPosition;
    private final int pictureWidth;
    private final int pictureHeight;

    private final Paging paging;

    public MotherLineagePageSetup(Configuration config, AncestorPerson person) {
        if (config.isShowCouplesVertical()) {
            paging = new VerticalPaging(config);
        } else {
            paging = new HorizontalPaging(config);
        }

        pictureWidth = calculatePageWidth(person);
        pictureHeight = calculatePageHeight(person);

        int x = calculateXPosition(person);
        int y = calculateYPosition(person);
        startPosition = new Position(x, y);
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

    private int calculateXPosition(AncestorPerson personModel) {
        return paging.calculateMotherLineageX(personModel);
    }

    private int calculateYPosition(AncestorPerson personModel) {
        return paging.calculateLineageY(personModel, pictureHeight);
    }

    private int calculatePageHeight(AncestorPerson personModel) {
        return paging.calculateLineageHeight(personModel);
    }

    private int calculatePageWidth(AncestorPerson personModel) {
        return paging.calculateMotherLineageWidth(personModel);
    }

}
