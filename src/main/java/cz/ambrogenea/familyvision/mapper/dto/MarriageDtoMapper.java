package cz.ambrogenea.familyvision.mapper.dto;

import cz.ambrogenea.familyvision.domain.Marriage;
import cz.ambrogenea.familyvision.dto.MarriageDto;
import cz.ambrogenea.familyvision.service.util.Services;

public class MarriageDtoMapper {

    public static MarriageDto map(Marriage marriage) {
        return new MarriageDto(
                Services.person().getPersonByGedcomId(marriage.getHusbandId(), marriage.getFamilyTreeId()),
                Services.person().getPersonByGedcomId(marriage.getWifeId(), marriage.getFamilyTreeId()),
                marriage.getFamilyTreeId(),
                DatePlaceMapper.map(marriage.getWeddingDate(), marriage.getWeddingPlace()),
                marriage.getChildrenIds()
        );
    }
}
