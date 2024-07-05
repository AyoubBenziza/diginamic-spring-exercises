package fr.diginamic.springdemo.entities.dtos;

import fr.diginamic.springdemo.entities.City;

public class CityDTO {
    private String name;
    private int population;
    private String departmentCode;

    public CityDTO(String name, int population, String departmentCode) {
        this.name = name;
        this.population = population;
        this.departmentCode = departmentCode;
    }

    public CityDTO() {
    }

    public CityDTO(City city) {
        this.name = city.getName();
        this.population = city.getPopulation();
        this.departmentCode = city.getDepartment().getCode();
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }
}
