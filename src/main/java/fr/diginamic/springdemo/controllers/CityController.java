package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService service;

    @GetMapping
    public Set<City> getCities() {
        return service.getCities();
    }
}
