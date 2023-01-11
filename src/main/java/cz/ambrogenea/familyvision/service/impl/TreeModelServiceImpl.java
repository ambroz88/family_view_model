package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.domain.Residence;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.*;
import cz.ambrogenea.familyvision.enums.LabelType;
import cz.ambrogenea.familyvision.enums.Sex;
import cz.ambrogenea.familyvision.mapper.dto.PersonRecordMapper;
import cz.ambrogenea.familyvision.service.TreeModelService;
import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.utils.Tools;

public class TreeModelServiceImpl implements TreeModelService {

    private final VisualConfiguration configuration;
    private final TreeModel treeModel;

    public TreeModelServiceImpl(AncestorPerson rootPerson, String treeName) {
        this.configuration = Config.visual();
        final PersonRecord personRecord = PersonRecordMapper.map(rootPerson, new Position());
        if (Config.treeShape().isShowResidence()) {
            addResidence(personRecord);
        }
        this.treeModel = new TreeModel(personRecord, treeName);
    }

    @Override
    public void addPerson(Position position, AncestorPerson person) {
        if (person != null) {
            final PersonRecord personRecord = PersonRecordMapper.map(person, position);
            treeModel.addPerson(personRecord);
            if (Config.treeShape().isShowResidence()) {
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

                    if (person.sex().equals(Sex.FEMALE)) {
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
    public void addLine(Position start, Position end) {
        if (start.x() == end.x() || start.y() == end.y()) {
            Line straightLine = new Line(start.x(), start.y(), end.x(), end.y());
            treeModel.lines().add(straightLine);
        } else {
            Line horizontal;
            Line vertical;
            Arc arc;
            if (start.x() < end.x()) {
                horizontal = new Line(start.x() + Arc.RADIUS, end.y(), end.x(), end.y());
                if (start.y() < end.y()) {//ancestors
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() - Arc.RADIUS);
                    arc = new Arc(new Position(start.x(), end.y() - 2 * Arc.RADIUS), 180);
                } else {//children
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() + Arc.RADIUS);
                    arc = new Arc(new Position(start.x(), end.y()), 90);
                }
            } else {
                horizontal = new Line(start.x() - Arc.RADIUS, end.y(), end.x(), end.y());
                if (start.y() < end.y()) {
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() - Arc.RADIUS);
                    arc = new Arc(new Position(start.x() - 2 * Arc.RADIUS, end.y() - 2 * Arc.RADIUS), -90);
                } else {
                    vertical = new Line(start.x(), start.y(), start.x(), end.y() + Arc.RADIUS);
                    arc = new Arc(new Position(start.x() - 2 * Arc.RADIUS, end.y()), 0);
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
