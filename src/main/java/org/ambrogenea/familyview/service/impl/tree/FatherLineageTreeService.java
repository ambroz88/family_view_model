package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;

public class FatherLineageTreeService implements TreeService {

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, Position rootPosition, Configuration configuration) {
        LineageService lineageService;
        if (configuration.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        } else {
            lineageService = new HorizontalLineageService(configuration);
        }

        lineageService.drawPerson(rootPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);

        if (rootPerson.getFather() != null) {
            lineageService.generateFathersFamily(rootPosition, rootPerson);
        } else if (rootPerson.getMother() != null) {
            lineageService.generateMotherFamily(rootPosition, rootPerson);
        }

        if (configuration.isShowSpouses()) {
            lineageService.addSpouse(rootPosition, rootPerson);
            if (configuration.isShowChildren()) {
                lineageService.generateChildren(rootPosition, rootPerson.getSpouseCouple());
            }
        }

        return lineageService.getTreeModel();
    }

}
