package fr.diginamic.springdemo.repositories;

import fr.diginamic.springdemo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);
    Set<City> findByNameStartingWith(String name);
    Set<City> findByPopulationIsGreaterThan(int population);
    Set<City> findByPopulationBetween(int min, int max);
}
