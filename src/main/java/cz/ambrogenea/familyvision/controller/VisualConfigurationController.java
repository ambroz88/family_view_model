package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.mapper.command.VisualConfigurationCommandMapper;
import cz.ambrogenea.familyvision.mapper.response.VisualConfigurationResponseMapper;
import cz.ambrogenea.familyvision.model.request.VisualConfigurationRequest;
import cz.ambrogenea.familyvision.model.response.VisualConfigurationResponse;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.util.Services;

public class VisualConfigurationController {

    private final VisualConfigurationService configurationService = Services.visual();

    public String get() throws JsonProcessingException {
        VisualConfigurationResponse response = VisualConfigurationResponseMapper.map(configurationService.get());
        return JsonParser.get().writeValueAsString(response);
    }

    public String update(String configurationRequest) throws JsonProcessingException {
        VisualConfigurationRequest request = JsonParser.get().readValue(configurationRequest, VisualConfigurationRequest.class);
        VisualConfigurationResponse response = VisualConfigurationResponseMapper.map(
                configurationService.update(VisualConfigurationCommandMapper.map(request))
        );
        return JsonParser.get().writeValueAsString(response);
    }

}
