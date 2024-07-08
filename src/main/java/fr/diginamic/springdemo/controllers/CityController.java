package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.mappers.CityMapper;
import fr.diginamic.springdemo.repositories.CityRepository;
import fr.diginamic.springdemo.services.CityService;
import fr.diginamic.springdemo.utils.ExportsUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
 * @see CityMapper
 * @see ExportsUtils
 * @see BindingResult
 * @see ResponseEntity
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
        return cityRepository.findAll().stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Get cities with pagination
     * @param page the page number
     * @param size the page size
     * @return a page of CityDTO
     */
    @GetMapping("/pagination")
    public Page<CityDTO> getCitiesWithPagination(@RequestParam @Size(min = 1) int page, @RequestParam int size) {
        return cityRepository.findAll(PageRequest.of(page, size)).map(CityMapper::convertToDTO);
    }

    /**
     * Get a city by its id
     * @param id the city id
     * @return a response entity
     */
    @GetMapping("/{id}")
    public CityDTO getCity(@PathVariable int id) {
        return CityMapper.convertToDTO(cityRepository.findById(id).orElse(null));
    }

    /**
     * Get a city by its name
     * @param name the city name
     * @return a city DTO
     */
    @GetMapping("/search")
    public CityDTO getCityByName(@RequestParam @Size(min = 1) String name) {
        return CityMapper.convertToDTO(cityRepository.findByName(name));
    }

    /**
     * Get cities by their name starting with a given value
     * @param name the name value
     * @return a set of CityDTO
     */
    @GetMapping("/search/starting")
    public Set<CityDTO> getCitiesByNameStartingWith(@RequestParam String name) {
        return cityRepository.findByNameStartingWith(name).stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Get cities with a population greater than a given value
     * @param population the population value
     * @return a set of CityDTO
     */
    @GetMapping("/search/population/greater")
    public Set<CityDTO> getCitiesByPopulationGreaterThan(@RequestParam @Min(0) int population) {
        return cityRepository.findByPopulationIsGreaterThan(population).stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Get cities with a population range
     * @param min the minimum population value
     * @param max the maximum population value
     * @return a set of CityDTO
     */
    @GetMapping("/search/population/range")
    public Set<CityDTO> getCitiesByPopulationRange(@RequestParam int min, @RequestParam int max) {
        return cityRepository.findByPopulationBetween(min, max).stream()
                .map(CityMapper::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Add a city
     * @param city the city data
     * @param result the binding result
     * @return a response entity
     */
    @PostMapping
    public ResponseEntity<?> addCity(@Valid @RequestBody City city, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = "Invalid city data provided";
            return ResponseEntity.badRequest().body(errorMessage);
        }
        City savedCity = cityRepository.save(city);
        CityDTO savedCityDTO = CityMapper.convertToDTO(savedCity);
        return ResponseEntity.ok(savedCityDTO);
    }

    /**
     * Update a city
     * @param id the city id
     * @param city the city data
     * @param result the binding result
     * @return a response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable @Min(0) int id, @Valid @RequestBody City city, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid city data");
        }
        if (!cityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cityService.update(id, city);
        return ResponseEntity.ok("City updated");
    }

    /**
     * Delete a city by its id
     * @param id the city id
     * @return a response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        if (!cityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cityRepository.deleteById(id);
        return ResponseEntity.ok("City deleted");
    }

    /**
     * Export cities to a CSV file
     * @param response the HTTP response
     */
    @GetMapping("/export")
    public void exportCities(HttpServletResponse response) {
        ExportsUtils.toCSVFile(getCities(), "cities", new String[]{"name", "population", "departmentCode"}, response);
    }
}
