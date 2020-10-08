package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.domain.Position;

public interface SpecificAncestorService extends CommonAncestorService {

    Position drawMother(Position childPosition, AncestorPerson mother, String marriageDate);

    Position drawFather(Position childPosition, AncestorPerson father);

    Position drawSpouse(Position rootPersonPosition, AncestorPerson person);

    Position drawAllSpouses(Position rootPersonPosition, AncestorPerson person);

    void drawSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild);

    void drawSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX);

    void addVerticalLineToParents(Position child);

}
