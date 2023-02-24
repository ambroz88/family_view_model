package cz.ambrogenea.familyvision.mapper.command;

import cz.ambrogenea.familyvision.model.command.VisualConfigurationCommand;
import cz.ambrogenea.familyvision.model.request.VisualConfigurationRequest;

public class VisualConfigurationCommandMapper {

    public static VisualConfigurationCommand map(VisualConfigurationRequest configurationRequest){
        return new VisualConfigurationCommand(
                configurationRequest.adultImageWidth(),
                configurationRequest.adultImageHeight(),
                configurationRequest.adultFontSize(),
                configurationRequest.siblingImageWidth(),
                configurationRequest.siblingImageHeight(),
                configurationRequest.siblingFontSize(),
                configurationRequest.diagram(),
                configurationRequest.marriageLabelShape(),
                configurationRequest.background(),
                configurationRequest.verticalShift(),
                configurationRequest.showAge(),
                configurationRequest.showOccupation(),
                configurationRequest.showTitle(),
                configurationRequest.showPlaces(),
                configurationRequest.shortenPlaces(),
                configurationRequest.showOrdinances(),
                configurationRequest.showChildrenCount(),
                configurationRequest.locale(),
                configurationRequest.resetMode()
        );
    }
}
