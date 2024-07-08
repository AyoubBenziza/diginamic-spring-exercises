package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.entities.dtos.ApiDepartmentDTO;
import fr.diginamic.springdemo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service for the Department entity
 * This class is used to interact with the DepartmentRepository
 */
@Service
public class DepartmentService {

    private final String apiUrl = "https://geo.api.gouv.fr/departements";

    /**
     * The RestTemplate
     */
    @Autowired
    private RestTemplate restTemplate;

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

    /**
     * Add the name of a department
     * @param departmentToUpdate the department to update
     * @see ApiDepartmentDTO
     * @see Department
     */
    public void addName(Department departmentToUpdate) {
        if (departmentToUpdate != null) {
            String code = departmentToUpdate.getCode();
            String url = apiUrl + "?code=" + code + "&fields=nom,code";

            ResponseEntity<List<ApiDepartmentDTO>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            List<ApiDepartmentDTO> apiDepartmentDTOList = responseEntity.getBody();

            if (apiDepartmentDTOList != null && !apiDepartmentDTOList.isEmpty()) {
                ApiDepartmentDTO apiDepartmentDTO = apiDepartmentDTOList.getFirst(); // Assuming the first item is the desired one
                departmentToUpdate.setName(apiDepartmentDTO.getNom());
            }
        }
    }
}
