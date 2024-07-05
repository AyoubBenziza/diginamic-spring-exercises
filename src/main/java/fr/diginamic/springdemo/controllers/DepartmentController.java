package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.entities.dtos.DepartmentDTO;
import fr.diginamic.springdemo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * A controller for the Department entity
 * @see Department
 * @see DepartmentDTO
 * @see DepartmentService
 * @see fr.diginamic.springdemo.entities.dtos.DepartmentDTO
 *
 * @author AyoubBenziza
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    /**
     * The DepartmentService instance
     * @see DepartmentService
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * Get all departments
     * @return a set of DepartmentDTO
     */
    @GetMapping
    public Set<DepartmentDTO> getDepartments() {
        return departmentService.extractDepartments();
    }

    /**
     * Get a department by its code
     * @param code the department code
     * @return a DepartmentDTO
     */
    @GetMapping("/{code}")
    public DepartmentDTO getDepartment(@PathVariable String code) {
        return departmentService.extractDepartment(code);
    }

    /**
     * Get a department by its name
     * @param name the department name
     * @return a DepartmentDTO
     */
    @GetMapping("/name")
    public DepartmentDTO getDepartmentByName(@RequestParam String name) {
        return departmentService.extractDepartmentByName(name);
    }

    /**
     * Get departments by name
     * @param name the department name
     * @return a set of DepartmentDTO
     */
    @GetMapping("/searchByName")
    public Set<DepartmentDTO> getDepartmentsByName(@RequestParam String name) {
        return departmentService.extractDepartmentsByName(name);
    }

    /**
     * Get cities in a department
     * @param code the department code
     * @return a set of CityDTO
     */
    @GetMapping("/{code}/cities")
    public Set<CityDTO> getCitiesInDepartment(@PathVariable String code) {
        return departmentService.extractCities(code);
    }

    /**
     * Get the most populated cities in a department
     * @param code the department code
     * @param nbCities the number of cities to return
     * @return a set of CityDTO
     */
    @GetMapping("/{code}/cities/mostPopulated")
    public Set<CityDTO> getTopNCitiesInDepartment(@PathVariable String code,@RequestParam int nbCities) {
        return departmentService.findTopNCitiesInDepartment(code, nbCities);
    }

    /**
     * Get cities in a department with a population greater than a given value
     * @param code the department code
     * @param min the minimum population
     * @param max the maximum population
     * @return a set of CityDTO
     */
    @GetMapping("/{code}/cities/searchPopulationBetween")
    public Set<CityDTO> getCitiesInDepartmentWithPopulationBetween(@PathVariable String code, @RequestParam int min, @RequestParam int max) {
        return departmentService.findCitiesWithPopulationBetween(code, min, max);
    }

    /**
     * Get cities in a department with a population greater than a given value
     * @param department the department code
     * @param result the binding result
     * @return a set of CityDTO
     */
    @PostMapping
    public ResponseEntity<String> addDepartment(@Valid @RequestBody Department department, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid department data");
        }
        departmentService.insertDepartments(department);
        return ResponseEntity.ok("Department added");
    }

    /**
     * Add cities to a department
     * @param code the department code
     * @param cities the cities to add
     * @return a DepartmentDTO
     */
    @PostMapping("/{code}/cities")
    public DepartmentDTO addCitiesToDepartment(@PathVariable String code,@Valid @RequestBody Set<City> cities) {
        return departmentService.addCities(code, cities);
    }

    /**
     * Update a department
     * @param code the department code
     * @param department the department to update
     */
    @PutMapping("/{code}")
    public void updateDepartment(@PathVariable String code, @RequestBody Department department) {
        departmentService.update(code, department);
    }

    /**
     * Delete a department
     * @param code the department code
     */
    @DeleteMapping("/{code}")
    public void deleteDepartment(@PathVariable String code) {
        departmentService.delete(code);
    }

}
