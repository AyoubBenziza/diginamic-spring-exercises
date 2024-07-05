package fr.diginamic.springdemo.repositories;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository for the Department entity
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    /**
     * Find a department by its code
     * @param code the code of the department
     * @return the department
     */
    Department findByCode(String code);

    /**
     * Find a department by its name
     * @param name the name of the department
     * @return the department
     */
    Department findByName(String name);

    /**
     * Find departments by their name starting with a given string
     * @param name the string to search for
     * @return the departments
     */
    Set<Department> findByNameStartingWith(String name);

    /**
     * Find cities in a department
     * @param departmentId the id of the department
     * @return the cities
     */
    @Query("SELECT c FROM City c WHERE c.department.code = :departmentId")
    Set<City> findCities(@Param("departmentId") String departmentId);

    /**
     * Find cities in a department by their population
     * @param departmentId the id of the department
     * @param minPopulation the minimum population
     * @param maxPopulation the maximum population
     * @return the cities
     */
    @Query("SELECT c FROM City c WHERE c.department.code = :departmentId AND c.population BETWEEN :minPopulation AND :maxPopulation")
    Set<City> findCitiesByPopulationRange(@Param("departmentId") String departmentId, @Param("minPopulation") int minPopulation, @Param("maxPopulation") int maxPopulation);

    /**
     * Find the top N cities in a department by their population
     * @param departmentId the id of the department
     * @param n the number of cities to return
     * @return the cities
     */
    @Query("SELECT c FROM City c WHERE c.department.code = :departmentId ORDER BY c.population DESC LIMIT :n")
    Set<City> findTopNCities(@Param("departmentId") String departmentId, @Param("n") int n);
}
