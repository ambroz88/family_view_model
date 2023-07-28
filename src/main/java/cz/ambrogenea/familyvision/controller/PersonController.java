package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.mapper.response.PersonResponseMapper;
import cz.ambrogenea.familyvision.service.PersonService;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.util.Services;

import java.util.List;
import java.util.stream.Collectors;

public class PersonController {

    private final PersonService personService = Services.person();

    public String getPerson(Long id) {
        try {
            final Person response = personService.getById(id);
            return JsonParser.get().writeValueAsString(PersonResponseMapper.map(response));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<String> getAll(Long treeId) {
        return personService.getPeopleInTree(treeId).stream().map(person -> {
                    try {
                        return JsonParser.get().writeValueAsString(PersonResponseMapper.map(person));
                    } catch (JsonProcessingException e) {
                        return "";
                    }
                }
        ).collect(Collectors.toList());
    }

}
