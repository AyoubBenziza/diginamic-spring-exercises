package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.entities.dtos.DepartmentDTO;
import fr.diginamic.springdemo.exceptions.InvalidException;
import fr.diginamic.springdemo.exceptions.NotFoundException;
import fr.diginamic.springdemo.mappers.DepartmentMapper;
import fr.diginamic.springdemo.repositories.DepartmentRepository;
import fr.diginamic.springdemo.services.DepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Get all departments
     * @return a set of DepartmentDTO
     * @throws NotFoundException if no departments are found
     * @see Department
     * @see DepartmentDTO
     * @see DepartmentService
     */
    @GetMapping
    public ResponseEntity<Set<DepartmentDTO>> getDepartments() throws NotFoundException {
        Set<Department> departments = departmentService.getDepartments();
        Set<DepartmentDTO> departmentDTOS = departments.stream()
                .map(DepartmentMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(departmentDTOS);
    }

    /**
     * Get departments with pagination
     * @param page the page number
     * @param size the page size
     * @return a page of DepartmentDTO
     * @see Page
     * @see PageRequest
     * @see Department
     * @see DepartmentDTO
     * @see DepartmentRepository
     * @see DepartmentMapper
     */
    @GetMapping("/pagination")
    public Page<DepartmentDTO> getDepartmentsPagination(@RequestParam @Min(0) int page, @RequestParam int size) {
        return departmentRepository.findAll(PageRequest.of(page, size)).map(DepartmentMapper::convertToDTO);
    }

    /**
     * Get a department by its code
     * @param code the department code
     * @return a DepartmentDTO
     * @throws NotFoundException if the department is not found
     */
    @GetMapping("/{code}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable String code) throws NotFoundException {
        Department department = departmentService.getDepartment(code);
        DepartmentDTO departmentDTO = DepartmentMapper.convertToDTO(department);
        return ResponseEntity.ok(departmentDTO);
    }

    /**
     * Get a department by its name
     * @param name the department name
     * @return a DepartmentDTO
     * @throws NotFoundException if the department is not found
     */
    @GetMapping("/search/name")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@RequestParam String name) throws NotFoundException {
        Department department = departmentService.getDepartmentByName(name);
        DepartmentDTO departmentDTO = DepartmentMapper.convertToDTO(department);
        return ResponseEntity.ok(departmentDTO);
    }

    /**
     * Get departments by name starting with a given string
     * @param name the department name
     * @return a set of DepartmentDTO
     * @throws NotFoundException if no departments are found
     * @see Department
     * @see DepartmentDTO
     * @see DepartmentService
     */
    @GetMapping("/search/name/start")
    public ResponseEntity<Set<DepartmentDTO>> getDepartmentsByNameStartingWith(@RequestParam String name) throws NotFoundException {
        Set<Department> departments = departmentService.getDepartmentsStartingWith(name);
        Set<DepartmentDTO> departmentDTOS = departments.stream()
                .map(DepartmentMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(departmentDTOS);
    }
    /**
     * Get cities in a department
     * @param code the department code
     * @return a set of CityDTO
     * @throws NotFoundException if no cities are found
     */
    @GetMapping("/{code}/cities")
    public ResponseEntity<Set<CityDTO>> getCitiesInDepartment(@PathVariable String code) throws NotFoundException {
        Set<City> cities = departmentService.getCities(code);
        Set<CityDTO> cityDTOS = cities.stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(cityDTOS);
    }

    /**
     * Get the most populated cities in a department
     * @param code the department code
     * @param nbCities the number of cities to return
     * @return a list of CityDTO
     * @throws NotFoundException if no cities are found
     */
    @GetMapping("/{code}/cities/mostPopulated")
    public ResponseEntity<Set<CityDTO>> getTopNCitiesInDepartment(@PathVariable String code, @RequestParam int nbCities) throws NotFoundException {
        Set<City> cities = departmentService.getTopNCities(code, nbCities);
        Set<CityDTO> cityDTOS = cities.stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(cityDTOS);
    }

    /**
     * Get cities in a department with a population range
     * @param code the department code
     * @param min the minimum population
     * @param max the maximum population
     * @return a set of CityDTO
     * @throws NotFoundException if no cities are found
     */
    @GetMapping("/{code}/cities/search/population/range")
    public ResponseEntity<Set<CityDTO>> getCitiesInDepartmentWithPopulationBetween(@PathVariable String code, @RequestParam int min, @RequestParam int max) throws NotFoundException {
        Set<City> cities = departmentService.getCitiesWithPopulationRange(code, min, max);
        Set<CityDTO> cityDTOS = cities.stream()
                .map(CityMapper::convertToDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(cityDTOS);
    }

    /**
     * Get cities in a department with a population greater than a given value
     * @param department the department code
     * @param result the binding result
     * @return a set of CityDTO
     * @throws InvalidException if the request is invalid
     */
    @PostMapping
    public ResponseEntity<DepartmentDTO> addDepartment(@Valid @RequestBody Department department, BindingResult result) throws InvalidException, NotFoundException {
        if (result.hasErrors()) {
            throw new InvalidException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        Department newDepartment = departmentService.create(department);
        return ResponseEntity.ok(DepartmentMapper.convertToDTO(newDepartment));
    }

    /**
     * Add cities to a department
     * @param code the department code
     * @param cities the cities to add
     * @return a DepartmentDTO
     * @throws InvalidException if the request is invalid
     */
    @PostMapping("/{code}/cities")
    public ResponseEntity<DepartmentDTO> addCitiesToDepartment(@PathVariable String code, @Valid @RequestBody Set<City> cities, BindingResult result) throws InvalidException, NotFoundException {
        if (result.hasErrors()) {
            throw new InvalidException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        Department updatedDepartment = departmentService.addCities(code, cities);
        return ResponseEntity.ok(DepartmentMapper.convertToDTO(updatedDepartment));
    }

    /**
     * Update a department
     * @param code the department code
     * @param department the department to update
     * @return a DepartmentDTO
     * @throws InvalidException if the request is invalid
     */
    @PutMapping("/{code}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable String code, @Valid @RequestBody Department department, BindingResult result) throws InvalidException, NotFoundException {
        if (result.hasErrors()) {
            throw new InvalidException(result.getAllErrors().getFirst().getDefaultMessage());
        }
        Department updatedDepartment = departmentService.update(code, department);
        return ResponseEntity.ok(DepartmentMapper.convertToDTO(updatedDepartment));
    }

    /**
     * Delete a department
     * @param code the department code
     * @return a response entity
     * @throws NotFoundException if the department is not found
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String code) throws NotFoundException {
        departmentService.delete(code);
        return ResponseEntity.ok("Department deleted");
    }

}
