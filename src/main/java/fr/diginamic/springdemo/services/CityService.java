package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Update a city
     * @param id the id of the city
     * @param city the city
     */
    public void update(int id, City city) {
        City cityToUpdate = cityRepository.findById(id).orElse(null);
        if (cityToUpdate != null) {
            cityToUpdate.setName(city.getName());
            cityToUpdate.setPopulation(city.getPopulation());
            cityToUpdate.setDepartment(city.getDepartment());
            cityRepository.save(cityToUpdate);
        }
    }
}
