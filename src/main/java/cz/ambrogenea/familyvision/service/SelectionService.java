package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.model.dto.AncestorPerson;

public interface SelectionService {
    AncestorPerson select(Long personId);
}
