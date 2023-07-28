package cz.ambrogenea.familyvision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.domain.City;
import cz.ambrogenea.familyvision.mapper.response.CityResponseMapper;
import cz.ambrogenea.familyvision.service.CityService;
import cz.ambrogenea.familyvision.service.util.JsonParser;
import cz.ambrogenea.familyvision.service.util.Services;

import java.util.List;
import java.util.stream.Collectors;

public class CityController {

    private final CityService cityService = Services.city();

    public String getCityById(Long id) {
        try {
            final City city = cityService.getCityById(id);
            return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getCity(String name) {
        try {
            final City city = cityService.getCityByName(name);
            return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String findCity(String name) {
        try {
            final City city = cityService.findCityByName(name);
            return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<String> getAllCities() {
        return cityService.getCities().stream().map(city -> {
                    try {
                        return JsonParser.get().writeValueAsString(CityResponseMapper.map(city));
                    } catch (JsonProcessingException e) {
                        return "";
                    }
                }
        ).collect(Collectors.toList());
    }

    public void deleteCity(Long id) {
        cityService.deleteCityById(id);
    }
}
