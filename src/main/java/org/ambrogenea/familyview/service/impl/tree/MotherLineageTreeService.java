package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;

public class MotherLineageTreeService implements TreeService {

    private final LineageService lineageService;
    private final Configuration configuration;
    private final AncestorPerson rootPerson;

    public MotherLineageTreeService(Configuration config, AncestorPerson rootPerson) {
        this.configuration = config;
        this.rootPerson = rootPerson;
        if (config.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        } else {
            lineageService = new HorizontalLineageService(configuration);
        }
    }

    @Override
    public TreeModel generateTreeModel(Position rootPosition) {
        lineageService.drawPerson(rootPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);

        if (rootPerson.getMother() != null) {
            lineageService.generateMotherFamily(rootPosition, rootPerson);
        }

        if (configuration.isShowSpouses() && configuration.isShowChildren()) {
            lineageService.addChildren(rootPosition, rootPerson.getSpouseCouple());
        }
        return lineageService.getTreeModel();
    }

}
