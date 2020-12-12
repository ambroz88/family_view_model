package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.PageSetup;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface Pageable {

    PageSetup createPageSetup(AncestorPerson person);

}
