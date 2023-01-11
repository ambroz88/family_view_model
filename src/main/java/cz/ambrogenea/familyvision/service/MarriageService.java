package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.model.command.MarriageCreateCommand;

public interface MarriageService {
    Marriage createMarriage(MarriageCreateCommand marriageCreateCommand);
    Marriage getMarriageByGedcomId(String gedcomId);
    MarriageDto getMarriageDtoByGedcomId(String gedcomId);
}
