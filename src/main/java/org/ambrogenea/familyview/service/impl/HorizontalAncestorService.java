package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.SpecificAncestorService;

public class HorizontalAncestorService extends CommonAncestorServiceImpl implements SpecificAncestorService {

    public HorizontalAncestorService(ConfigurationService configuration) {
        super(configuration);
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = childPosition.addXAndY(getConfiguration().getHalfSpouseLabelSpace(),
                -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position label = new Position(
                childPosition.getX() - getConfiguration().getMarriageLabelWidth() / 2,
                motherPosition.getY() - getConfiguration().getMarriageLabelHeight() / 2
        );

        addLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        addPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        Position fatherPosition = childPosition.addXAndY(-getConfiguration().getHalfSpouseLabelSpace(),
                -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        addPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public void addFirstParents(Position childPosition, AncestorPerson child) {
        addGrandParents(childPosition, child);
    }

    @Override
    public void addGrandParents(Position childPosition, AncestorPerson child) {
        addAllParents(childPosition, child);
    }

    @Override
    public void addAllParents(Position childPosition, AncestorPerson child) {
        if (child.getMother() != null) {
            int verticalShift = -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
            double motherParentsCount = Math.min(child.getMother().getInnerParentsCount(), child.getMother().getLastParentsCount());

            Position motherPosition;
            if (child.getFather() == null) {
                motherPosition = childPosition.addXAndY(0, verticalShift);
            } else {
                double fatherParentsCount = Math.min(child.getFather().getInnerParentsCount(), child.getFather().getLastParentsCount());

                Position fatherPosition;
                if (motherParentsCount == 0 || fatherParentsCount == 0) {
                    fatherPosition = childPosition.addXAndY(-getConfiguration().getHalfSpouseLabelSpace(), verticalShift);
                    motherPosition = childPosition.addXAndY(getConfiguration().getHalfSpouseLabelSpace(), verticalShift);
                } else {
                    double motherParentWidth = (getConfiguration().getCoupleWidth() + Spaces.SIBLINGS_GAP) * motherParentsCount;
                    double fatherParentWidth = (getConfiguration().getCoupleWidth() + Spaces.SIBLINGS_GAP) * fatherParentsCount;
                    int halfParentWidth = (int) (fatherParentWidth + motherParentWidth) / 2;
                    fatherPosition = childPosition.addXAndY(-halfParentWidth, verticalShift);
                    motherPosition = childPosition.addXAndY(halfParentWidth, verticalShift);
                }

                addPerson(fatherPosition, child.getFather());
                Position labelPosition = fatherPosition.addXAndY(getConfiguration().getAdultImageWidth() / 2,
                        -getConfiguration().getMarriageLabelHeight() / 2);
                addLabel(labelPosition, motherPosition.getX() - fatherPosition.getX() - getConfiguration().getAdultImageWidth(),
                        child.getParents().getMarriageDate());
                addAllParents(fatherPosition, child.getFather());
            }

            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(childPosition, child.getSimpleBirthPlace());
            }

            addPerson(motherPosition, child.getMother());
            addAllParents(motherPosition, child.getMother());
            addVerticalLineToParents(childPosition);
        }
    }

    @Override
    public void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = getConfiguration().getSpouseLabelSpace();
        }
        Position spousePosition = new Position(rootSibling.getX() + spouseGap, rootSibling.getY());

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);
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
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        addLine(child, new Position(child.getX(), endY), Relation.DIRECT);
    }
}
