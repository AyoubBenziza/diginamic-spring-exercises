package fr.diginamic.springdemo.entities.dtos;

/**
 * ApiDepartmentDTO is a class used to represent the Department entity in the API
 * It is used to serialize the Department entity into a JSON object
 * @author AyoubBenziza
 */
public class ApiDepartmentDTO {
    private String nom;
    private String code;

    public ApiDepartmentDTO() {
    }

    public ApiDepartmentDTO(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
