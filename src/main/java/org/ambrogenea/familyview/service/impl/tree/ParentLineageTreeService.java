package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalLineageService;
import org.ambrogenea.familyview.service.impl.VerticalLineageService;

public class ParentLineageTreeService implements TreeService {

    private final LineageService lineageService;
    private final Configuration configuration;
    private final AncestorPerson rootPerson;

    public ParentLineageTreeService(Configuration config, AncestorPerson rootPerson) {
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
        if (rootPerson.getFather() != null) {
            drawParentsLineage(rootPosition);
        } else if (rootPerson.getMother() != null) {
            lineageService.drawPerson(rootPosition, rootPerson);
            lineageService.drawSpouseAndSiblings(rootPosition, rootPerson);
            lineageService.drawMotherFamily(rootPosition, rootPerson);
        }
        return lineageService.getTreeModel();
    }

    private void drawParentsLineage(Position child) {
        int parentsY = child.getY() - configuration.getAdultImageHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = new Position(child.getX(), parentsY);

        lineageService.drawPerson(fatherPosition, rootPerson.getFather());
        lineageService.drawFathersFamily(fatherPosition, rootPerson.getFather());

        int motherX;
        if (configuration.isShowSiblings()) {
            int fathersSiblings = rootPerson.getFather().getMaxYoungerSiblings();
            lineageService.drawSiblings(fatherPosition, rootPerson.getFather());

            int mothersSiblings = rootPerson.getMother().getMaxOlderSiblings();
            int siblingsAmount = fathersSiblings + mothersSiblings;
            motherX = child.getX() + configuration.getAdultImageWidth() + Math.max((siblingsAmount + 2) * (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP), configuration.getWideMarriageLabel());
            lineageService.drawSiblings(new Position(motherX, parentsY), rootPerson.getMother());
        } else {
            if (rootPerson.getFather().getFather() == null && rootPerson.getFather().getMother() == null) {
                motherX = child.getX() + configuration.getAdultImageWidth() + configuration.getMarriageLabelWidth();
            } else {
                motherX = child.getX() + configuration.getAdultImageWidth() + configuration.getCoupleWidthVertical();
            }
        }

        Position motherPosition = new Position(motherX, parentsY);
        lineageService.drawPerson(motherPosition, rootPerson.getMother());
        lineageService.drawFathersFamily(motherPosition, rootPerson.getMother());

        int centerXPosition = (fatherPosition.getX() + motherX) / 2;
        Position childPosition = new Position(centerXPosition, child.getY());
        lineageService.drawPerson(childPosition, rootPerson);
        lineageService.drawSpouseAndSiblings(childPosition, rootPerson);

        if (configuration.isShowSpouses() && configuration.isShowChildren()) {
            lineageService.drawChildren(new Position(centerXPosition, child.getY()), rootPerson.getSpouseCouple());
        }

        Position LabelPosition = fatherPosition.addX(configuration.getAdultImageWidth() / 2);
        LabelPosition.addY(-configuration.getMarriageLabelHeight() / 2);
        int labelWidth = motherX - LabelPosition.getX() - configuration.getAdultImageWidth() / 2;
        lineageService.drawLabel(LabelPosition, labelWidth, rootPerson.getParents().getMarriageDate());
        lineageService.drawLine(childPosition, new Position(centerXPosition, LabelPosition.getY() + configuration.getMarriageLabelHeight()), Line.LINEAGE);
    }

}
