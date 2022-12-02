package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;

public interface CommonAncestorService {

    void addPerson(Position center, AncestorPerson person);

    void addRootPerson(Position center, AncestorPerson person);

    void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    void addYoungerSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    void addOlderSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    void addLabel(Position labelPosition, int labelWidth, String text);

    void addLine(Position start, Position end, Relation lineType);

    void addHeraldry(Position childPosition, String simpleBirthPlace);

    void addChildrenHeraldry(Position childPosition, AncestorCouple spouseCouple);

    int generateChildren(Position fatherPosition, AncestorCouple spouseCouple);

    Position addParents(Position childPosition, AncestorPerson person);

    Position addRootSpouses(Position rootPersonPosition, AncestorPerson person);

    void addStraightChildrenLine(Position childPosition);

    TreeModel getTreeModel();

    ParentsDto generateParents(Position heraldryPosition, AncestorPerson child);
}
