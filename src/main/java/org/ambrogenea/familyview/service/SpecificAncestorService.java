package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.Position;

public interface SpecificAncestorService extends CommonAncestorService {

    Position addMother(Position childPosition, AncestorPerson mother, String marriageDate);

    Position addFather(Position childPosition, AncestorPerson father);

    void addFirstParents(Position childPosition, AncestorPerson child);

    void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild);

    void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX);

    void addVerticalLineToParents(Position child);

}
