package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.City;
import cz.ambrogenea.familyvision.repository.CityRepository;
import cz.ambrogenea.familyvision.service.CityService;

import java.util.List;

public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository = new CityRepository();

    @Override
    public City createCity(String cityName) {
        City found = findCityByName(cityName);
        if (found == null) {
            return cityRepository.save(new City(cityName));
        } else {
            return found;
        }
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City getCityByName(String name) {
        return cityRepository.findByName(name).orElse(null);
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public City findCityByName(String name) {
        return cityRepository.findAll().stream()
                .filter(city -> city.contains(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
