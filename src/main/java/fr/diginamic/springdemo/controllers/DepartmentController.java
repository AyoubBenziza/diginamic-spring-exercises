package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.entities.dtos.DepartmentDTO;
import fr.diginamic.springdemo.mappers.DepartmentMapper;
import fr.diginamic.springdemo.repositories.CityRepository;
import fr.diginamic.springdemo.repositories.DepartmentRepository;
import fr.diginamic.springdemo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.diginamic.springdemo.mappers.CityMapper;

/**
 * A controller for the Department entity
 * @see Department
 * @see DepartmentDTO
 * @see DepartmentService
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
     * The CityRepository instance
     * @see CityRepository
     */
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Get all departments
     * @return a set of DepartmentDTO
     */
    @GetMapping
    public Set<DepartmentDTO> getDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Get a department by its code
     * @param code the department code
     * @return a DepartmentDTO
     */
    @GetMapping("/{code}")
    public DepartmentDTO getDepartment(@PathVariable String code) {
        return DepartmentMapper.convertToDTO(departmentRepository.findByCode(code));
    }

    /**
     * Get a department by its name
     * @param name the department name
     * @return a DepartmentDTO
     */
    @GetMapping("/name")
    public DepartmentDTO getDepartmentByName(@RequestParam String name) {
        return DepartmentMapper.convertToDTO(departmentRepository.findByName(name));
    }

    /**
     * Get departments by name
     * @param name the department name
     * @return a set of DepartmentDTO
     */
    @GetMapping("/searchByName")
    public Set<DepartmentDTO> getDepartmentsByName(@RequestParam String name) {
        return departmentRepository.findByNameStartingWith(name).stream()
                .map(DepartmentMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Get cities in a department
     * @param code the department code
     * @return a set of CityDTO
     */
    @GetMapping("/{code}/cities")
    public Set<CityDTO> getCitiesInDepartment(@PathVariable String code) {
        return cityRepository.findCitiesByDepartment_Code(code).stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Get the most populated cities in a department
     * @param code the department code
     * @param nbCities the number of cities to return
     * @return a list of CityDTO
     */
    @GetMapping("/{code}/cities/mostPopulated")
    public List<CityDTO> getTopNCitiesInDepartment(@PathVariable String code, @RequestParam int nbCities) {
        return cityRepository.findAllByDepartment_CodeOrderByPopulationDesc(code, PageRequest.of(0, nbCities)).map(CityMapper::convertToDTO).getContent();
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
        return cityRepository.findCitiesByPopulationBetweenAndDepartment_Code(min, max, code).stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Get cities in a department with a population greater than a given value
     * @param department the department code
     * @param result the binding result
     * @return a set of CityDTO
     */
    @PostMapping
    public ResponseEntity<?> addDepartment(@Valid @RequestBody Department department, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid department data");
        }
        Department savedDepartment = departmentRepository.save(department);
        DepartmentDTO savedDepartmentDTO = DepartmentMapper.convertToDTO(savedDepartment);
        return ResponseEntity.ok(savedDepartmentDTO);
    }

    /**
     * Add cities to a department
     * @param code the department code
     * @param cities the cities to add
     * @return a DepartmentDTO
     */
    @PostMapping("/{code}/cities")
    public ResponseEntity<?> addCitiesToDepartment(@PathVariable String code, @Valid @RequestBody Set<City> cities, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid city data");
        }
        Department department = departmentRepository.findByCode(code);
        if (department == null) {
            return ResponseEntity.badRequest().body("Department not found");
        }
        cities.forEach(city -> city.setDepartment(department));
        cityRepository.saveAll(cities);
        return ResponseEntity.ok(DepartmentMapper.convertToDTO(department));
    }

    /**
     * Update a department
     * @param code the department code
     * @param department the department to update
     */
    @PutMapping("/{code}")
    public ResponseEntity<?> updateDepartment(@PathVariable String code, @Valid @RequestBody Department department, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid department data");
        }
        if (departmentRepository.existsByCode(code)) {
            return ResponseEntity.notFound().build();
        }
        departmentService.update(code, department);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete a department
     * @param code the department code
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteDepartment(@PathVariable String code) {
        if (departmentRepository.existsByCode(code)) {
            return ResponseEntity.notFound().build();
        }
        departmentRepository.deleteByCode(code);
        return ResponseEntity.ok().build();
    }

}
