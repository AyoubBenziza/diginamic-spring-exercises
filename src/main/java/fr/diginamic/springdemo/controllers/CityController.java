package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.exceptions.InvalidException;
import fr.diginamic.springdemo.exceptions.NotFoundException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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
     * @throws NotFoundException if no cities are found
     */
    @GetMapping
    public ResponseEntity<Set<CityDTO>> getCities() throws NotFoundException {
        Set<CityDTO> cities = cityService.getCities().stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(cities);
    }

    /**
     * Get cities with pagination
     * @param page the page number
     * @param size the page size
     * @return a page of CityDTO
     * @see Page
     * @see Pageable
     */
    @GetMapping("/pagination")
    public Page<CityDTO> getCitiesWithPagination(@RequestParam @Min(0) int page, @RequestParam int size) {
        return cityRepository.findAll(PageRequest.of(page, size)).map(CityMapper::convertToDTO);
    }

    /**
     * Get a city by its id
     * @param id the city id
     * @return a response entity
     * @throws NotFoundException if the city is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCity(@PathVariable int id) throws NotFoundException {
        City city = cityService.getCity(id);
        CityDTO cityDTO = CityMapper.convertToDTO(city);
        return ResponseEntity.ok(cityDTO);
    }

    /**
     * Get a city by its name
     * @param name the city name
     * @return a city DTO
     * @throws NotFoundException if the city is not found
     */
    @GetMapping("/search/name")
    public ResponseEntity<CityDTO> getCityByName(@RequestParam @Size(min = 1) String name) throws NotFoundException {
        City city = cityService.getCityByName(name);
        CityDTO cityDTO = CityMapper.convertToDTO(city);
        return ResponseEntity.ok(cityDTO);
    }

    /**
     * Get cities by their name starting with a given value
     * @param name the name value
     * @return a set of CityDTO
     * @throws NotFoundException if no cities are found
     */
    @GetMapping("/search/name/start")
    public ResponseEntity<Set<CityDTO>> getCitiesByNameStartingWith(@RequestParam String name) throws NotFoundException {
        Set<City> cities = cityService.getCitiesByNameStartingWith(name);
        Set<CityDTO> citiesDTO = cities.stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(citiesDTO);
    }

    /**
     * Get cities with a population greater than a given value
     * @param population the population value
     * @return a set of CityDTO
     * @throws NotFoundException if no cities are found
     */
    @GetMapping("/search/population/greater")
    public ResponseEntity<Set<CityDTO>> getCitiesByPopulationGreaterThan(@RequestParam @Min(0) int population) throws NotFoundException {
        Set<City> cities = cityService.getCitiesByPopulationGreaterThan(population);
        Set<CityDTO> citiesDTO = cities.stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(citiesDTO);
    }

    /**
     * Get cities with a population range
     * @param min the minimum population value
     * @param max the maximum population value
     * @return a set of CityDTO
     * @throws NotFoundException if no cities are found
     */
    @GetMapping("/search/population/range")
    public ResponseEntity<Set<CityDTO>> getCitiesByPopulationRange(@RequestParam int min, @RequestParam int max) throws NotFoundException {
        Set<City> cities = cityService.getCitiesByPopulationRange(min, max);
        Set<CityDTO> citiesDTO = cities.stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(citiesDTO);
    }

    /**
     * Add a city
     * @param city the city data
     * @param result the binding result
     * @return a response entity
     */
    @PostMapping
    public ResponseEntity<CityDTO> addCity(@Valid @RequestBody City city, BindingResult result) throws InvalidException {
        if (result.hasErrors()) {
            throw new InvalidException(result.getAllErrors().getFirst().getDefaultMessage());
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
    public ResponseEntity<CityDTO> updateCity(@PathVariable @Min(0) int id, @Valid @RequestBody City city, BindingResult result) throws InvalidException, NotFoundException {
        if (result.hasErrors()) {
            throw new InvalidException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        City updatedCity = cityService.update(id, city);
        CityDTO updatedCityDTO = CityMapper.convertToDTO(updatedCity);
        return ResponseEntity.ok(updatedCityDTO);
    }

    /**
     * Delete a city by its id
     * @param id the city id
     * @return a response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable int id) throws NotFoundException {
        cityService.delete(id);
        return ResponseEntity.ok("City deleted");
    }

    /**
     * Export cities to a CSV file
     * @param response the HTTP response
     * @see ExportsUtils
     * @see HttpServletResponse
     */
    @GetMapping("/export")
    public void exportCities(HttpServletResponse response) throws NotFoundException {
        ExportsUtils.toCSVFile(cityService.getCities(), "cities", new String[]{"name", "population", "departmentCode"}, response);
    }
}
