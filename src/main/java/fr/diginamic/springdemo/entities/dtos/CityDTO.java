package fr.diginamic.springdemo.entities.dtos;

public class CityDTO {
    private String name;
    private int population;

    public CityDTO(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public CityDTO() {
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }
}
