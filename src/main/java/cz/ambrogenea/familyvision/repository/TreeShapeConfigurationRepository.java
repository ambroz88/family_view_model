package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;

public class TreeShapeConfigurationRepository {

    private static TreeShapeConfigurationRepository instance = null;
    private TreeShapeConfiguration treeShapeConfiguration;

    private TreeShapeConfigurationRepository() {
        this.treeShapeConfiguration = new TreeShapeConfiguration();
    }

    public static TreeShapeConfigurationRepository get() {
        if (instance == null) {
            instance = new TreeShapeConfigurationRepository();
        }
        return instance;
    }


    public TreeShapeConfiguration find() {
        return treeShapeConfiguration;
    }

    public TreeShapeConfiguration save(TreeShapeConfiguration configuration) {
        treeShapeConfiguration = configuration;
        return treeShapeConfiguration;
    }
}
