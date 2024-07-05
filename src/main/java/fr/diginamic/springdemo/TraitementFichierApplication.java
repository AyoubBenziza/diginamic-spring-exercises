package fr.diginamic.springdemo;

import fr.diginamic.springdemo.utils.ImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class TraitementFichierApplication implements CommandLineRunner {

    private static final String CITIES_CSV_PATH = Objects.requireNonNull(TraitementFichierApplication.class.getClassLoader().getResource("recensement.csv")).getPath();

    private static final String[] CITIES_CSV_HEADERS = {"codeRegion", "nameRegion", "codeDepartment", "codeArrondissement", "codeCanton", "codeCommune", "nameCommune", "populationMunicipale", "populationComptéeAPart", "populationTotale"};

    @Autowired
    private ImportUtils importUtils;

    public static void main(String[] args) {
        System.out.println("Application started");
        SpringApplication app = new SpringApplication(TraitementFichierApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        importUtils.mostPopulatedCitiesCSV(CITIES_CSV_PATH, CITIES_CSV_HEADERS, 1000);
    }
}
