package com.payroll.utils;

import java.util.ResourceBundle;

/**
 * This is a convenience singleton class to get server properties.
 * By default it will use values from 'Server.properties'.
 * It will use overrides from 'Server_Custom.properties' if locally provided.
 *
 * Created by mjohns on 11/17/16.
 * Modified by Dairai on 06/13/17
 */
public class ConfigManager {

    //* mongo *//*
    public static final String MONGO_SERVER_KEY = "mongo.server";
    public static final String MONGO_DB_NAME_KEY = "mongo.dbName";
    public static final String MONGO_DB_PORT_KEY = "mongo.dbPort";
    public static final String MONGO_DB_USER = "mongo.dbUser";
    public static final String MONGO_DB_PASSWORD = "mongo.dbPassword";
    public static final String MYSQL_URL_KEY = "mysql.url";

    private static ConfigManager _instance = null;

    public static ConfigManager getInstance(){
        if (_instance == null){
            _instance = new ConfigManager();
        }
        return _instance;
    }

    ResourceBundle defaultConfig = null;

    private ConfigManager() {
        defaultConfig = ResourceBundle.getBundle("Config");
    }

    public String getSetting(String token) {

        if (defaultConfig != null && defaultConfig.containsKey(token))
            return defaultConfig.getString(token);

        return "";
    }

    public int getSettingAsInt(String token) {

        if (defaultConfig != null && defaultConfig.containsKey(token))
            return Integer.parseInt(defaultConfig.getString(token));

        return Integer.parseInt(defaultConfig.getString(token));
    }
}
