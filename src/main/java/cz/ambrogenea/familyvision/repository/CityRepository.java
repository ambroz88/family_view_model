package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CityRepository {

    private final HashMap<Long, City> cityMap = new HashMap<>();
    private final HashMap<String, City> cityNameMap = new HashMap<>();

    public City save(City city) {
        cityMap.put(city.getId(), city);
        cityNameMap.put(city.getName(), city);
        return city;
    }

    public Optional<City> findById(Long id) {
        return Optional.ofNullable(cityMap.get(id));
    }

    public Optional<City> findByName(String name) {
        return Optional.ofNullable(cityNameMap.get(name));
    }

    public List<City> findAll() {
        return new ArrayList<>(cityMap.values());
    }

    public void deleteById(Long id) {
        cityMap.remove(id);
    }

}
