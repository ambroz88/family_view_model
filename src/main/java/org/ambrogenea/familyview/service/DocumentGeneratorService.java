package org.ambrogenea.familyview.service;

import java.io.InputStream;
import java.util.List;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface DocumentGeneratorService {

    List<TreeModel> generateFamilies(String personId, FamilyData familyData);

    XWPFDocument generateDocument(List<InputStream> familyImages);

}
