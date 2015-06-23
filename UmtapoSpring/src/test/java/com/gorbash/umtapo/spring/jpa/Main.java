package com.gorbash.umtapo.spring.jpa;

import com.gorbash.umtapo.jpa.db.DBConfig;
import com.gorbash.umtapo.jpa.db.DBSetup;

import javax.persistence.EntityManager;

/**
 * Created by Gorbash on 2015-06-05.
 */
public class Main {
    public static void main(String[] args) {
        DBConfig config = new DBConfig("jdbc:hsqldb:file:testdb");
        config = new DBConfig("jdbc:hsqldb:hsql://localhost/");

        config.addProperty("hibernate.hbm2ddl.auto", "validate");
        DBSetup dbSetup = new DBSetup(config);
        EntityManager em = dbSetup.getEM();
        em.getTransaction().begin();
        em.createQuery("from Person").getResultList().forEach(System.out::println);
        em.createQuery("from Book").getResultList().forEach(System.out::println);
        em.createQuery("from Loan").getResultList().forEach(System.out::println);
        em.getTransaction().rollback();
        em.close();
    }
}
