package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;

public interface TreeService {

    TreeModel generateTreeModel(AncestorPerson rootPerson);

}
