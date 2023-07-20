package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.domain.City;
import cz.ambrogenea.familyvision.mapper.response.CityResponseMapper;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.util.Services;

import java.util.List;
import java.util.stream.Collectors;

public class CityController {

    public String getCityById(Long id) {
        try {
            final City city = Services.city().getCityById(id);
            return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getCity(String name) {
        try {
            final City city = Services.city().getCityByName(name);
            return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String findCity(String name) {
        try {
            final City city = Services.city().findCityByName(name);
            return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<String> getAllCities() {
        return Services.city().getCities().stream().map(city -> {
                    try {
                        return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
                    } catch (JsonProcessingException e) {
                        return "";
                    }
                }
        ).collect(Collectors.toList());
    }

}
