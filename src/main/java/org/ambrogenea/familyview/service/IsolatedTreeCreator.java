package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.TreeModel;

public interface IsolatedTreeCreator {

    TreeModel generateAllAncestorCreator(ConfigurationService configurationService, String personId);

    TreeModel generateFatherLineageCreator(ConfigurationService configurationService, String personId);

    TreeModel generateMotherLineageCreator(ConfigurationService configurationService, String personId);

    TreeModel generateParentLineageCreator(ConfigurationService configurationService, String personId);

    TreeModel generateCloseFamilyCreator(ConfigurationService configurationService, String personId);

}
