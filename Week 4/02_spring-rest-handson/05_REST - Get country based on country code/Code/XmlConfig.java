package com.cognizant.spring_learn;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class XmlConfig {

    /**
     * Loads the Spring XML configuration file.
     * This makes the beans defined in country.xml accessible in Spring Boot context.
     */
    @Bean
    public ClassPathXmlApplicationContext xmlContext() {
        return new ClassPathXmlApplicationContext("country.xml");
    }

    /**
     * Loads the 'country' bean defined in country.xml.
     */
    @Bean
    public Country country() {
        return xmlContext().getBean("country", Country.class);
    }

    /**
     * Loads the 'countryList' bean defined in country.xml.
     * Used for returning the list of countries in REST endpoints.
     */
    @Bean
    public List<Country> countryList() {
        return xmlContext().getBean("countryList", List.class);
    }
}
