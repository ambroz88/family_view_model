package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.model.dto.tree.Arc;
import cz.ambrogenea.familyvision.model.response.tree.ArcResponse;
import cz.ambrogenea.familyvision.model.response.tree.PositionResponse;

public class ArcResponseMapper {

    public static ArcResponse map(Arc arc) {
        return new ArcResponse(
                new PositionResponse(
                        arc.leftUpperCorner().x(),
                        arc.leftUpperCorner().y()
                ),
                arc.startAngle()
        );
    }
}
