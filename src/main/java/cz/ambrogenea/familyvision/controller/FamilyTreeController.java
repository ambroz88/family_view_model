package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.domain.FamilyTree;
import cz.ambrogenea.familyvision.mapper.command.FamilyTreeCommandMapper;
import cz.ambrogenea.familyvision.mapper.response.FamilyTreeResponseMapper;
import cz.ambrogenea.familyvision.model.request.FamilyTreeRequest;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.util.Services;

public class FamilyTreeController {

    public String create(String request) throws JsonProcessingException {
        FamilyTreeRequest familyTreeRequest = JsonParser.get().readValue(request, FamilyTreeRequest.class);
        FamilyTree domain = Services.familyTree().saveFamilyTree(FamilyTreeCommandMapper.map(familyTreeRequest));
        return JsonParser.get().writeValueAsString(FamilyTreeResponseMapper.map(domain));
    }

    public String getFamilyTree(Long id) throws JsonProcessingException {
        FamilyTree domain = Services.familyTree().getFamilyTreeById(id);
        return JsonParser.get().writeValueAsString(FamilyTreeResponseMapper.map(domain));
    }

    public void deleteFamilyTree(Long id) {
        Services.familyTree().deleteFamilyTree(id);
    }
}
