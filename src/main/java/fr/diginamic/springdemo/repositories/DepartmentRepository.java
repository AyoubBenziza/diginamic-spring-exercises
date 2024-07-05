package fr.diginamic.springdemo.repositories;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByCode(String code);
    Department findByName(String name);
    Set<Department> findByNameStartingWith(String name);

    @Query("SELECT c FROM City c WHERE c.department.code = :departmentId")
    Set<City> findCities(@Param("departmentId") String departmentId);

    @Query("SELECT c FROM City c WHERE c.department.code = :departmentId AND c.population BETWEEN :minPopulation AND :maxPopulation")
    Set<City> findCitiesByPopulationRange(@Param("departmentId") String departmentId, @Param("minPopulation") int minPopulation, @Param("maxPopulation") int maxPopulation);

    @Query("SELECT c FROM City c WHERE c.department.code = :departmentId ORDER BY c.population DESC LIMIT :n")
    Set<City> findTopNCities(@Param("departmentId") String departmentId, @Param("n") int n);
}
