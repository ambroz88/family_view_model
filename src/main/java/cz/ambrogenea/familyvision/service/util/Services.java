package cz.ambrogenea.familyvision.service.util;

import cz.ambrogenea.familyvision.service.*;
import cz.ambrogenea.familyvision.service.impl.*;

public class Services {

    private static VisualConfigurationService visualConfigService;
    private static TreeShapeConfigurationService treeShapeConfigService;
    private static PersonService personService;
    private static MarriageService marriageService;
    private static FamilyTreeService familyTreeService;
    private static CityService cityService;

    public static ConfigurationExtensionService horizontal() {
        return new HorizontalConfigurationService();
    }

    public static ConfigurationExtensionService vertical() {
        return new VerticalConfigurationService();
    }

    public static VisualConfigurationService visual() {
        if (visualConfigService == null) {
            visualConfigService = new VisualConfigurationServiceImpl();
        }
        return visualConfigService;
    }

    public static TreeShapeConfigurationService treeShape() {
        if (treeShapeConfigService == null) {
            treeShapeConfigService = new TreeShapeConfigurationServiceImpl();
        }
        return treeShapeConfigService;
    }

    public static PersonService person() {
        if (personService == null) {
            personService = new PersonServiceImpl();
        }
        return personService;
    }

    public static MarriageService marriage() {
        if (marriageService == null) {
            marriageService = new MarriageServiceImpl();
        }
        return marriageService;
    }

    public static FamilyTreeService familyTree() {
        if (familyTreeService == null) {
            familyTreeService = new FamilyTreeServiceImpl();
        }
        return familyTreeService;
    }

    public static CityService city() {
        if (cityService == null) {
            cityService = new CityServiceImpl();
        }
        return cityService;
    }
}
