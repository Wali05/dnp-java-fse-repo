package com.cognizant.spring_learn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class XmlConfig {

    /**
     * Loads the XML configuration file country.xml from the classpath.
     * This context will be used to fetch the bean definitions declared in the XML.
     */
    @Bean
    public ClassPathXmlApplicationContext xmlContext() {
        return new ClassPathXmlApplicationContext("country.xml");
    }

    /**
     * Retrieves the 'country' bean defined in country.xml and registers it in the Spring Boot context.
     * @return Country bean defined in the XML configuration.
     */
    @Bean
    public Country country() {
        return xmlContext().getBean("country", Country.class);
    }
}
