package cz.ambrogenea.familyvision.service.impl;

import cz.ambrogenea.familyvision.domain.City;
import cz.ambrogenea.familyvision.repository.CityRepository;
import cz.ambrogenea.familyvision.service.CityService;

import java.util.List;

public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository = new CityRepository();

    @Override
    public City createCity(String originalCityName) {
        City found = null;
        if (originalCityName != null && !originalCityName.isBlank()) {
            found = findCityByName(originalCityName);
            if (found == null) {
                found = saveCity(new City(originalCityName));
            }
        }
        return found;
    }

    @Override
    public Long getCityId(String city) {
        Long cityId = null;
        City foundCity = createCity(city);
        if (foundCity != null) {
            cityId = foundCity.getId();
        }
        return cityId;
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City getCityByName(String originalName) {
        return cityRepository.findByOriginalName(originalName).orElse(null);
    }

    @Override
    public City getCityById(Long id) {
        if (id == null) {
            return null;
        }
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public City findCityByName(String originalName) {
        String[] nameParts = originalName.split(",");
        List<City> names = cityRepository.findByName(nameParts[0]);
        return names.stream()
                .filter(city -> city.isTheSame(originalName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }
}
