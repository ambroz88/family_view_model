package cz.ambrogenea.familyvision.service;

import java.util.List;

import cz.ambrogenea.familyvision.dto.parsing.DocumentInputs;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface DocumentGeneratorService {

    List<DocumentInputs> generateFamilies(String personId, Long treeId);

    XWPFDocument generateDocument(List<DocumentInputs> familyInputs);

}
