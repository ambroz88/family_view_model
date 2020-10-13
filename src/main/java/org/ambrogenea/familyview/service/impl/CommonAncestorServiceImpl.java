package org.ambrogenea.familyview.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.*;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.service.CommonAncestorService;
import org.ambrogenea.familyview.utils.Tools;

public class CommonAncestorServiceImpl implements CommonAncestorService {

    protected final Configuration configuration;
    protected final TreeModel treeModel;

    public CommonAncestorServiceImpl(Configuration configuration) {
        this.configuration = configuration;
        this.treeModel = new TreeModel();
    }

    @Override
    public void drawPerson(Position personCenter, AncestorPerson person) {
        treeModel.getPersons().add(new PersonRecord(person, personCenter));
    }

    @Override
    public void drawSiblings(Position rootSiblingPosition, AncestorPerson rootSibling) {
        drawOlderSiblings(rootSiblingPosition, rootSibling);
        drawYoungerSiblings(rootSiblingPosition, rootSibling);
    }

    @Override
    public void drawOlderSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position lineEnd = new Position(rootSiblingPosition);
        lineEnd.addY(-(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        Position siblingPosition = new Position(rootSiblingPosition.getX() - Spaces.SIBLINGS_GAP, rootSiblingPosition.getY());

        for (int i = olderSiblingCount - 1; i >= 0; i--) {
            sibling = rootChild.getOlderSiblings().get(i);

            startX = siblingPosition.getX() - getConfiguration().getSiblingImageWidth() - Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            drawPerson(siblingPosition, sibling);
            drawLine(siblingPosition, lineEnd, Line.SIBLINGS);
        }
    }

    @Override
    public void drawYoungerSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position lineEnd = new Position(rootSiblingPosition);
        lineEnd.addY(-(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        int youngerSiblingsCount = rootChild.getYoungerSiblings().size();
        Position siblingPosition = new Position(rootSiblingPosition.getX() + Spaces.SIBLINGS_GAP, rootSiblingPosition.getY());

        for (int i = 0; i < youngerSiblingsCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = siblingPosition.getX() + getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            drawPerson(siblingPosition, sibling);
            drawLine(siblingPosition, lineEnd, Line.SIBLINGS);
        }
    }

    @Override
    public void drawLabel(Position labelPosition, int labelWidth, String text) {
        treeModel.getMarriages().add(new Marriage(labelPosition, text, labelWidth));
    }

    @Override
    public void drawLine(Position start, Position end, int lineType) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {

            Line straigthLine = new Line(start.getX(), start.getY(), end.getX(), end.getY());
            straigthLine.setType(lineType);
            treeModel.getLines().add(straigthLine);

        } else {

            Line horizontal;
            Line vertical;
            if (getConfiguration().getLabelShape().equals(LabelShape.RECTANGLE)) {

                horizontal = new Line(start.getX(), start.getY(), end.getX(), start.getY());
                vertical = new Line(end.getX(), start.getY(), end.getX(), end.getY());
                treeModel.getLines().add(horizontal);
                treeModel.getLines().add(vertical);

            } else if (getConfiguration().getLabelShape().equals(LabelShape.OVAL)) {

                Arc arc;
                if (start.getX() < end.getX()) {
                    if (start.getY() < end.getY()) {//ancestors
                        horizontal = new Line(start.getX() + Arc.RADIUS, end.getY(), end.getX(), end.getY());
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() - Arc.RADIUS);
                        arc = new Arc(new Position(start.getX(), end.getY() - 2 * Arc.RADIUS), 180);
                    } else {//children
                        horizontal = new Line(start.getX() + Arc.RADIUS, end.getY(), end.getX(), end.getY());
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() + Arc.RADIUS);
                        arc = new Arc(new Position(start.getX(), end.getY()), 90);
                    }
                } else {
                    if (start.getY() < end.getY()) {
                        horizontal = new Line(start.getX() - Arc.RADIUS, end.getY(), end.getX(), end.getY());
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() - Arc.RADIUS);
                        arc = new Arc(new Position(start.getX() - 2 * Arc.RADIUS, end.getY() - 2 * Arc.RADIUS), -90);
                    } else {
                        horizontal = new Line(start.getX() - Arc.RADIUS, end.getY(), end.getX(), end.getY());
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() + Arc.RADIUS);
                        arc = new Arc(new Position(start.getX() - 2 * Arc.RADIUS, end.getY()), 0);
                    }
                }

                arc.setType(lineType);
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
                    Position heraldryPosition = new Position(childPosition);
                    heraldryPosition.addY(-(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

                    treeModel.getImages().add(new ImageModel(heraldryImage, heraldryPosition, Spaces.VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    @Override
    public void addChildrenHeraldry(Position childPosition, Couple spouseCouple) {
        String birthPlace = spouseCouple.getChildren().get(0).getSimpleBirthPlace();
        addHeraldry(childPosition, birthPlace);
    }

    @Override
    public TreeModel getTreeModel() {
        return treeModel;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected void addStraightChildrenLine(int startX, int rootSiblingY) {
        int verticalShift = (configuration.getSiblingImageHeight() + Spaces.VERTICAL_GAP) / 2;

        Line vertical = new Line(startX, rootSiblingY - verticalShift, startX, rootSiblingY);
        vertical.setType(Line.SIBLINGS);
        treeModel.getLines().add(vertical);
    }

    protected void addRoundChildrenLine(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getSiblingImageHeight() + Spaces.VERTICAL_GAP) / 2;

        int startAngle;
        int xRadius;
        int yRadius;
        Arc arc;
        if (startX < rootSiblingX) {
            //older siblings
            yRadius = Arc.RADIUS;
            xRadius = Arc.RADIUS;
            startAngle = 90;
            arc = new Arc(new Position(startX, rootSiblingY - verticalShift), startAngle);
            treeModel.getArcs().add(arc);
        } else if (startX > rootSiblingX) {
            //younger siblings
            yRadius = Arc.RADIUS;
            xRadius = -Arc.RADIUS;
            startAngle = 0;
            arc = new Arc(new Position(startX - 2 * Arc.RADIUS, rootSiblingY - verticalShift), startAngle);
            treeModel.getArcs().add(arc);
        } else {
            xRadius = 0;
            yRadius = 0;
        }

        addLineAboveChildren(new Position(startX + xRadius, rootSiblingY), rootSiblingX - startX);
        Line vertical = new Line(startX, rootSiblingY - verticalShift + yRadius, startX, rootSiblingY);
        vertical.setType(Line.SIBLINGS);
        treeModel.getLines().add(vertical);
    }

    protected void addLineAboveSpouse(Position rootSibling, int spouseGap) {
        int verticalShift = (configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
        Position linePosition = new Position(rootSibling);
        linePosition.addY(-verticalShift);
        drawLine(linePosition, new Position(linePosition.getX() + spouseGap, linePosition.getY()), Line.SIBLINGS);
    }

    protected void addLineAboveChildren(Position rootSibling, int spouseGap) {
        int verticalShift = (configuration.getSiblingImageHeight() + Spaces.VERTICAL_GAP) / 2;
        Position linePosition = new Position(rootSibling);
        linePosition.addY(-verticalShift);
        drawLine(linePosition, new Position(linePosition.getX() + spouseGap, linePosition.getY()), Line.SIBLINGS);
    }

}
