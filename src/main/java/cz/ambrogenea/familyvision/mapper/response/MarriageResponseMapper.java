package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.dto.tree.Marriage;
import cz.ambrogenea.familyvision.model.response.tree.MarriageResponse;
import cz.ambrogenea.familyvision.model.response.tree.PositionResponse;

public class MarriageResponseMapper {

    public static MarriageResponse map(Marriage marriage) {
        return new MarriageResponse(
                new PositionResponse(
                        marriage.position().x(),
                        marriage.position().y()
                ),
                marriage.date(),
                marriage.boysCount(),
                marriage.girlsCount(),
                marriage.labelType()
        );
    }
}
