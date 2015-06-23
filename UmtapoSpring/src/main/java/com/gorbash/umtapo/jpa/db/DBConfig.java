package com.gorbash.umtapo.jpa.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GorbasH on 2014-09-20.
 */
public class DBConfig {

    public static final String DEFAULT_URL = "jdbc:hsqldb:file:testdb";
    public static final String DEFAULT_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    public static final String DEFAULT_USER = "SA";
    public static final String DEFAULT_PASSWORD = "";


    private String url;
    private String driver;
    private String user;
    private String password;
    private Map<String, String> additionalProperties;


    public DBConfig() {
        this(DEFAULT_URL);
    }

    public DBConfig(String url) {
        this(DEFAULT_USER, DEFAULT_PASSWORD, DEFAULT_DRIVER, url);
    }

    public DBConfig(String user, String password, String driver, String url) {
        this.password = password;
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.additionalProperties = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DBConfig{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public void addProperty(String name, String value) {
        additionalProperties.put(name, value);
    }

    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }
}
