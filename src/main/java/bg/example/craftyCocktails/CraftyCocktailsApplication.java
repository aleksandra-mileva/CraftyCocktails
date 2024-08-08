package bg.example.craftyCocktails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CraftyCocktailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CraftyCocktailsApplication.class, args);
    }

}
