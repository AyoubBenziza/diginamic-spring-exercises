package fr.diginamic.springdemo.entities.dtos;

/**
 * ApiDepartmentDTO is a class used to represent the Department entity in the API
 * It is used to serialize the Department entity into a JSON object
 *
 * @author AyoubBenziza
 */
public record ApiDepartmentDTO(String nom, String code) {

    /**
     * Get the name of the department
     *
     * @return the name of the department
     */
    @Override
    public String nom() {
        return nom;
    }

    /**
     * Get the code of the department
     *
     * @return the code of the department
     */
    @Override
    public String code() {
        return code;
    }
}
