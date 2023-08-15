package cz.ambrogenea.familyvision.service.util;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.enums.CoupleType;
import cz.ambrogenea.familyvision.service.ConfigurationExtensionService;

public class Config {

    public static VisualConfiguration visual() {
        return Services.visual().get();
    }

    public static TreeShapeConfiguration treeShape() {
        return Services.treeShape().get();
    }

    public static ConfigurationExtensionService extension() {
        if (treeShape().getCoupleType() == CoupleType.VERTICAL) {
            return Services.vertical();
        } else {
            return Services.horizontal();
        }
    }
}
