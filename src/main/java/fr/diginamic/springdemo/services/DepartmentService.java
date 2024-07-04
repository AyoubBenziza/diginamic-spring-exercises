package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.CityDTO;
import fr.diginamic.springdemo.entities.dtos.DepartmentDTO;
import fr.diginamic.springdemo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Set<DepartmentDTO> extractDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet());
    }

    public DepartmentDTO extractDepartment(String code) {
        return convertToDTO(departmentRepository.findByCode(code));
    }

    public Department getDepartment(String code) {
        return departmentRepository.findByCode(code);
    }

    public DepartmentDTO extractDepartmentByName(String name) {
        return convertToDTO(departmentRepository.findByName(name));
    }

    public Set<DepartmentDTO> extractDepartmentsByName(String name) {
        return departmentRepository.findByNameStartingWith(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet());
    }

    public Set<CityDTO> extractCities(String id) {
        return departmentRepository.findCities(id).stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                .collect(Collectors.toSet());
    }

    public void insertDepartments(Department... departmentsToAdd) {
        departmentRepository.saveAll(Arrays.asList(departmentsToAdd));
    }

    public Set<CityDTO> findTopNCitiesInDepartment(String departmentId, int n) {
        Set<City> cities = departmentRepository.findTopNCities(departmentId, n);
        return cities.stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                .collect(Collectors.toSet());
    }

    public Set<CityDTO> findCitiesWithPopulationBetween(String departmentId, int minPopulation, int maxPopulation) {
        Set<City> cities = departmentRepository.findCitiesByPopulationRange(departmentId, minPopulation, maxPopulation);
        return cities.stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                .collect(Collectors.toSet());
    }

    @Transactional
    public DepartmentDTO addCities(String code, Set<City> cities) {
        Department department = departmentRepository.findByCode(code);
        if (department != null) {
            cities.forEach(city -> {
                city.setDepartment(department);
                department.getCities().add(city);
            });
            departmentRepository.save(department);
            return convertToDTO(department);
        }
        return null;
    }

    public void update(String code, Department department) {
        Department departmentToUpdate = departmentRepository.findByCode(code);
        if (departmentToUpdate != null) {
            departmentToUpdate.setName(department.getName());
            departmentRepository.save(departmentToUpdate);
        }
    }

    public void delete(String code) {
        Department department = departmentRepository.findByCode(code);
        if (department != null) {
            departmentRepository.delete(department);
        }
    }

    public DepartmentDTO convertToDTO(Department department) {
        if (department != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO(department.getName());
            Set<CityDTO> cityDTOs = department.getCities().stream()
                    .map(city -> new CityDTO(city.getName(), city.getPopulation(), city.getDepartment().getCode()))
                    .collect(Collectors.toSet());
            departmentDTO.setCities(cityDTOs);
            return departmentDTO;
        }
        return null;
    }
}
