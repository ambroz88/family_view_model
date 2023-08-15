package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;

public class TreeShapeConfigurationRepository {

    private TreeShapeConfiguration treeShapeConfiguration = new TreeShapeConfiguration();

    public TreeShapeConfiguration find() {
        return treeShapeConfiguration;
    }

    public TreeShapeConfiguration save(TreeShapeConfiguration configuration) {
        treeShapeConfiguration = configuration;
        return treeShapeConfiguration;
    }
}
