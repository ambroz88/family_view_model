package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;

public interface TreeService {

    TreeModel generateTreeModel(Position rootPosition);

}
