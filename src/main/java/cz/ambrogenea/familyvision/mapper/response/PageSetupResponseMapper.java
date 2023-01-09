package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.dto.tree.PageMaxCoordinates;
import cz.ambrogenea.familyvision.dto.tree.PageSetup;
import cz.ambrogenea.familyvision.dto.tree.Position;
import cz.ambrogenea.familyvision.model.response.tree.PageSetupResponse;
import cz.ambrogenea.familyvision.model.response.tree.PositionResponse;
import cz.ambrogenea.familyvision.service.util.Config;

public class PageSetupResponseMapper {

    public static PageSetupResponse map(PageMaxCoordinates pageMaxCoordinates) {
        VisualConfiguration config = Config.visual();
        int horizontalBorder = config.getAdultImageWidth() / 2 + Spaces.SIBLINGS_GAP;
        int verticalBorder = config.getAdultImageHeightAlternative() / 2 + Spaces.SIBLINGS_GAP;
        int maxX = pageMaxCoordinates.getMaxX() + horizontalBorder;
        int minX = pageMaxCoordinates.getMinX() - horizontalBorder;
        int maxY = pageMaxCoordinates.getMaxY() + verticalBorder;
        int minY = pageMaxCoordinates.getMinY() - verticalBorder;
        if (Config.visual().isShowTitle()) {
            maxY += Spaces.TITLE_HEIGHT;
        }

        return new PageSetupResponse(
                new PositionResponse(
                        minX,
                        minY
                ),
                maxX - minX,
                maxY - minY
        );
    }

}
