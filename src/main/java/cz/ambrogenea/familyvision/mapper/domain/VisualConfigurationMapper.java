package cz.ambrogenea.familyvision.mapper.domain;

import cz.ambrogenea.familyvision.domain.VisualConfiguration;
import cz.ambrogenea.familyvision.model.command.VisualConfigurationCommand;

public class VisualConfigurationMapper {

    public static VisualConfiguration map(VisualConfigurationCommand configurationCommand) {
        final VisualConfiguration visualConfiguration = new VisualConfiguration();
        visualConfiguration.setAdultImageWidth(configurationCommand.adultImageWidth());
        visualConfiguration.setAdultImageHeight(configurationCommand.adultImageHeight());
        visualConfiguration.setAdultFontSize(configurationCommand.adultFontSize());
        visualConfiguration.setSiblingImageWidth(configurationCommand.siblingImageWidth());
        visualConfiguration.setSiblingImageHeight(configurationCommand.siblingImageHeight());
        visualConfiguration.setSiblingFontSize(configurationCommand.siblingFontSize());
        visualConfiguration.setDiagram(configurationCommand.diagram());
        visualConfiguration.setMarriageLabelShape(configurationCommand.marriageLabelShape());
        visualConfiguration.setBackground(configurationCommand.background());
        visualConfiguration.setVerticalShift(configurationCommand.verticalShift());
        visualConfiguration.setShowAge(configurationCommand.showAge());
        visualConfiguration.setShowOccupation(configurationCommand.showOccupation());
        visualConfiguration.setShowTitle(configurationCommand.showTitle());
        visualConfiguration.setShowPlaces(configurationCommand.showPlaces());
        visualConfiguration.setShortenPlaces(configurationCommand.shortenPlaces());
        visualConfiguration.setShowOrdinances(configurationCommand.showOrdinances());
        visualConfiguration.setLocale(configurationCommand.locale());
        visualConfiguration.setResetMode(configurationCommand.resetMode());
        return visualConfiguration;
    }
}
