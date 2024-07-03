package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public Map<Integer, City> getCities() {
        return cityService.getAll();
    }

    @PostMapping
    public void addCity(City city) {
        cityService.add(city);
    }

    @PutMapping("/{id}")
    public void updateCity(@PathVariable int id, City city) {
        cityService.update(id, city);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable int id) {
        cityService.delete(id);
    }
}
