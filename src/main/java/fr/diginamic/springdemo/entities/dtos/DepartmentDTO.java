package fr.diginamic.springdemo.entities.dtos;

import java.util.Set;

public class DepartmentDTO {
    private int population;
    private Set<CityDTO> cities;

    public DepartmentDTO(int population) {
        this.population = population;
    }

    public DepartmentDTO() {
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Set<CityDTO> getCities() {
        return cities;
    }

    public void setCities(Set<CityDTO> cities) {
        this.cities = cities;
    }
}
