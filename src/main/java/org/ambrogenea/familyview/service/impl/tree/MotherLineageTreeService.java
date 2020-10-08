package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;

public class MotherLineageTreeService implements TreeService {

    private LineageService lineageService;
    private final Configuration configuration;
    private final AncestorPerson rootPerson;

    public MotherLineageTreeService(Configuration config, AncestorPerson rootPerson) {
        this.configuration = config;
        this.rootPerson = rootPerson;
        if (config.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        }
    }

    @Override
    public TreeModel generateTreeModel(Position rootPosition) {
        lineageService.drawPerson(rootPosition, rootPerson);
        lineageService.drawSpouseAndSiblings(rootPosition,rootPerson);

        if (rootPerson.getMother() != null) {
            drawMotherFamily(rootPosition, rootPerson);
        }

        if (configuration.isShowSpouses() && configuration.isShowChildren()) {
            lineageService.drawChildren(rootPosition, rootPerson.getSpouseCouple());
        }
        return lineageService.getTreeModel();
    }

    private void drawMotherFamily(Position rootPositionPosition, AncestorPerson person) {
        lineageService.addVerticalLineToParents(rootPositionPosition);
        Position motherPosition = lineageService.drawFather(rootPositionPosition, person.getMother());

        if (person.getFather() != null) {
            lineageService.drawMother(rootPositionPosition, person.getFather(), person.getParents().getMarriageDate());
        }

        if (configuration.isShowSiblings()) {
            lineageService.drawSiblingsAroundMother(motherPosition, person.getMother());
        }

        if (configuration.isShowHeraldry()) {
            lineageService.addHeraldry(rootPositionPosition, person.getSimpleBirthPlace());
        }

        lineageService.drawFathersFamilyVertical(motherPosition, person.getMother());
    }
    
}
