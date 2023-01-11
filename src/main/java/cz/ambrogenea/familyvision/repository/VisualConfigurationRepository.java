package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;

public class VisualConfigurationRepository {

    private static VisualConfigurationRepository instance = null;
    private VisualConfiguration visualConfiguration;

    private VisualConfigurationRepository() {
        this.visualConfiguration = new VisualConfiguration();
    }

    public static VisualConfigurationRepository get() {
        if (instance == null) {
            instance = new VisualConfigurationRepository();
        }
        return instance;
    }


    public VisualConfiguration find() {
        return visualConfiguration;
    }

    public VisualConfiguration save(VisualConfiguration configuration) {
        visualConfiguration = configuration;
        return visualConfiguration;
    }
}
