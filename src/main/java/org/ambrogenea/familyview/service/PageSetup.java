package org.ambrogenea.familyview.service;

import org.ambrogenea.familyview.dto.tree.Position;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface PageSetup {

    public int getWidth();

    public int getHeight();

    public Position getRootPosition();
}
