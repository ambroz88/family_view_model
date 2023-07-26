package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.dto.AncestorPerson;

public interface SelectionService {
    AncestorPerson select(Long personId);
}
