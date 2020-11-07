package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;
import org.ambrogenea.familyview.utils.Tools;

public class AllAncestorTreeService implements TreeService {

    private LineageService specificAncestorService;
    private ConfigurationService configuration;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        this.configuration = configuration;
        if (configuration.isShowCouplesVertical()) {
            specificAncestorService = new VerticalLineageService(configuration);
        } else {
            specificAncestorService = new HorizontalLineageService(configuration);
        }

        specificAncestorService.addFirstParents(pageSetup.getRootPosition(), rootPerson);

        Position rootPosition = pageSetup.getRootPosition();
        specificAncestorService.addRootPerson(rootPosition, rootPerson);
        specificAncestorService.generateSpouseAndSiblings(rootPosition, rootPerson);
        specificAncestorService.generateChildren(rootPosition, rootPerson.getSpouseCouple());

        TreeModel treeModel = specificAncestorService.getTreeModel();
        treeModel.setPageSetup(pageSetup);
        treeModel.setTreeName("Vývod z předků " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    private void drawFirstParents(AncestorPerson rootPerson, Position child) {
        if (rootPerson.getMother() != null) {
            int parentsY = child.getY() - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;

            if (configuration.isShowHeraldry()) {
                specificAncestorService.addHeraldry(child, rootPerson.getSimpleBirthPlace());
            }

            int shiftX = configuration.getAdultImageWidth() / 4;
            double motherParentsCount = Math.min(rootPerson.getFather().getInnerParentsCount(), rootPerson.getFather().getLastParentsCount());
            double fatherParentsCount = Math.min(rootPerson.getMother().getInnerParentsCount(), rootPerson.getMother().getLastParentsCount());
            int fatherX = child.getX() - configuration.getAdultImageWidth() + shiftX
                    - (int) ((configuration.getCoupleWidth() + shiftX) * fatherParentsCount);
            int motherX = child.getX() + configuration.getAdultImageWidth() - shiftX
                    + (int) ((configuration.getCoupleWidth() + shiftX) * motherParentsCount);

            if (rootPerson.getFather() != null) {
                Position fatherPosition = new Position(fatherX, parentsY);
                specificAncestorService.addPerson(fatherPosition, rootPerson.getFather());
                specificAncestorService.addGrandParents(fatherPosition, rootPerson.getFather());
            }

            Position motherPosition = new Position(motherX, parentsY);
            specificAncestorService.addPerson(motherPosition, rootPerson.getMother());
            specificAncestorService.addGrandParents(motherPosition, rootPerson.getMother());

            int halfAdult = configuration.getAdultImageWidth() / 2;
            int labelWidth = motherX - fatherX - configuration.getAdultImageWidth();
            specificAncestorService.addLabel(new Position(fatherX + halfAdult, parentsY - configuration.getMarriageLabelHeight() / 2), labelWidth, rootPerson.getParents().getMarriageDate());

            int newChildX = (fatherX + motherX) / 2;
            Position newChild = new Position(newChildX, child.getY());
            specificAncestorService.addRootPerson(newChild, rootPerson);
            specificAncestorService.addLine(new Position(newChildX, parentsY), newChild, Relation.DIRECT);

            if (configuration.isShowSpouses()) {
                specificAncestorService.addRootSpouses(newChild, rootPerson);

                if (configuration.isShowChildren()) {
                    specificAncestorService.generateChildren(newChild, rootPerson.getSpouseCouple());
                }
            }
        }

    }

}
