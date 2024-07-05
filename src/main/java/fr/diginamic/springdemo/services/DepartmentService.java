package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.entities.dtos.DepartmentDTO;
import fr.diginamic.springdemo.mappers.DepartmentMapper;
import fr.diginamic.springdemo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for the Department entity
 * This class is used to interact with the DepartmentRepository
 */
@Service
public class DepartmentService {

    /**
     * The DepartmentRepository
     */
    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Extract all departments
     * @return the departments
     */
    public Set<DepartmentDTO> extractDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Extract a department by its code
     * @param code the code of the department
     * @return the department
     */
    public DepartmentDTO extractDepartment(String code) {
        return DepartmentMapper.convertToDTO(departmentRepository.findByCode(code));
    }

    /**
     * Get a department by its code
     * @param code the code of the department
     * @return the department
     */
    public Department getDepartment(String code) {
        return departmentRepository.findByCode(code);
    }

    /**
     * Extract a department by its name
     * @param name the name of the department
     * @return the department
     */
    public DepartmentDTO extractDepartmentByName(String name) {
        return DepartmentMapper.convertToDTO(departmentRepository.findByName(name));
    }

    /**
     * Extract departments by their name starting with a given string
     * @param name the string to search for
     * @return the departments
     */
    public Set<DepartmentDTO> extractDepartmentsByName(String name) {
        return departmentRepository.findByNameStartingWith(name).stream()
                .map(DepartmentMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Extract cities in a department
     * @param id the id of the department
     * @return the cities
     */
    public Set<CityDTO> extractCities(String id) {
        return departmentRepository.findCities(id).stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                .collect(Collectors.toSet());
    }

    /**
     * Extract the top N cities in a department by their population
     * @param departmentId the id of the department
     * @param n the number of cities to return
     * @return the cities
     */
    public Set<CityDTO> findTopNCitiesInDepartment(String departmentId, int n) {
        Set<City> cities = departmentRepository.findTopNCities(departmentId, n);
        return cities.stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                .collect(Collectors.toSet());
    }

    /**
     * Find cities in a department by their population
     * @param departmentId the id of the department
     * @param minPopulation the minimum population
     * @param maxPopulation the maximum population
     * @return the cities
     */
    public Set<CityDTO> findCitiesWithPopulationBetween(String departmentId, int minPopulation, int maxPopulation) {
        Set<City> cities = departmentRepository.findCitiesByPopulationRange(departmentId, minPopulation, maxPopulation);
        return cities.stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                .collect(Collectors.toSet());
    }

    /**
     * Insert departments
     * @param departmentsToAdd the departments to add
     */
    public void insertDepartments(Department... departmentsToAdd) {
        departmentRepository.saveAll(Arrays.asList(departmentsToAdd));
    }

    /**
     * Add cities to a department
     * @param code the code of the department
     * @param cities the cities to add
     * @return the department
     */
    public DepartmentDTO addCities(String code, Set<City> cities) {
        Department department = departmentRepository.findByCode(code);
        if (department != null) {
            cities.forEach(city -> {
                city.setDepartment(department);
                department.getCities().add(city);
            });
            departmentRepository.save(department);
            return DepartmentMapper.convertToDTO(department);
        }
        return null;
    }

    /**
     * Update a department
     * @param code the code of the department
     * @param department the department
     */
    public void update(String code, Department department) {
        Department departmentToUpdate = departmentRepository.findByCode(code);
        if (departmentToUpdate != null) {
            departmentToUpdate.setCode(department.getCode());
            departmentToUpdate.setCities(department.getCities());
            departmentRepository.save(departmentToUpdate);
        }
    }

    /**
     * Delete a department
     * @param code the code of the department
     */
    public void delete(String code) {
        Department department = departmentRepository.findByCode(code);
        if (department != null) {
            departmentRepository.delete(department);
        }
    }
}
