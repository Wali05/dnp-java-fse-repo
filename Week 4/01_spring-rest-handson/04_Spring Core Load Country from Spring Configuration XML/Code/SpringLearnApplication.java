package com.cognizant.spring_learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - main() method");

        // Load Spring Boot application context
        ApplicationContext context = SpringApplication.run(SpringLearnApplication.class, args);

        displayCountry(context);

        LOGGER.info("END - main() method");
    }

    public static void displayCountry(ApplicationContext context) {
        LOGGER.info("START - displayCountry() method");

        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country);

        LOGGER.info("END - displayCountry() method");
    }
}
