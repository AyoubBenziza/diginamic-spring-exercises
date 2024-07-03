package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DepartmentService departmentService;

    public Set<CityDTO> extractCities() {
        return cityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toSet());
    }

    public CityDTO extractCity(int id) {
        return convertToDTO(cityRepository.findById(id).orElse(null));
    }

    public City getCity(int id) {
        return cityRepository.findById(id).orElse(null);
    }

    public CityDTO extractCityByName(String name) {
        return convertToDTO(cityRepository.findByName(name));
    }

    public Set<CityDTO> insertCities(City... citiesToAdd) {
        for (City city : citiesToAdd) {
            if (city.getDepartment() != null) {
                Department department = departmentService.getDepartment(city.getDepartment().getId());
                if (department != null) {
                    city.setDepartment(department);
                }
            }
            cityRepository.save(city);
        }
        return extractCities();
    }

    public void update(int id, City city) {
        City cityToUpdate = getCity(id);
        if (cityToUpdate != null) {
            cityToUpdate.setName(city.getName());
            cityToUpdate.setPopulation(city.getPopulation());
            cityToUpdate.setDepartment(city.getDepartment());
            cityRepository.save(cityToUpdate);
        }
    }

    public void delete(int id) {
        City city = getCity(id);
        if (city != null) {
            cityRepository.delete(city);
        }
    }

    public CityDTO convertToDTO(City city) {
        if (city != null) {
            return new CityDTO(city.getName(), city.getPopulation());
        }
        return null;
    }
}
