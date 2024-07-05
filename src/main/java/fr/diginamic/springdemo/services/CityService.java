package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.mappers.CityMapper;
import fr.diginamic.springdemo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

/**
 * Service for the City entity
 * This class is used to interact with the CityRepository
 * @author AyoubBenziza
 */
@Service
public class CityService {

    /**
     * The CityRepository
     */
    @Autowired
    private CityRepository cityRepository;

    /**
     * Extract all cities
     * @return the cities
     */
    public Set<CityDTO> extractCities() {
        return cityRepository.findAll().stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Extract a city by its id
     * @param id the id of the city
     * @return the city
     */
    public CityDTO extractCity(int id) {
        return CityMapper.convertToDTO(cityRepository.findById(id).orElse(null));
    }

    /**
     * Get a city by its id
     * @param id the id of the city
     * @return the city
     */
    public City getCity(int id) {
        return cityRepository.findById(id).orElse(null);
    }

    /**
     * Extract a city by its name
     * @param name the name of the city
     * @return the city
     */
    public CityDTO extractCityByName(String name) {
        return CityMapper.convertToDTO(cityRepository.findByName(name));
    }

    /**
     * Extract cities by their name starting with a given string
     * @param name the string to search for
     * @return the cities
     */
    public Set<CityDTO> extractCitiesByNameStartingWith(String name) {
        return cityRepository.findByNameStartingWith(name).stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Extract cities by their population
     * @param population the population to search for
     * @return the cities
     */
    public Set<CityDTO> extractCitiesWithPopulationGreaterThan(int population) {
        return cityRepository.findByPopulationIsGreaterThan(population).stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Extract cities by their population
     * @param min the minimum population
     * @param max the maximum population
     * @return the cities
     */
    public Set<CityDTO> extractCitiesWithPopulationBetween(int min, int max) {
        return cityRepository.findByPopulationBetween(min, max).stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Insert cities
     * @param citiesToAdd the cities to add
     */
    public void insertCities(City... citiesToAdd) {
        cityRepository.saveAll(Arrays.asList(citiesToAdd));
    }

    /**
     * Update a city
     * @param id the id of the city
     * @param city the city
     */
    public void update(int id, City city) {
        City cityToUpdate = getCity(id);
        if (cityToUpdate != null) {
            cityToUpdate.setName(city.getName());
            cityToUpdate.setPopulation(city.getPopulation());
            cityToUpdate.setDepartment(city.getDepartment());
            cityRepository.save(cityToUpdate);
        }
    }

    /**
     * Delete a city
     * @param id the id of the city
     */
    public void delete(int id) {
        City city = getCity(id);
        if (city != null) {
            cityRepository.delete(city);
        }
    }
}
