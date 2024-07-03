package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CityService {

    private final Map<Integer,City> cities = new HashMap<>(
            Map.of(
                    1, new City("Paris", 2_140_526),
                    2, new City("Marseille", 870_018),
                    3, new City("Lyon", 515_695),
                    4, new City("Toulouse", 479_553),
                    5, new City("Nice", 342_522)
            )
    );

    public Map<Integer,City> getAll() {
        return cities;
    }

    public void add(City... citiesToAdd) {
        for (City city : citiesToAdd) {
            cities.put(cities.size() + 1, city);
        }
    }

    public void update(int id, City city) {
        cities.put(id, city);
    }

    public void delete(int id) {
        cities.remove(id);
    }
}
