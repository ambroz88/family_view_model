package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.TreeModel;

public interface TreeService {

    TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration);

}
