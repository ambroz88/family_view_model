package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class VerticalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public VerticalAncestorService(ConfigurationService configuration) {
        super(configuration);
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = childPosition.addXAndY(
                getConfiguration().getMarriageLabelWidth(), -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);
        int labelYShift = 0;
        if (configuration.getAdultDiagram() != Diagrams.SCROLL) {
            labelYShift = Spaces.LABEL_GAP;
        }

        Position label = new Position(childPosition.getX(), motherPosition.getY()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - getConfiguration().getMarriageLabelHeight() + labelYShift);

        addLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
//        addPerson(motherPosition, mother);
        addPerson(motherPosition.addXAndY(0, 3 * labelYShift), mother);
        return motherPosition;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        int fatherYShift = -getConfiguration().getAdultImageHeightAlternative()
                - getConfiguration().getAdultImageHeight()
                - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = childPosition.addXAndY(0, fatherYShift);
        addPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public Position addSpouse(Position rootPosition, AncestorPerson root) {
        if (root.getSpouse() != null) {
            int labelYShift = 0;
            if (configuration.getAdultDiagram() != Diagrams.SCROLL) {
                labelYShift = Spaces.LABEL_GAP;
            }
            Position label = rootPosition.addXAndY(0,
                    getConfiguration().getAdultImageHeightAlternative() / 2 - labelYShift);
            addLabel(label, getConfiguration().getMarriageLabelWidth(),
                    root.getSpouseCouple().getDatePlace().getLocalizedDate(getConfiguration().getLocale())
            );

            Position spousePosition = rootPosition.addXAndY(getConfiguration().getSpouseDistance(),
                    getConfiguration().getCoupleVerticalDifference());
            addPerson(spousePosition, root.getSpouse());
            return spousePosition;
        } else {
            return rootPosition;
        }
    }

    @Override
    public void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (int) (getConfiguration().getAdultImageWidth() / 2 + getConfiguration().getAdultImageWidth() * 0.25);
        }
        Position spousePosition = new Position(rootSibling.getX() + spouseGap, rootSibling.getY());

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            int lineY = rootSibling.getY() - (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
            addLine(new Position(rootSibling.getX(), lineY),
                    new Position(spousePosition.getX(), lineY),
                    Relation.SIDE);
        }

        addOlderSiblings(rootSibling, rootChild);
        addYoungerSiblings(spousePosition, rootChild);
    }

    @Override
    public void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap = lastSpouseX - rootSibling.getX();

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);
        }

        addOlderSiblings(rootSibling, rootChild);
        addYoungerSiblings(new Position(lastSpouseX, rootSibling.getY()), rootChild);
    }

    @Override
    public void addVerticalLineToParents(Position child) {
        int endY = child.getY() - getConfiguration().getAdultImageHeight()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        addLine(child, new Position(child.getX(), endY), Relation.DIRECT);
    }
}
