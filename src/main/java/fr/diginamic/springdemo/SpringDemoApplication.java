package fr.diginamic.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main class for the SpringDemo application
 */
@SpringBootApplication
public class SpringDemoApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Main method
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }

}
