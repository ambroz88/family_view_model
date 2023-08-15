package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;

public class VisualConfigurationRepository {

    private VisualConfiguration visualConfiguration = new VisualConfiguration();

    public VisualConfiguration find() {
        return visualConfiguration;
    }

    public VisualConfiguration save(VisualConfiguration configuration) {
        visualConfiguration = configuration;
        return visualConfiguration;
    }
}
