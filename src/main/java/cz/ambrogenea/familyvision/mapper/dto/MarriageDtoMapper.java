package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.service.Services;

public class MarriageDtoMapper {

    public static MarriageDto map(Marriage marriage) {
        return new MarriageDto(
                Services.person().getPersonByGedcomId(marriage.getHusbandId()),
                Services.person().getPersonByGedcomId(marriage.getWifeId()),
                marriage.getWeddingDatePlace(),
                marriage.getChildrenIds()
        );
    }
}
