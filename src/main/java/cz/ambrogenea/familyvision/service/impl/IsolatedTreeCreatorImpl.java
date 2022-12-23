package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.service.ConfigurationService;
import cz.ambrogenea.familyvision.service.IsolatedTreeCreator;
import cz.ambrogenea.familyvision.service.impl.selection.AllAncestorsSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.FathersSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.MothersSelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.ParentsSelectionService;
import cz.ambrogenea.familyvision.service.impl.tree.AllAncestorTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.FatherLineageTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.MotherLineageTreeService;
import cz.ambrogenea.familyvision.service.impl.tree.ParentLineageTreeService;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.TreeService;

public class IsolatedTreeCreatorImpl implements IsolatedTreeCreator {

    @Override
    public TreeModel generateAllAncestorCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new AllAncestorsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new AllAncestorTreeService();
        return treeService.generateTreeModel(rootPerson, configurationService);
    }

    @Override
    public TreeModel generateFatherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new FathersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new FatherLineageTreeService();
        return treeService.generateTreeModel(rootPerson, configurationService);
    }

    @Override
    public TreeModel generateMotherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new MothersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new MotherLineageTreeService();
        return treeService.generateTreeModel(rootPerson, configurationService);
    }

    @Override
    public TreeModel generateParentLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new ParentsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new ParentLineageTreeService();
        return treeService.generateTreeModel(rootPerson, configurationService);
    }

}
