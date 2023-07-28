package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.domain.FamilyTree;
import cz.ambrogenea.familyvision.mapper.command.FamilyTreeCommandMapper;
import cz.ambrogenea.familyvision.mapper.response.FamilyTreeResponseMapper;
import cz.ambrogenea.familyvision.model.request.FamilyTreeRequest;
import cz.ambrogenea.familyvision.service.FamilyTreeService;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.util.Services;

public class FamilyTreeController {

    private final FamilyTreeService familyTreeService = Services.familyTree();

    public String create(String request) throws JsonProcessingException {
        FamilyTreeRequest familyTreeRequest = JsonParser.get().readValue(request, FamilyTreeRequest.class);
        FamilyTree domain = familyTreeService.saveFamilyTree(FamilyTreeCommandMapper.map(familyTreeRequest));
        return JsonParser.get().writeValueAsString(FamilyTreeResponseMapper.map(domain));
    }

    public String getFamilyTree(Long id) throws JsonProcessingException {
        FamilyTree domain = familyTreeService.getFamilyTreeById(id);
        return JsonParser.get().writeValueAsString(FamilyTreeResponseMapper.map(domain));
    }

    public void deleteFamilyTree(Long id) {
        familyTreeService.deleteFamilyTree(id);
    }
}
