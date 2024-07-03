package fr.diginamic.springdemo.repositories;

import fr.diginamic.springdemo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);
}
