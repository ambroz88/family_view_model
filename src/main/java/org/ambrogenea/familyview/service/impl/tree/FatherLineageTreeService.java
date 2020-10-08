package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;

public class FatherLineageTreeService implements TreeService {

    private LineageService lineageService;
    private final Configuration configuration;
    private final AncestorPerson rootPerson;

    public FatherLineageTreeService(AncestorPerson model, Configuration config) {
        this.configuration = config;
        this.rootPerson = model;
        if (config.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        }
    }

    @Override
    public TreeModel generateTreeModel(Position rootPosition) {
        lineageService.drawPerson(rootPosition, rootPerson);
        lineageService.drawSpouseAndSiblings(rootPosition, rootPerson);

        if (rootPerson.getFather() != null) {
            if (configuration.isShowCouplesVertical()) {
                lineageService.drawFathersFamilyVertical(rootPosition, rootPerson);
            }
        }

        if (configuration.isShowSpouses() && configuration.isShowChildren()) {
            lineageService.drawChildren(rootPosition, rootPerson.getSpouseCouple());
        }

        return lineageService.getTreeModel();
    }

}
