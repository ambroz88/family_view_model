package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;

public interface CommonAncestorService {
    Position addSiblingsAndDescendents(AncestorPerson person);
    void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);
    ParentsDto generateParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateHorizontalParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateVerticalParents(Position heraldryPosition, AncestorPerson child);

    void addLine(Position start, Position end, Relation lineType);
    void addPerson(Position center, AncestorPerson person);
    TreeModel getTreeModel();

}
