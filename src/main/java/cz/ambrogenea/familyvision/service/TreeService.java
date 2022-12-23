package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;

public interface TreeService {

    TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration);

}
