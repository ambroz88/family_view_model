package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.tree.TreeModel;

public interface IsolatedTreeCreator {
    TreeModel generateAllAncestorCreator(String personId);
    TreeModel generateFatherLineageCreator(String personId);
    TreeModel generateMotherLineageCreator(String personId);
    TreeModel generateParentLineageCreator(String personId);
}
