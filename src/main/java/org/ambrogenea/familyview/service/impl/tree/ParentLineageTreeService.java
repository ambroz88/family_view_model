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
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;
import org.ambrogenea.familyview.utils.Tools;

public class ParentLineageTreeService implements TreeService {

    private LineageService lineageService;
    private ConfigurationService configuration;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        this.configuration = configuration;
        if (configuration.isShowCouplesVertical()) {
            lineageService = new VerticalLineageService(configuration);
        } else {
            lineageService = new HorizontalLineageService(configuration);
        }

        Position rootPosition = pageSetup.getRootPosition();
        if (rootPerson.getFather() != null) {
            drawParentsLineage(rootPerson, rootPosition);
        } else if (rootPerson.getMother() != null) {
            lineageService.addRootPerson(rootPosition, rootPerson);
            lineageService.generateSpouseAndSiblings(rootPosition, rootPerson);
            lineageService.generateMotherFamily(rootPosition, rootPerson);
            lineageService.generateChildren(rootPosition, rootPerson.getSpouseCouple());
        }

        TreeModel treeModel = lineageService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Rodové linie rodičů " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    private void drawParentsLineage(AncestorPerson rootPerson, Position child) {
        Position fatherPosition = child.addXAndY(0, -configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP);

        lineageService.addPerson(fatherPosition, rootPerson.getFather());
        lineageService.generateFathersFamily(fatherPosition, rootPerson.getFather());

        Position motherPosition;
        if (!rootPerson.getFather().hasBothParents()) {
            motherPosition = fatherPosition.addXAndY(configuration.getAdultImageWidth() + configuration.getMarriageLabelWidth(), 0);
        } else {
            if (configuration.isShowSiblings()) {
                lineageService.addSiblings(fatherPosition, rootPerson.getFather());
                motherPosition = lineageService.calculateMotherPosition(fatherPosition, rootPerson);
                lineageService.addSiblings(motherPosition, rootPerson.getMother());
            } else {
                motherPosition = fatherPosition.addXAndY(configuration.getAdultImageWidth() + configuration.getCoupleWidth(), 0);
            }
        }

        lineageService.addPerson(motherPosition, rootPerson.getMother());
        lineageService.generateFathersFamily(motherPosition, rootPerson.getMother());

        int centerXPosition = (fatherPosition.getX() + motherPosition.getX()) / 2;
        Position childPosition = new Position(centerXPosition, child.getY());
        lineageService.addRootPerson(childPosition, rootPerson);
        lineageService.generateSpouseAndSiblings(childPosition, rootPerson);
        lineageService.generateChildren(new Position(centerXPosition, child.getY()), rootPerson.getSpouseCouple());

        Position LabelPosition = fatherPosition.addXAndY(configuration.getAdultImageWidth() / 2, -configuration.getMarriageLabelHeight() / 2);
        int labelWidth = motherPosition.getX() - fatherPosition.getX() - configuration.getAdultImageWidth();
        lineageService.addLabel(LabelPosition, labelWidth,
                rootPerson.getParents().getDatePlace().getLocalizedDate(configuration.getLocale()));
        lineageService.addLine(childPosition, new Position(centerXPosition, LabelPosition.getY() + configuration.getMarriageLabelHeight()), Relation.DIRECT);
    }

}
