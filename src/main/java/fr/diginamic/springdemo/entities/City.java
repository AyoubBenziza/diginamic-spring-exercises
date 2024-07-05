package fr.diginamic.springdemo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    @Size(min = 2, message = "The name must be at least 2 characters long")
    private String name;

    @Column
    @NotNull
    @Min(value = 1, message = "The population must be a positive number")
    private int population;

    @ManyToOne
    @JoinColumn(name = "department_code")
    private Department department;

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public City() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof City city) {
            return city.getId() == this.getId();
        }
        return false;
    }
}
