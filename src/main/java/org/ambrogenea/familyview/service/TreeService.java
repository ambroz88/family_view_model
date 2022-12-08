package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;

public interface TreeService {

    TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration);

}
