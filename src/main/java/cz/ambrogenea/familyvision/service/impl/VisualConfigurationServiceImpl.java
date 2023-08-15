package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.mapper.domain.VisualConfigurationMapper;
import cz.ambrogenea.familyvision.model.command.VisualConfigurationCommand;
import cz.ambrogenea.familyvision.repository.VisualConfigurationRepository;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class VisualConfigurationServiceImpl implements VisualConfigurationService {

    private final VisualConfigurationRepository configurationRepository = new VisualConfigurationRepository();

    @Override
    public VisualConfiguration get() {
        return configurationRepository.find();
    }

    @Override
    public VisualConfiguration update(VisualConfigurationCommand configurationCommand) {
        return configurationRepository.save(VisualConfigurationMapper.map(configurationCommand));
    }

}
