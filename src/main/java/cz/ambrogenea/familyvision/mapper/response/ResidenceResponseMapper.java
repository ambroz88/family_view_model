package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.dto.tree.ResidenceDto;
import cz.ambrogenea.familyvision.model.response.tree.PositionResponse;
import cz.ambrogenea.familyvision.model.response.tree.ResidenceResponse;

public class ResidenceResponseMapper {

    public static ResidenceResponse map(ResidenceDto residenceDto) {
        return new ResidenceResponse(
                new PositionResponse(
                        residenceDto.position().x(),
                        residenceDto.position().y()
                ),
                residenceDto.city(),
                residenceDto.date(),
                residenceDto.number()
        );
    }

}
