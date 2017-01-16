package com.epamtraining.connection;

import java.util.ResourceBundle;

/**
 * @author Sergey Bondarenko
 */
public class Configuration {
    private static Configuration configuration = new Configuration();
    public String DB_USER_NAME ;
    public String DB_PASSWORD ;
    public String DB_URL;
    public String DB_DRIVER;
    public Integer DB_MAX_CONNECTIONS;

    public Configuration(){
        init();
    }
    public static Configuration getInstance() {
            return configuration;
    }
    private void init(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        DB_USER_NAME = resourceBundle.getString("user");
        DB_PASSWORD = resourceBundle.getString("password");
        DB_URL = resourceBundle.getString("url");
        DB_DRIVER = resourceBundle.getString("driver");
        DB_MAX_CONNECTIONS = Integer.parseInt(resourceBundle.getString("maxConnections"));
    }
}