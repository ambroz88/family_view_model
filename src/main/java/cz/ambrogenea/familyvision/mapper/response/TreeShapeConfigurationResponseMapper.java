package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.model.response.TreeShapeConfigurationResponse;

public class TreeShapeConfigurationResponseMapper {

    public static TreeShapeConfigurationResponse map(TreeShapeConfiguration treeShapeConfiguration) {
        return new TreeShapeConfigurationResponse(
                treeShapeConfiguration.getLineageType(),
                treeShapeConfiguration.getCoupleType(),
                treeShapeConfiguration.getAncestorGenerations(),
                treeShapeConfiguration.getDescendentGenerations(),
                treeShapeConfiguration.isShowSiblings(),
                treeShapeConfiguration.isShowSiblingSpouses(),
                treeShapeConfiguration.isShowSpouses(),
                treeShapeConfiguration.isShowHeraldry(),
                treeShapeConfiguration.isShowResidence()
        );
    }

}
