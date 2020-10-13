package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Couple;

public interface CommonAncestorService {

    void drawPerson(Position center, AncestorPerson person);

    void drawSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    void drawYoungerSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    void drawOlderSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    void drawLabel(Position labelPosition, int labelWidth, String text);

    void drawLine(Position start, Position end, int lineType);

    void addHeraldry(Position childPosition, String simpleBirthPlace);

    void addChildrenHeraldry(Position childPosition, Couple spouseCouple);

    TreeModel getTreeModel();
}
