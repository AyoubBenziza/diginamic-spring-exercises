package fr.diginamic.springdemo.entities.dtos;

import java.util.Set;

public class DepartmentDTO {
    private String name;
    private Set<CityDTO> cities;

    public DepartmentDTO(String name) {
        this.name = name;
    }

    public DepartmentDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CityDTO> getCities() {
        return cities;
    }

    public void setCities(Set<CityDTO> cities) {
        this.cities = cities;
    }
}
