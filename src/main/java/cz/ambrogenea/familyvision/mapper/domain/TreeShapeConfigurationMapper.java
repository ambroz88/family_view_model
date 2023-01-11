package cz.ambrogenea.familyvision.mapper.domain;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.model.command.TreeShapeConfigurationCommand;

public class TreeShapeConfigurationMapper {

    public static TreeShapeConfiguration map(TreeShapeConfigurationCommand configurationCommand) {
        TreeShapeConfiguration treeShapeConfiguration = new TreeShapeConfiguration();
        treeShapeConfiguration.setLineageType(configurationCommand.lineageType());
        treeShapeConfiguration.setCoupleType(configurationCommand.coupleType());
        treeShapeConfiguration.setAncestorGenerations(configurationCommand.ancestorGenerations());
        treeShapeConfiguration.setDescendentGenerations(configurationCommand.descendentGenerations());
        treeShapeConfiguration.setShowSiblings(configurationCommand.showSiblings());
        treeShapeConfiguration.setShowSiblingSpouses(configurationCommand.showSiblingSpouses());
        treeShapeConfiguration.setShowSpouses(configurationCommand.showSpouses());
        treeShapeConfiguration.setShowHeraldry(configurationCommand.showHeraldry());
        treeShapeConfiguration.setShowResidence(configurationCommand.showResidence());
        return treeShapeConfiguration;
    }

}
