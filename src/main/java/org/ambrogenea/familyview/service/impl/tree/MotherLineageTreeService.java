package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;

public class MotherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        LineageService lineageService;
        if (configuration.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        } else {
            lineageService = new HorizontalLineageService(configuration);
        }

        Position rootPosition = pageSetup.getRootPosition();
        lineageService.addRootPerson(rootPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);

        if (rootPerson.getMother() != null) {
            lineageService.generateMotherFamily(rootPosition, rootPerson);
        }

        if (configuration.isShowSpouses()) {
            lineageService.addSpouse(rootPosition, rootPerson);
            if (configuration.isShowChildren()) {
                lineageService.generateChildren(rootPosition, rootPerson.getSpouseCouple());
            }
        }

        return lineageService.getTreeModel().setPageSetup(pageSetup);
    }

}
