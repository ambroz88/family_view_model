package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.*;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.TreeModelService;
import org.ambrogenea.familyview.utils.Tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TreeModelServiceImpl implements TreeModelService {

    private final ConfigurationService configuration;
    private final TreeModel treeModel;

    public TreeModelServiceImpl(ConfigurationService configuration) {
        this.configuration = configuration;
        this.treeModel = new TreeModel();
    }

    @Override
    public void addPerson(Position position, AncestorPerson person) {
        final PersonRecord personRecord = new PersonRecord(person, position);
        treeModel.addPerson(personRecord);
        if (configuration.isShowResidence()) {
            addResidence(personRecord);
        }
    }

    private void addResidence(final PersonRecord person) {
        Residence residence;
        int yShift;
        Position position;
        for (int i = 0; i < person.getResidences().size(); i++) {

            residence = person.getResidences().get(i);
            if (!residence.getCity().isEmpty()) {

                if (person.isDirectLineage()) {
                    yShift = -configuration.getAdultImageHeight() / 2 + i * (Spaces.RESIDENCE_SIZE + 5);

                    if (person.getSex().equals(Sex.FEMALE)) {
                        position = person.getPosition().addXAndY(
                                (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP) / 2, yShift);
                    } else {
                        position = person.getPosition().addXAndY(
                                -(configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP) / 2 - Spaces.RESIDENCE_SIZE, yShift);
                    }
                } else {
                    position = person.getPosition().addXAndY(
                            -(configuration.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) / 2 - Spaces.RESIDENCE_SIZE,
                            -configuration.getSiblingImageHeight() / 2 + i * (Spaces.RESIDENCE_SIZE + 5)
                    );
                }

                treeModel.addResidence(position, residence);
                treeModel.addCityToRegister(residence.getCity());
            }
        }
    }

    @Override
    public void addRootPerson(Position center, AncestorPerson person) {
        treeModel.setRootPerson(new PersonRecord(person, center));
    }

    @Override
    public void addMarriage(Position labelPosition, String text, LabelType labelType) {
        treeModel.getMarriages().add(new Marriage(labelPosition, text, labelType));
    }

    @Override
    public void addLine(Position start, Position end, Relation lineType) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {

            Line straightLine = new Line(start.getX(), start.getY(), end.getX(), end.getY());
            straightLine.setType(lineType);
            treeModel.getLines().add(straightLine);

        } else {
            Line horizontal;
            Line vertical;
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

    @Override
    public void addHeraldry(Position heraldryPosition, String simpleBirthPlace) {
        if (!simpleBirthPlace.isEmpty()) {
            String birthPlace = Tools.replaceDiacritics(simpleBirthPlace);

            InputStream heraldry = ClassLoader.getSystemResourceAsStream("heraldry/" + birthPlace + ".png");
            if (heraldry != null) {
                try {
                    BufferedImage heraldryImage = ImageIO.read(heraldry);
                    treeModel.getImages().add(new ImageModel(heraldryImage, heraldryPosition, Spaces.VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public TreeModel getTreeModel() {
        return treeModel;
    }
}
