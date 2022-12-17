package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Residence;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.*;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.mapper.PersonRecordMapper;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.TreeModelService;
import org.ambrogenea.familyview.utils.Tools;

public class TreeModelServiceImpl implements TreeModelService {

    private final ConfigurationService configuration;
    private final TreeModel treeModel;

    public TreeModelServiceImpl(AncestorPerson rootPerson, String treeName, ConfigurationService configuration) {
        this.configuration = configuration;
        final PersonRecord personRecord = PersonRecordMapper.map(rootPerson, new Position());
        if (configuration.isShowResidence()) {
            addResidence(personRecord);
        }
        this.treeModel = new TreeModel(personRecord, treeName);
    }

    @Override
    public void addPerson(Position position, AncestorPerson person) {
        if (person != null) {
            final PersonRecord personRecord = PersonRecordMapper.map(person, position);
            treeModel.addPerson(personRecord);
            if (configuration.isShowResidence()) {
                addResidence(personRecord);
            }
        }
    }

    private void addResidence(final PersonRecord person) {
        Residence residence;
        int yShift;
        Position position;
        for (int i = 0; i < person.residences().size(); i++) {

            residence = person.residences().get(i);
            if (!residence.getCity().isEmpty()) {

                if (person.directLineage()) {
                    yShift = -configuration.getAdultImageHeight() / 2 + i * (Spaces.RESIDENCE_SIZE + 5);

                    if (person.getSex().equals(Sex.FEMALE)) {
                        position = person.position().addXAndY(
                                (configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP) / 2, yShift);
                    } else {
                        position = person.position().addXAndY(
                                -(configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP) / 2 - Spaces.RESIDENCE_SIZE, yShift);
                    }
                } else {
                    position = person.position().addXAndY(
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
    public void addMarriage(Position labelPosition, String text, LabelType labelType) {
        treeModel.marriages().add(new Marriage(labelPosition, text, labelType));
    }

    @Override
    public void addLine(Position start, Position end, Relation lineType) {
        if (start.x() == end.x() || start.y() == end.y()) {
            Line straightLine = new Line(start.x(), start.y(), end.x(), end.y(), lineType);
            treeModel.lines().add(straightLine);
        } else {
            Line horizontal;
            Line vertical;
            Arc arc;
            if (start.x() < end.x()) {
                horizontal = new Line(start.x() + Arc.RADIUS, end.y(), end.x(), end.y(), lineType);
                if (start.y() < end.y()) {//ancestors
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() - Arc.RADIUS, lineType);
                    arc = new Arc(new Position(start.x(), end.y() - 2 * Arc.RADIUS), 180, lineType);
                } else {//children
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() + Arc.RADIUS, lineType);
                    arc = new Arc(new Position(start.x(), end.y()), 90, lineType);
                }
            } else {
                horizontal = new Line(start.x() - Arc.RADIUS, end.y(), end.x(), end.y(), lineType);
                if (start.y() < end.y()) {
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() - Arc.RADIUS, lineType);
                    arc = new Arc(new Position(start.x() - 2 * Arc.RADIUS, end.y() - 2 * Arc.RADIUS), -90, lineType);
                } else {
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() + Arc.RADIUS, lineType);
                    arc = new Arc(new Position(start.x() - 2 * Arc.RADIUS, end.y()), 0, lineType);
                }
            }

            treeModel.arcs().add(arc);
            treeModel.lines().add(horizontal);
            treeModel.lines().add(vertical);
        }
    }

    @Override
    public void addHeraldry(Position heraldryPosition, String simpleBirthPlace) {
        if (!simpleBirthPlace.isEmpty()) {
            String birthPlace = Tools.replaceDiacritics(simpleBirthPlace);
            treeModel.images().add(new ImageModel(birthPlace, heraldryPosition));
        }
    }

    @Override
    public TreeModel getTreeModel() {
        return treeModel;
    }
}
