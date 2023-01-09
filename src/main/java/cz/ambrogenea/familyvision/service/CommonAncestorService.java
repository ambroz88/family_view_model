package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.ParentsDto;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;

public interface CommonAncestorService {
    Position addSiblingsAndDescendents(AncestorPerson person);
    void addSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);
    ParentsDto generateParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateHorizontalParents(Position heraldryPosition, AncestorPerson child);
    ParentsDto generateVerticalParents(Position heraldryPosition, AncestorPerson child);

    void addLine(Position start, Position end);
    void addPerson(Position center, AncestorPerson person);
    TreeModel getTreeModel();

}
