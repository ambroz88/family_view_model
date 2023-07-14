package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.AncestorCouple;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.enums.LabelType;

public interface TreeModelService {
    void addPerson(Position center, AncestorPerson person);
    void addMarriage(Position labelPosition, String text, AncestorCouple couple, LabelType labelType);
    void addLine(Position start, Position end);
    void addHeraldry(Position childPosition, String simpleBirthPlace);
    TreeModel getTreeModel();
}
