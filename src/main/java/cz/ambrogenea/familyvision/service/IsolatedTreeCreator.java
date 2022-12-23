package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.tree.TreeModel;

public interface IsolatedTreeCreator {

    TreeModel generateAllAncestorCreator(ConfigurationService configurationService, String personId);

    TreeModel generateFatherLineageCreator(ConfigurationService configurationService, String personId);

    TreeModel generateMotherLineageCreator(ConfigurationService configurationService, String personId);

    TreeModel generateParentLineageCreator(ConfigurationService configurationService, String personId);

}
