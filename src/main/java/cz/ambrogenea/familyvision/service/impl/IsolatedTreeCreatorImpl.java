package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.IsolatedTreeCreator;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.TreeService;
import cz.ambrogenea.familyvision.service.impl.selection.AllAncestorsSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.FathersSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.MothersSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.ParentsSelectionService;
import cz.ambrogenea.familyvision.service.impl.tree.AllAncestorTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.FatherLineageTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.MotherLineageTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.ParentLineageTreeService;

public class IsolatedTreeCreatorImpl implements IsolatedTreeCreator {

    @Override
    public TreeModel generateAllAncestorCreator(String personId) {
        SelectionService selectionService = new AllAncestorsSelectionService();
        AncestorPerson rootPerson = selectionService.select(personId);

        TreeService treeService = new AllAncestorTreeService();
        return treeService.generateTreeModel(rootPerson);
    }

    @Override
    public TreeModel generateFatherLineageCreator(String personId) {
        SelectionService selectionService = new FathersSelectionService();
        AncestorPerson rootPerson = selectionService.select(personId);

        TreeService treeService = new FatherLineageTreeService();
        return treeService.generateTreeModel(rootPerson);
    }

    @Override
    public TreeModel generateMotherLineageCreator(String personId) {
        SelectionService selectionService = new MothersSelectionService();
        AncestorPerson rootPerson = selectionService.select(personId);

        TreeService treeService = new MotherLineageTreeService();
        return treeService.generateTreeModel(rootPerson);
    }

    @Override
    public TreeModel generateParentLineageCreator(String personId) {
        SelectionService selectionService = new ParentsSelectionService();
        AncestorPerson rootPerson = selectionService.select(personId);

        TreeService treeService = new ParentLineageTreeService();
        return treeService.generateTreeModel(rootPerson);
    }

}
