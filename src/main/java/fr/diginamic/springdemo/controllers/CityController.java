package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public Set<CityDTO> getCities() {
        return cityService.extractCities();
    }

    @GetMapping("/{id}")
    public CityDTO getCity(@PathVariable int id) {
        return cityService.extractCity(id);
    }

    @GetMapping("/search")
    public CityDTO getCityByName(@RequestParam String name) {
        return cityService.extractCityByName(name);
    }

    @PostMapping
    public Set<CityDTO> addCity(@RequestBody City city) {
        return cityService.insertCities(city);
    }

    @PutMapping("/{id}")
    public void updateCity(@PathVariable int id, @RequestBody City city) {
        cityService.update(id, city);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable int id) {
        cityService.delete(id);
    }
}
