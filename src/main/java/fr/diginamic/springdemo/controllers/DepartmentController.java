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

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Set<DepartmentDTO> getDepartments() {
        return departmentService.extractDepartments();
    }

    @GetMapping("/{code}")
    public DepartmentDTO getDepartment(@PathVariable String code) {
        return departmentService.extractDepartment(code);
    }

    @GetMapping("/name")
    public DepartmentDTO getDepartmentByName(@RequestParam String name) {
        return departmentService.extractDepartmentByName(name);
    }

    @GetMapping("/searchByName")
    public Set<DepartmentDTO> getDepartmentsByName(@RequestParam String name) {
        return departmentService.extractDepartmentsByName(name);
    }

    @GetMapping("/{code}/cities")
    public Set<CityDTO> getCitiesInDepartment(@PathVariable String code) {
        return departmentService.extractCities(code);
    }

    @GetMapping("/{code}/cities/mostPopulated")
    public Set<CityDTO> getTopNCitiesInDepartment(@PathVariable String code,@RequestParam int nbCities) {
        return departmentService.findTopNCitiesInDepartment(code, nbCities);
    }

    @GetMapping("/{code}/cities/searchPopulationBetween")
    public Set<CityDTO> getCitiesInDepartmentWithPopulationBetween(@PathVariable String code, @RequestParam int min, @RequestParam int max) {
        return departmentService.findCitiesWithPopulationBetween(code, min, max);
    }

    @PostMapping
    public ResponseEntity<String> addDepartment(@Valid @RequestBody Department department, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid department data");
        }
        departmentService.insertDepartments(department);
        return ResponseEntity.ok("Department added");
    }

    @PostMapping("/{code}/cities")
    public DepartmentDTO addCitiesToDepartment(@PathVariable String code,@Valid @RequestBody Set<City> cities) {
        return departmentService.addCities(code, cities);
    }

    @PutMapping("/{code}")
    public void updateDepartment(@PathVariable String code, @RequestBody Department department) {
        departmentService.update(code, department);
    }

    @DeleteMapping("/{code}")
    public void deleteDepartment(@PathVariable String code) {
        departmentService.delete(code);
    }

}
