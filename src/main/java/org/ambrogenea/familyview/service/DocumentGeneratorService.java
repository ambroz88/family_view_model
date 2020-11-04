package org.ambrogenea.familyview.service;

import java.util.List;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.parsing.DocumentInputs;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface DocumentGeneratorService {

    List<DocumentInputs> generateFamilies(String personId, FamilyData familyData);

    XWPFDocument generateDocument(List<DocumentInputs> familyInputs);

}