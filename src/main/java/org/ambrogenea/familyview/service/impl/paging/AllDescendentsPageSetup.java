package org.ambrogenea.familyview.service.impl.paging;

import static org.ambrogenea.familyview.constant.Spaces.TITLE_HEIGHT;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.DescendentTreeInfo;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.Pageable;
import org.ambrogenea.familyview.service.Paging;
import org.ambrogenea.familyview.service.impl.HorizontalConfigurationService;
import org.ambrogenea.familyview.service.impl.VerticalConfigurationService;

public class AllDescendentsPageSetup implements Pageable {

    private final Paging paging;
    private final ConfigurationService config;
    private final ConfigurationExtensionService extensionConfig;

    public AllDescendentsPageSetup(ConfigurationService config) {
        this.config = config;
        if (config.isShowCouplesVertical()) {
            paging = new VerticalPaging(config);
            extensionConfig = new VerticalConfigurationService(config);
        } else {
            paging = new HorizontalPaging(config);
            extensionConfig = new HorizontalConfigurationService(config);
        }
    }

    @Override
    public PageSetup createPageSetup(AncestorPerson person) {
        int pictureHeight = calculatePageHeight(person);
        int x = calculateXPosition(person);
        int y = calculateYPosition();
        return new PageSetup(
                calculatePageWidth(person),
                pictureHeight,
                0,
                new Position(x, y)
        );
    }

    private int calculateXPosition(AncestorPerson personModel) {
        DescendentTreeInfo treeInfo = personModel.getSpouseCouple().getDescendentTreeInfo();
        return (treeInfo.getMaxCouplesCount() * (extensionConfig.getCoupleWidth() + Spaces.SIBLINGS_GAP)
                + treeInfo.getMaxSinglesCount() * (config.getAdultImageWidth() + Spaces.SIBLINGS_GAP)) / 2
                - extensionConfig.getSpouseDistance() / 2 + Spaces.SIBLINGS_GAP;
    }

    private int calculateYPosition() {
        return TITLE_HEIGHT + config.getAdultImageHeight() / 2 + Spaces.SIBLINGS_GAP;
    }

    private int calculatePageHeight(AncestorPerson personModel) {
        return paging.calculateAllDescendentHeight(personModel);
    }

    private int calculatePageWidth(AncestorPerson personModel) {
        DescendentTreeInfo treeInfo = personModel.getSpouseCouple().getDescendentTreeInfo();
        return Spaces.SIBLINGS_GAP + treeInfo.getMaxCouplesCount() * (extensionConfig.getCoupleWidth() + Spaces.SIBLINGS_GAP)
                + treeInfo.getMaxSinglesCount() * (config.getAdultImageWidth() + Spaces.SIBLINGS_GAP);
    }

}
