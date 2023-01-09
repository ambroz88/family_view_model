package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.dto.tree.Line;
import cz.ambrogenea.familyvision.model.response.tree.LineResponse;

public class LineResponseMapper {

    public static LineResponse map(Line line) {
        return new LineResponse(
                line.startX(),
                line.startY(),
                line.endX(),
                line.endY()
        );
    }
}
