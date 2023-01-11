package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.model.command.VisualConfigurationCommand;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface VisualConfigurationService {
    VisualConfiguration get();
    VisualConfiguration update(VisualConfigurationCommand configuration);
}
