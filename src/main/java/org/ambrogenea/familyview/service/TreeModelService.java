package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.enums.Relation;

public interface TreeModelService {
    void addPerson(Position center, AncestorPerson person);
    void addMarriage(Position labelPosition, String text, LabelType labelType);
    void addLine(Position start, Position end, Relation lineType);
    void addHeraldry(Position childPosition, String simpleBirthPlace);
    TreeModel getTreeModel();
}
