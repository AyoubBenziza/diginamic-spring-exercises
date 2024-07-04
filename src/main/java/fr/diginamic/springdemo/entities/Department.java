package fr.diginamic.springdemo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @NotNull
    @Size(min = 2, max = 5)
    private String code;

    @Column
    private String name;

    @OneToMany(mappedBy = "department",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<City> cities = new HashSet<>();

    public Department(String name) {
        this.name = name;
    }

    public Department() {
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public void removeCity(City city) {
        cities.remove(city);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Department department = (Department) obj;
        return code.equals(department.code);
    }
}
