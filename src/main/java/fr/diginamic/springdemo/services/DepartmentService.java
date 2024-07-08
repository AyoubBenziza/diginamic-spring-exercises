package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
