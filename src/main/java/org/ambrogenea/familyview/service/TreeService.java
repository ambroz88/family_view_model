package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.dto.AncestorPerson;

public interface TreeService {

    TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService config);

}
