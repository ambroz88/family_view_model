package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;

public interface SpecificAncestorService extends CommonAncestorService {

    Position addMother(Position childPosition, AncestorPerson mother, String marriageDate);

    Position addFather(Position childPosition, AncestorPerson father);

    Position addSpouse(Position rootPosition, AncestorPerson root);

    void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild);

    void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX);

    void addVerticalLineToParents(Position child);

}
