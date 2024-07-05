package fr.diginamic.springdemo.utils;

import fr.diginamic.springdemo.entities.City;
import fr.diginamic.springdemo.entities.Department;
import fr.diginamic.springdemo.services.CityService;
import fr.diginamic.springdemo.services.DepartmentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class for importing data from files
 */
@Component
public class ImportUtils {

    /**
     * The CityService
     */
    @Autowired
    private CityService cityService;

    /**
     * The DepartmentService
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * Import the most populated cities from a CSV file
     * @param path the path to the CSV file
     * @param headers the headers of the CSV file
     * @param limit the maximum number of cities to import
     */
    public void mostPopulatedCitiesCSV(String path, String[] headers, int limit) {
        System.out.println("Importing cities from " + path);
        try {
            Reader in = new FileReader(path);
            Iterable<CSVRecord> records = CSVFormat.Builder
                    .create(CSVFormat.EXCEL)
                    .setDelimiter(';')
                    .setHeader(headers)
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(in);

            List<City> cities = new ArrayList<>();
            AtomicInteger remaining = new AtomicInteger(limit);
            records.forEach(record -> {
                if (remaining.getAndDecrement() > 0){
                    String departmentCode = record.get("codeDepartment").trim();
                    String cityName = record.get("nameCommune").trim();
                    int population = Integer.parseInt(record.get("populationTotale").replaceAll(" ", ""));

                    City city = new City();
                    city.setName(cityName);
                    city.setPopulation(population);
                    // Temporarily store the department code in the city object for later processing
                    city.setDepartment(new Department(departmentCode)); // Assuming Department has a constructor that accepts a code

                    cities.add(city);
                }
            });

            // Sort cities by population in descending order
            cities.sort(Comparator.comparingInt(City::getPopulation).reversed());

            cities.forEach(city -> {
                String departmentCode = city.getDepartment().getCode();
                Department department = departmentService.getDepartment(departmentCode);
                if (department == null) {
                    department = new Department();
                    department.setCode(departmentCode);
                    departmentService.insertDepartments(department);
                }

                city.setDepartment(department);
                cityService.insertCities(city);
            });
        } catch (IOException e) {
            throw new RuntimeException("Error while importing cities from CSV", e);
        }
    }
}
