package fr.diginamic.springdemo.entities.dtos;

import java.util.Set;

/**
 * A DTO for the Department entity
 * @see fr.diginamic.springdemo.entities.Department
 * @see fr.diginamic.springdemo.entities.City
 * @see fr.diginamic.springdemo.entities.dtos.CityDTO
 * @author AyoubBenziza
 */
public class DepartmentDTO {

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
}
