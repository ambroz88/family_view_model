package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;
import org.ambrogenea.familyview.utils.Tools;

public class AllAncestorTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        Position rootPosition = pageSetup.getRootPosition();
        Position heraldryPosition = rootPosition.addXAndY(0, -(configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2);
        LineageService lineageService = new LineageServiceImpl(configuration);

        lineageService.addRootPerson(rootPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);
        lineageService.generateChildren(rootPosition, rootPerson.getSpouseCouple());

        lineageService.addAllParents(heraldryPosition, rootPerson);

        TreeModel treeModel = lineageService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Vývod z předků " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
