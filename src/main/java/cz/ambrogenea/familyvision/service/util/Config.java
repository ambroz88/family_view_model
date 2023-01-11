package cz.ambrogenea.familyvision.service.util;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.repository.TreeShapeConfigurationRepository;
import cz.ambrogenea.familyvision.repository.VisualConfigurationRepository;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;
import cz.ambrogenea.familyvision.service.impl.HorizontalConfigurationService;
import cz.ambrogenea.familyvision.service.impl.VerticalConfigurationService;

public class Config {

    public static VisualConfiguration visual() {
        return VisualConfigurationRepository.get().find();
    }

    public static TreeShapeConfiguration treeShape() {
        return TreeShapeConfigurationRepository.get().find();
    }

    public static ConfigurationExtensionService horizontal() {
        return new HorizontalConfigurationService();
    }

    public static ConfigurationExtensionService vertical() {
        return new VerticalConfigurationService();
    }

    public static ConfigurationExtensionService extension() {
        if (treeShape().getCoupleType() == CoupleType.VERTICAL) {
            return vertical();
        } else {
            return horizontal();
        }
    }
}
