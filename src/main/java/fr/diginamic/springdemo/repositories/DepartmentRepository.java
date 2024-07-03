package fr.diginamic.springdemo.repositories;

import fr.diginamic.springdemo.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByName(String name);
}
