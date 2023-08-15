package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.model.dto.parsing.DocumentInputs;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.List;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface DocumentGeneratorService {

    List<DocumentInputs> generateFamilies(Long id);

    XWPFDocument generateDocument(List<DocumentInputs> familyInputs);

}
