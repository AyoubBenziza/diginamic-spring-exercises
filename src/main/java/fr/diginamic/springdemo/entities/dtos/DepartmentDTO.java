package fr.diginamic.springdemo.entities.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

/**
 * A DTO for the Department entity
 * @see fr.diginamic.springdemo.entities.Department
 * @see fr.diginamic.springdemo.entities.City
 * @see fr.diginamic.springdemo.entities.dtos.CityDTO
 * @author AyoubBenziza
 */
@JsonPropertyOrder({"name", "population", "cities"})
public class DepartmentDTO {
    /**
     * The department name
     */
    private String name;

    /**
     * The department population
     */
    private int population;

    /**
     * The department cities
     */
    private Set<CityDTO> cities;

    /**
     * Constructor
     * @param population the department population
     */
    public DepartmentDTO(int population) {
        this.population = population;
    }

    /**
     * Default constructor
     */
    public DepartmentDTO() {
    }

    /**
     * Get the department name
     * @return a string
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Set the department name
     * @param name the department name
     */
    @JsonAlias("nom")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the department population (sum of all cities' populations)
     * @return an integer
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Set the department population
     * @param population the department population
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * Get the department cities
     * @return a set of CityDTO
     */
    public Set<CityDTO> getCities() {
        return cities;
    }

    /**
     * Set the department cities
     * @param cities a set of CityDTO
     */
    public void setCities(Set<CityDTO> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", cities=" + cities +
                '}';
    }
}
