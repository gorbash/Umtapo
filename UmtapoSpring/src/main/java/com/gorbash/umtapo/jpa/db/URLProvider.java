package com.gorbash.umtapo.jpa.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Gorbash on 2015-06-26.
 */
@Configuration
public class URLProvider {

    public static final String TEST_URL = "jdbc:hsqldb:file:testdb";
    public static final String PRODUCTION_URL = "jdbc:hsqldb:hsql://localhost/";

    @Bean
    @Profile("test")
    String getTestUrl() {
        return TEST_URL;
    }

    @Bean
    @Profile("prod")
    String getProductionUrl() {
        return PRODUCTION_URL;
    }
}
