package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CityService {

    private final Set<City> cities = new HashSet<>(
            Set.of(
                    new City("Paris", 2_148_000),
                    new City("Lyon", 516_092),
                    new City("Marseille", 861_635)
            )
    );

    public Set<City> getAll() {
        return cities;
    }

    public void add(City city) {
        cities.add(city);
    }
}
