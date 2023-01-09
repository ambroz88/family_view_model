package cz.ambrogenea.familyvision.mapper.response;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.model.response.VisualConfigurationResponse;

public class VisualConfigurationResponseMapper {

    public static VisualConfigurationResponse map(VisualConfiguration visualConfiguration) {
        return new VisualConfigurationResponse(
                visualConfiguration.getAdultImageWidth(),
                visualConfiguration.getAdultImageHeight(),
                visualConfiguration.getAdultFontSize(),
                visualConfiguration.getSiblingImageWidth(),
                visualConfiguration.getSiblingImageHeight(),
                visualConfiguration.getSiblingFontSize(),
                visualConfiguration.getDiagram(),
                visualConfiguration.getMarriageLabelShape(),
                visualConfiguration.getBackground(),
                visualConfiguration.getVerticalShift(),
                visualConfiguration.isShowAge(),
                visualConfiguration.isShowOccupation(),
                visualConfiguration.isShowTitle(),
                visualConfiguration.isShowPlaces(),
                visualConfiguration.isShortenPlaces(),
                visualConfiguration.isShowOrdinances(),
                visualConfiguration.getLocale(),
                visualConfiguration.isResetMode()
        );
    }

}
