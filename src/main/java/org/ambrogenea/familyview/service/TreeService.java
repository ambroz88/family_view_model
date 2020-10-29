package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;

public interface TreeService {

    TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService config);

}
