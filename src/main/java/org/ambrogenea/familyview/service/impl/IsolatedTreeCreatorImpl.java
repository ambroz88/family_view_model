package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.IsolatedTreeCreator;
import org.ambrogenea.familyview.service.SelectionService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.selection.AllAncestorsSelectionService;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.service.impl.selection.MothersSelectionService;
import org.ambrogenea.familyview.service.impl.selection.ParentsSelectionService;
import org.ambrogenea.familyview.service.impl.tree.AllAncestorTreeService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.MotherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.ParentLineageTreeService;
import org.ambrogenea.familyview.utils.Tools;

public class IsolatedTreeCreatorImpl implements IsolatedTreeCreator {

    @Override
    public TreeModel generateAllAncestorCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new AllAncestorsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new AllAncestorTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, configurationService);
        treeModel.setTreeName("Vývod z předků " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateFatherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new FathersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new FatherLineageTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, configurationService);
        treeModel.setTreeName("Rodová linie " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateMotherLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new MothersSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new MotherLineageTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, configurationService);
        treeModel.setTreeName("Rodová linie matky " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    @Override
    public TreeModel generateParentLineageCreator(ConfigurationService configurationService, String personId) {
        SelectionService selectionService = new ParentsSelectionService(configurationService.getFamilyData());
        AncestorPerson rootPerson = selectionService.select(personId, configurationService.getGenerationCount());

        TreeService treeService = new ParentLineageTreeService();
        TreeModel treeModel = treeService.generateTreeModel(rootPerson, configurationService);
        treeModel.setTreeName("Rodové linie rodičů " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
