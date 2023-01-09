package cz.ambrogenea.familyvision.mapper.command;

import cz.ambrogenea.familyvision.model.command.TreeShapeConfigurationCommand;
import cz.ambrogenea.familyvision.model.request.TreeShapeConfigurationRequest;

public class TreeShapeConfigurationCommandMapper {

    public static TreeShapeConfigurationCommand map(TreeShapeConfigurationRequest request) {
        return new TreeShapeConfigurationCommand(
                request.lineageType(),
                request.coupleType(),
                request.ancestorGenerations(),
                request.descendentGenerations(),
                request.showSiblings(),
                request.showSiblingSpouses(),
                request.showSpouses(),
                request.showHeraldry(),
                request.showResidence()
        );
    }
}
