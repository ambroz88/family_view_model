package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.model.command.TreeShapeConfigurationCommand;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface TreeShapeConfigurationService {
    TreeShapeConfiguration get();
    TreeShapeConfiguration update(TreeShapeConfigurationCommand configuration);
}
