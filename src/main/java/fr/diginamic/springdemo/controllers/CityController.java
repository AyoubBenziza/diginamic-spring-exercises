package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.repositories.CityRepository;
import fr.diginamic.springdemo.services.CityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public Set<CityDTO> getCities() {
        return cityService.extractCities();
    }

    @GetMapping("/pagination")
    public Page<CityDTO> getCitiesPagination(@RequestParam int page, @RequestParam int size) {
        PageRequest pagination = PageRequest.of(page, size);
        return cityRepository.findAll(pagination).map(CityDTO::new);
    }

    @GetMapping("/{id}")
    public CityDTO getCity(@PathVariable int id) {
        return cityService.extractCity(id);
    }

    @GetMapping("/search")
    public CityDTO getCityByName(@RequestParam String name) {
        return cityService.extractCityByName(name);
    }

    @GetMapping("/search/starting")
    public Set<CityDTO> getCitiesByNameStartingWith(@RequestParam String name) {
        return cityService.extractCitiesByNameStartingWith(name);
    }

    @PostMapping
    public ResponseEntity<String> addCity(@Valid @RequestBody City city, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid city data");
        }
        cityService.insertCities(city);
        return ResponseEntity.ok("City added");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCity(@Valid @PathVariable @Min(0)  int id, @Valid @RequestBody City city, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid city data");
        }
        cityService.update(id, city);
        return ResponseEntity.ok("City updated");
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable int id) {
        cityService.delete(id);
    }
}
