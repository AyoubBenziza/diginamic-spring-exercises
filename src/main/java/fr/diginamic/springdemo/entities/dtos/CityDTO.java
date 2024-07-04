package fr.diginamic.springdemo.entities.dtos;

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
