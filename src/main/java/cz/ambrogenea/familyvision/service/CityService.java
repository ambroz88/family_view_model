package cz.ambrogenea.familyvision.service;

import cz.ambrogenea.familyvision.domain.City;

import java.util.List;

public interface CityService {
    City createCity(String city);
    City saveCity(City city);
    City getCityByName(String name);
    City getCityById(Long id);
    City findCityByName(String name);
    List<City> getCities();
}
