package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.mapper.domain.TreeShapeConfigurationMapper;
import cz.ambrogenea.familyvision.model.command.TreeShapeConfigurationCommand;
import cz.ambrogenea.familyvision.repository.TreeShapeConfigurationRepository;
import cz.ambrogenea.familyvision.service.TreeShapeConfigurationService;
import cz.ambrogenea.familyvision.service.util.Config;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeShapeConfigurationServiceImpl implements TreeShapeConfigurationService {

    private final TreeShapeConfigurationRepository configurationRepository;

    public TreeShapeConfigurationServiceImpl() {
        configurationRepository = TreeShapeConfigurationRepository.get();
    }

    @Override
    public TreeShapeConfiguration get() {
        return configurationRepository.find();
    }

    @Override
    public TreeShapeConfiguration update(TreeShapeConfigurationCommand configurationCommand) {
        return configurationRepository.save(TreeShapeConfigurationMapper.map(configurationCommand));
    }

}
