package fr.diginamic.springdemo.repositories;

import fr.diginamic.springdemo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository for the City entity
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * Find a city by its name
     * @param name the name of the city
     * @return the city
     */
    City findByName(String name);

    /**
     * Find cities by their name starting with a given string
     * @param name the string to search for
     * @return the cities
     */
    Set<City> findByNameStartingWith(String name);

    /**
     * Find cities by their population
     * @param population the population to search for
     * @return the cities
     */
    Set<City> findByPopulationIsGreaterThan(int population);

    /**
     * Find cities by their population
     * @param min the minimum population
     * @param max the maximum population
     * @return the cities
     */
    Set<City> findByPopulationBetween(int min, int max);
}
