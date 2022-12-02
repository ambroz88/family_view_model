package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;
import org.ambrogenea.familyview.utils.Tools;

public class FatherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        Position rootPosition = pageSetup.getRootPosition();
        LineageService lineageService = new LineageServiceImpl(configuration);
        lineageService.addRootPerson(rootPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);
        lineageService.generateChildren(rootPosition, rootPerson.getSpouseCouple());

        Position heraldryPosition = rootPosition.addXAndY(0, -(configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2);
        lineageService.addLine(rootPosition, heraldryPosition, Relation.DIRECT);

        if (rootPerson.getFather() != null) {
            lineageService.generateFathersFamily(heraldryPosition, rootPerson);
        } else if (rootPerson.getMother() != null) {
            lineageService.generateMotherFamily(heraldryPosition, rootPerson);
        }

        TreeModel treeModel = lineageService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Rodová linie " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

}
