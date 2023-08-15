package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.enums.LabelType;
import cz.ambrogenea.familyvision.model.dto.AncestorCouple;
import cz.ambrogenea.familyvision.model.dto.AncestorPerson;
import cz.ambrogenea.familyvision.model.dto.tree.Position;
import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;

public interface TreeModelService {
    void addPerson(Position center, AncestorPerson person);
    void addMarriage(Position labelPosition, String text, AncestorCouple couple, LabelType labelType);
    void addLine(Position start, Position end);
    void addHeraldry(Position childPosition, String simpleBirthPlace);
    TreeModel getTreeModel();
}
