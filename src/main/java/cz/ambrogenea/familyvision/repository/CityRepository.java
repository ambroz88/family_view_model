package cz.ambrogenea.familyvision.repository;

import cz.ambrogenea.familyvision.domain.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CityRepository {

    private final HashMap<Long, City> cityMap = new HashMap<>();
    private final HashMap<String, City> originalNameMap = new HashMap<>();
    private final HashMap<String, List<City>> cityNameMap = new HashMap<>();

    public City save(City city) {
        cityMap.put(city.getId(), city);
        originalNameMap.put(city.getOriginalName(), city);
        List<City> cities;
        if (cityNameMap.containsKey(city.getName())) {
            cities = cityNameMap.get(city.getName());
        } else {
            cities = new ArrayList<>();
        }
        cities.add(city);
        cityNameMap.put(city.getName(), cities);
        return city;
    }

    public Optional<City> findById(Long id) {
        return Optional.ofNullable(cityMap.get(id));
    }

    public List<City> findByName(String name) {
        return cityNameMap.getOrDefault(name, new ArrayList<>());
    }

    public Optional<City> findByOriginalName(String name) {
        return Optional.ofNullable(originalNameMap.get(name));
    }

    public List<City> findAll() {
        return new ArrayList<>(cityMap.values());
    }

    public void deleteById(Long id) {
        cityMap.remove(id);
    }

}
