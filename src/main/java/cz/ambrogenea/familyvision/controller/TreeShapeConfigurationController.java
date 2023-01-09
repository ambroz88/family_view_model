package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.mapper.command.TreeShapeConfigurationCommandMapper;
import cz.ambrogenea.familyvision.mapper.response.TreeShapeConfigurationResponseMapper;
import cz.ambrogenea.familyvision.model.request.TreeShapeConfigurationRequest;
import cz.ambrogenea.familyvision.model.response.TreeShapeConfigurationResponse;
import cz.ambrogenea.familyvision.service.TreeShapeConfigurationService;
import cz.ambrogenea.familyvision.service.impl.TreeShapeConfigurationServiceImpl;
import cz.ambrogenea.familyvision.service.util.JsonParser;

public class TreeShapeConfigurationController {

    private final TreeShapeConfigurationService configurationService;

    public TreeShapeConfigurationController() {
        configurationService = new TreeShapeConfigurationServiceImpl();
    }

    public String get() throws JsonProcessingException {
        TreeShapeConfigurationResponse response = TreeShapeConfigurationResponseMapper.map(configurationService.get());
        return JsonParser.get().writeValueAsString(response);
    }

    public String update(String configurationRequest) throws JsonProcessingException {
        TreeShapeConfigurationRequest request = JsonParser.get().readValue(configurationRequest, TreeShapeConfigurationRequest.class);
        TreeShapeConfigurationResponse response = TreeShapeConfigurationResponseMapper.map(
                configurationService.update(TreeShapeConfigurationCommandMapper.map(request))
        );
        return JsonParser.get().writeValueAsString(response);
    }

}
