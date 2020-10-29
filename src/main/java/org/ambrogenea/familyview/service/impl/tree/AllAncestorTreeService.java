package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.SpecificAncestorService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalAncestorService;
import org.ambrogenea.familyview.service.impl.VerticalAncestorService;

public class AllAncestorTreeService implements TreeService {

    private SpecificAncestorService specificAncestorService;
    private ConfigurationService configuration;

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, PageSetup pageSetup, ConfigurationService configuration) {
        this.configuration = configuration;
        if (configuration.isShowCouplesVertical()) {
            specificAncestorService = new VerticalAncestorService(configuration);
        } else {
            specificAncestorService = new HorizontalAncestorService(configuration);
        }

        drawFirstParents(rootPerson, pageSetup.getRootPosition());
        return specificAncestorService.getTreeModel().setPageSetup(pageSetup);
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
                specificAncestorService.addGrandParents(rootPerson.getFather(), fatherPosition);
            }

            Position motherPosition = new Position(motherX, parentsY);
            specificAncestorService.addPerson(motherPosition, rootPerson.getMother());
            specificAncestorService.addGrandParents(rootPerson.getMother(), motherPosition);

            int halfAdult = configuration.getAdultImageWidth() / 2;
            int labelWidth = motherX - fatherX - configuration.getAdultImageWidth();
            specificAncestorService.addLabel(new Position(fatherX + halfAdult, parentsY - configuration.getMarriageLabelHeight() / 2), labelWidth, rootPerson.getParents().getMarriageDate());

            int newChildX = (fatherX + motherX) / 2;
            Position newChild = new Position(newChildX, child.getY());
            specificAncestorService.addRootPerson(newChild, rootPerson);
            specificAncestorService.addLine(new Position(newChildX, parentsY), newChild, Line.LINEAGE);

            if (configuration.isShowSpouses()) {
                specificAncestorService.addRootSpouses(newChild, rootPerson);

                if (configuration.isShowChildren()) {
                    specificAncestorService.generateChildren(newChild, rootPerson.getSpouseCouple());
                }
            }
        }

    }

}
