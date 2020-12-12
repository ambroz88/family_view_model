package org.ambrogenea.familyview.service.impl.paging;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.Pageable;
import org.ambrogenea.familyview.service.Paging;

public class ParentLineagePageSetup implements Pageable {

    private final Paging paging;

    public ParentLineagePageSetup(ConfigurationService config) {
        if (config.isShowCouplesVertical()) {
            paging = new VerticalPaging(config);
        } else {
            paging = new HorizontalPaging(config);
        }
    }

    @Override
    public PageSetup createPageSetup(AncestorPerson person) {
        int pictureHeight = calculatePageHeight(person);
        int x = calculateXPosition(person);
        int y = calculateYPosition(person, pictureHeight);
        return new PageSetup(
                calculatePageWidth(person),
                pictureHeight,
                0,
                new Position(x, y)
        );
    }

    private int calculateXPosition(AncestorPerson personModel) {
        return paging.calculateFatherLineageX(personModel);
    }

    private int calculateYPosition(AncestorPerson personModel, int pictureHeight) {
        return paging.calculateLineageY(personModel, pictureHeight);
    }

    private int calculatePageHeight(AncestorPerson personModel) {
        return paging.calculateLineageHeight(personModel);
    }

    private int calculatePageWidth(AncestorPerson personModel) {
        return paging.calculateParentLineageWidth(personModel);
    }

}
