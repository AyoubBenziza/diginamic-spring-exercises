package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.repositories.CityRepository;
import fr.diginamic.springdemo.services.CityService;
import fr.diginamic.springdemo.utils.ExportsUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * A controller for the City entity
 * @see City
 * @see CityDTO
 * @see CityService
 * @see CityRepository
 * @see fr.diginamic.springdemo.entities.dtos.CityDTO
 *
 * @author AyoubBenziza
 */
@RestController
@RequestMapping("/cities")
public class CityController {

    /**
     * The CityService instance
     * @see CityService
     */
    @Autowired
    private CityService cityService;

    /**
     * The CityRepository instance
     * @see CityRepository
     */
    @Autowired
    private CityRepository cityRepository;

    /**
     * Get all cities
     * @return a set of CityDTO
     */
    @GetMapping
    public Set<CityDTO> getCities() {
        return cityService.extractCities();
    }

    /**
     * Get cities with pagination
     * @param page the page number
     * @param size the page size
     * @return a page of CityDTO
     */
    @GetMapping("/pagination")
    public Page<CityDTO> getCitiesPagination(@RequestParam int page, @RequestParam int size) {
        PageRequest pagination = PageRequest.of(page, size);
        return cityRepository.findAll(pagination).map(CityDTO::new);
    }

    /**
     * Get a city by its id
     * @param id the city id
     * @return a CityDTO
     */
    @GetMapping("/{id}")
    public CityDTO getCity(@PathVariable int id) {
        return cityService.extractCity(id);
    }

    /**
     * Get cities with a population greater than a given value
     * @param name the city name
     * @return a CityDTO
     */
    @GetMapping("/search")
    public CityDTO getCityByName(@RequestParam String name) {
        return cityService.extractCityByName(name);
    }

    /**
     * Get cities with a population greater than a given value
     * @param name the city name
     * @return a CityDTO
     */
    @GetMapping("/search/starting")
    public Set<CityDTO> getCitiesByNameStartingWith(@RequestParam String name) {
        return cityService.extractCitiesByNameStartingWith(name);
    }

    /**
     * Get cities with a population greater than a given value
     * @param city the city name
     * @param result the binding result
     * @return a CityDTO
     */
    @PostMapping
    public ResponseEntity<String> addCity(@Valid @RequestBody City city, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid city data");
        }
        cityService.insertCities(city);
        return ResponseEntity.ok("City added");
    }

    /**
     * Update a city
     * @param id the city id
     * @param city the city data
     * @param result the binding result
     * @return a response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCity(@Valid @PathVariable @Min(0)  int id, @Valid @RequestBody City city, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid city data");
        }
        cityService.update(id, city);
        return ResponseEntity.ok("City updated");
    }

    /**
     * Delete a city
     * @param id the city id
     */
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable int id) {
        cityService.delete(id);
    }

    /**
     * Export cities to a CSV file
     * @param response the HTTP response
     */
    @GetMapping("/export")
    public void exportCities(HttpServletResponse response) {
        ExportsUtils.toCSVFile(cityService.extractCities(), "cities", new String[]{"name", "population", "departmentCode"}, response);
    }
}
