package cz.ambrogenea.familyvision.service.util;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.TreeShapeConfigurationService;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;
import cz.ambrogenea.familyvision.service.impl.VisualConfigurationServiceImpl;
import cz.ambrogenea.familyvision.service.impl.HorizontalConfigurationService;
import cz.ambrogenea.familyvision.service.impl.TreeShapeConfigurationServiceImpl;
import cz.ambrogenea.familyvision.service.impl.VerticalConfigurationService;

public class Config {

    private static TreeShapeConfigurationService treeShapeConfigurationService;
    private static VisualConfigurationService visualConfigurationService;
    private static ConfigurationExtensionService horizontalConfigService;
    private static ConfigurationExtensionService verticalConfigService;

    public static VisualConfigurationService visual() {
        if (visualConfigurationService == null) {
            visualConfigurationService = new VisualConfigurationServiceImpl(new VisualConfiguration());
        }
        return visualConfigurationService;
    }

    public static TreeShapeConfigurationService treeShape() {
        if (treeShapeConfigurationService == null) {
            treeShapeConfigurationService = new TreeShapeConfigurationServiceImpl(new TreeShapeConfiguration());
        }
        return treeShapeConfigurationService;
    }

    public static ConfigurationExtensionService horizontal() {
        if (horizontalConfigService == null) {
            horizontalConfigService = new HorizontalConfigurationService();
        }
        return horizontalConfigService;
    }

    public static ConfigurationExtensionService vertical() {
        if (verticalConfigService == null) {
            verticalConfigService = new VerticalConfigurationService();
        }
        return verticalConfigService;
    }

    public static ConfigurationExtensionService extension() {
        if (treeShape().getCoupleType() == CoupleType.VERTICAL) {
            return vertical();
        } else {
            return horizontal();
        }
    }
}
