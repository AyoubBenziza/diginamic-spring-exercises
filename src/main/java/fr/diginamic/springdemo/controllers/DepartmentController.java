package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.entities.dtos.DepartmentDTO;
import fr.diginamic.springdemo.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Set<DepartmentDTO> getDepartments() {
        return departmentService.extractDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentDTO getDepartment(@PathVariable int id) {
        return departmentService.extractDepartment(id);
    }

    @GetMapping("/search")
    public DepartmentDTO getDepartmentByName(@RequestParam String name) {
        return departmentService.extractDepartmentByName(name);
    }

    @GetMapping("/{id}/top-cities")
    public Set<CityDTO> getTopNCitiesInDepartment(@PathVariable int id,@RequestParam int nbCities) {
        return departmentService.findTopNCitiesInDepartment(id, nbCities);
    }

    @GetMapping("/{id}/cities/searchPopulationBetween")
    public Set<CityDTO> getCitiesInDepartmentWithPopulationBetween(@PathVariable int id, @RequestParam int min, @RequestParam int max) {
        return departmentService.findCitiesWithPopulationBetween(id, min, max);
    }

    @PostMapping
    public Set<DepartmentDTO> addDepartment(@RequestBody Department department) {
        return departmentService.insertDepartments(department);
    }

    @PostMapping("/{id}/cities")
    public DepartmentDTO addCitiesToDepartment(@PathVariable int id, @RequestBody Set<City> cities) {
        return departmentService.addCities(id, cities);
    }

    @PutMapping("/{id}")
    public void updateDepartment(@PathVariable int id, @RequestBody Department department) {
        departmentService.update(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable int id) {
        departmentService.delete(id);
    }

}
