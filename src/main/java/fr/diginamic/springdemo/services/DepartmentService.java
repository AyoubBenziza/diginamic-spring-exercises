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
import java.util.HashSet;
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

    public DepartmentDTO extractDepartment(int id) {
        return convertToDTO(departmentRepository.findById(id).orElse(null));
    }

    public Department getDepartment(int id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public DepartmentDTO extractDepartmentByName(String name) {
        return convertToDTO(departmentRepository.findByName(name));
    }

    public Set<DepartmentDTO> insertDepartments(Department... departmentsToAdd) {
        departmentRepository.saveAll(Arrays.asList(departmentsToAdd));
        return Arrays.stream(departmentsToAdd)
                .map(this::convertToDTO)
                .collect(Collectors.toSet());
    }

    public Set<CityDTO> findTopNCitiesInDepartment(int departmentId, int n) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            return department.getCities().stream()
                    .sorted((c1, c2) -> c2.getPopulation() - c1.getPopulation())
                    .limit(n)
                    .map(city -> new CityDTO(city.getName(), city.getPopulation()))
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public Set<CityDTO> findCitiesWithPopulationBetween(int departmentId, int minPopulation, int maxPopulation) {
        DepartmentDTO departmentDTO = extractDepartment(departmentId);
        if (departmentDTO != null) {
            return departmentDTO.getCities().stream()
                    .filter(cityDTO -> cityDTO.getPopulation() >= minPopulation && cityDTO.getPopulation() <= maxPopulation)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Transactional
    public DepartmentDTO addCities(int id, Set<City> cities) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            for (City city : cities) {
                city.setDepartment(department); // Assuming there's a method to set the department in City
                department.addCity(city);
            }
            departmentRepository.save(department); // This ensures changes are persisted
            return convertToDTO(department);
        }
        return null;
    }

    public void update(int id, Department department) {
        Department departmentToUpdate = departmentRepository.findById(id).orElse(null);
        if (departmentToUpdate != null) {
            departmentToUpdate.setName(department.getName());
            departmentRepository.save(departmentToUpdate);
        }
    }

    public void delete(int id) {
        departmentRepository.findById(id).ifPresent(department -> departmentRepository.delete(department));
    }

    public DepartmentDTO convertToDTO(Department department) {
        if (department != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO(department.getName());
            Set<CityDTO> cityDTOs = department.getCities().stream()
                    .map(city -> new CityDTO(city.getName(), city.getPopulation()))
                    .collect(Collectors.toSet());
            departmentDTO.setCities(cityDTOs);
            return departmentDTO;
        }
        return null;
    }
}
