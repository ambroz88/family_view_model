package org.ambrogenea.familyview.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.*;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.CommonAncestorService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.utils.Tools;

public class CommonAncestorServiceImpl implements CommonAncestorService {

    protected final ConfigurationService configuration;
    protected final TreeModel treeModel;

    public CommonAncestorServiceImpl(ConfigurationService configuration) {
        this.configuration = configuration;
        this.treeModel = new TreeModel();
    }

    @Override
    public void addPerson(Position personCenter, AncestorPerson person) {
        treeModel.addPersonWithResidence(new PersonRecord(person, personCenter), configuration);
    }

    @Override
    public void addRootPerson(Position center, AncestorPerson person) {
        treeModel.setRootPerson(new PersonRecord(person, center));
    }

    @Override
    public void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling) {
        addOlderSiblings(rootSiblingPosition, rootSibling);
        addYoungerSiblings(rootSiblingPosition, rootSibling);
    }

    @Override
    public void addOlderSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position lineEnd = rootSiblingPosition.addXAndY(0,
                -(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        Position siblingPosition = new Position(rootSiblingPosition.getX() - Spaces.SIBLINGS_GAP, rootSiblingPosition.getY());

        for (int i = olderSiblingCount - 1; i >= 0; i--) {
            sibling = rootChild.getOlderSiblings().get(i);

            startX = siblingPosition.getX() - getConfiguration().getSiblingImageWidth() - Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            addPerson(siblingPosition, sibling);
            if (i == 0) {
                addLine(siblingPosition, lineEnd, Relation.SIDE);
            } else {
                addStraightChildrenLine(siblingPosition);
            }
        }
    }

    @Override
    public void addYoungerSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position lineEnd = rootSiblingPosition.addXAndY(0,
                -(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        int youngerSiblingsCount = rootChild.getYoungerSiblings().size();
        Position siblingPosition = new Position(rootSiblingPosition.getX() + Spaces.SIBLINGS_GAP, rootSiblingPosition.getY());

        for (int i = 0; i < youngerSiblingsCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = siblingPosition.getX() + getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            addPerson(siblingPosition, sibling);
            if (i == youngerSiblingsCount - 1) {
                addLine(siblingPosition, lineEnd, Relation.SIDE);
            } else {
                addStraightChildrenLine(siblingPosition);
            }
        }
    }

    @Override
    public void addLabel(Position labelPosition, int labelWidth, String text) {
        treeModel.getMarriages().add(new Marriage(labelPosition, text, labelWidth));
    }

    @Override
    public void addLine(Position start, Position end, Relation lineType) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {

            Line straigthLine = new Line(start.getX(), start.getY(), end.getX(), end.getY());
            straigthLine.setType(lineType);
            treeModel.getLines().add(straigthLine);

        } else {

            Line horizontal;
            Line vertical;
            if (getConfiguration().getLabelShape().equals(LabelShape.RECTANGLE)) {

                horizontal = new Line(start.getX(), end.getY(), end.getX(), end.getY());
                vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY());
                treeModel.getLines().add(horizontal);
                treeModel.getLines().add(vertical);

            } else if (getConfiguration().getLabelShape().equals(LabelShape.OVAL)) {

                Arc arc;
                if (start.getX() < end.getX()) {
                    horizontal = new Line(start.getX() + Arc.RADIUS, end.getY(), end.getX(), end.getY());
                    if (start.getY() < end.getY()) {//ancestors
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() - Arc.RADIUS);
                        arc = new Arc(new Position(start.getX(), end.getY() - 2 * Arc.RADIUS), 180, lineType);
                    } else {//children
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() + Arc.RADIUS);
                        arc = new Arc(new Position(start.getX(), end.getY()), 90, lineType);
                    }
                } else {
                    horizontal = new Line(start.getX() - Arc.RADIUS, end.getY(), end.getX(), end.getY());
                    if (start.getY() < end.getY()) {
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() - Arc.RADIUS);
                        arc = new Arc(new Position(start.getX() - 2 * Arc.RADIUS, end.getY() - 2 * Arc.RADIUS), -90, lineType);
                    } else {
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() + Arc.RADIUS);
                        arc = new Arc(new Position(start.getX() - 2 * Arc.RADIUS, end.getY()), 0, lineType);
                    }
                }

                arc.setRelation(lineType);
                horizontal.setType(lineType);
                vertical.setType(lineType);

                treeModel.getArcs().add(arc);
                treeModel.getLines().add(horizontal);
                treeModel.getLines().add(vertical);
            }
        }
    }

    @Override
    public void addHeraldry(Position childPosition, String simpleBirthPlace) {
        if (!simpleBirthPlace.isEmpty()) {
            String birthPlace = Tools.replaceDiacritics(simpleBirthPlace);

            InputStream heraldry = ClassLoader.getSystemResourceAsStream("heraldry/" + birthPlace + ".png");
            if (heraldry != null) {
                try {
                    BufferedImage heraldryImage = ImageIO.read(heraldry);
                    Position heraldryPosition = childPosition.addXAndY(0, -(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

                    treeModel.getImages().add(new ImageModel(heraldryImage, heraldryPosition, Spaces.VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    @Override
    public int generateChildren(Position fatherPosition, AncestorCouple spouseCouple) {
        int childrenWidth = 0;
        if (spouseCouple != null) {

            if (getConfiguration().isShowChildren() && !spouseCouple.getChildren().isEmpty()) {
                Position coupleCenterPosition = fatherPosition.addXAndY(getConfiguration().getHalfSpouseLabelSpace(), 0);

                Position heraldryPosition = coupleCenterPosition.addXAndY(
                        0, (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

                addLine(heraldryPosition, coupleCenterPosition, Relation.SIDE);

                childrenWidth = addChildren(heraldryPosition, spouseCouple);
            }

        }
        return childrenWidth;
    }

    private int addChildren(Position heraldryPosition, AncestorCouple spouseCouple) {
        int childrenCount = spouseCouple.getChildren().size();
        int childrenWidth = childrenCount * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) - Spaces.HORIZONTAL_GAP;

        Position childrenPosition = heraldryPosition.addXAndY(
                getConfiguration().getSiblingImageWidth() / 2 - childrenWidth / 2,
                (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        if (getConfiguration().isShowHeraldry()) {
            addChildrenHeraldry(new Position(heraldryPosition.getX(), childrenPosition.getY()), spouseCouple);
        }

        for (int i = 0; i < childrenCount; i++) {
            if (i == 0 || i == childrenCount - 1) {
                addLine(childrenPosition, heraldryPosition, Relation.SIDE);
            } else {
                addStraightChildrenLine(childrenPosition);
            }
            //TODO: draw spouse of the children
            addPerson(childrenPosition, spouseCouple.getChildren().get(i));
            childrenPosition = childrenPosition.addXAndY(getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP, 0);
        }

        return childrenWidth / 2;
    }

    @Override
    public Position addParents(Position childPosition, AncestorPerson person) {
        Position fatherPosition = childPosition.addXAndY(-getConfiguration().getHalfSpouseLabelSpace(),
                -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position motherPosition = childPosition.addXAndY(getConfiguration().getHalfSpouseLabelSpace(),
                -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        addPerson(fatherPosition, person.getFather());
        addPerson(motherPosition, person.getMother());
        Position label = fatherPosition.addXAndY(
                getConfiguration().getAdultImageWidth() / 2 + Spaces.LABEL_GAP,
                -getConfiguration().getMarriageLabelHeight() / 2
        );

        addLabel(label, getConfiguration().getMarriageLabelWidth() - 2 * Spaces.LABEL_GAP,
                person.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale()));
        return new Position(childPosition.getX(), fatherPosition.getY());
    }

    @Override
    public Position addRootSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseDistance = getConfiguration().getMarriageLabelWidth() + getConfiguration().getAdultImageWidth();
            Position spousePosition = new Position(rootPersonPosition);

            Position label = rootPersonPosition.addXAndY(
                    -getConfiguration().getAdultImageWidth() / 2 - getConfiguration().getMarriageLabelWidth(),
                    -getConfiguration().getMarriageLabelHeight() / 2);

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                spousePosition = spousePosition.addXAndY(spouseDistance, 0);
                label = label.addXAndY(spouseDistance, 0);
                addPerson(spousePosition, person.getSpouse(index));
                addLabel(label, getConfiguration().getMarriageLabelWidth(),
                        person.getSpouseCouple(index).getDatePlace().getLocalizedDate(getConfiguration().getLocale()));
            }

            return spousePosition;
        }
        return rootPersonPosition;
    }

    @Override
    public void addStraightChildrenLine(Position siblingPosition) {
        Position endLine = siblingPosition.addXAndY(0, -(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);
        addLine(siblingPosition, endLine, Relation.SIDE);
    }

    @Override
    public void addChildrenHeraldry(Position childPosition, AncestorCouple spouseCouple) {
        String birthPlace = spouseCouple.getChildren().get(0).getBirthDatePlace().getSimplePlace();
        addHeraldry(childPosition, birthPlace);
    }

    @Override
    public TreeModel getTreeModel() {
        return treeModel;
    }

    protected ConfigurationService getConfiguration() {
        return configuration;
    }

    protected void addLineAboveSpouse(Position rootSibling, int spouseGap) {
        int verticalShift = (configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
        Position linePosition = rootSibling.addXAndY(0, -verticalShift);
        addLine(linePosition, linePosition.addXAndY(spouseGap, 0), Relation.SIDE);
    }

}
