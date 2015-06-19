package com.gorbash.umtapo.jpa.db;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GorbasH on 2014-09-20.
 */
public class DBSetup {


    public static final String PERSISTENCE_UNIT_NAME = "Umtapo";
    private static final Logger logger = Logger.getLogger(DBSetup.class);
    private EntityManagerFactory emf;
    private EntityManager em;


    public DBSetup(DBConfig config) {
        setupDB(config);
    }

    public synchronized EntityManager getEM() {
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }

    private void setupDB(DBConfig config) {
        logger.info(String.format("Setting database connection with following config: %s", config));
        createFactory(config);
    }

    private synchronized EntityManagerFactory createFactory(DBConfig config) {

        if (emf == null) {
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("hibernate.connection.driver_class", config.getDriver());
            properties.put("hibernate.connection.url", config.getUrl());
            properties.put("hibernate.connection.username", config.getUser());
            properties.put("hibernate.connection.password", config.getPassword());
            properties.putAll(config.getAdditionalProperties());
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        }

        return emf;
    }
}
