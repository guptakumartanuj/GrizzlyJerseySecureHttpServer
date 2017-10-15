package com.secure.server.main;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by harsh on 18/4/17.
 */
public class Config {

    private final String port;

    private static final Log LOG = LogFactory.getLog(Config.class);

    private static String defaultConfigFileName = "/config/config.properties";

    private static volatile Config INSTANCE;

    private Config() {

        Properties configFile = new Properties();
        try {
            configFile.load(this.getClass().getResourceAsStream(defaultConfigFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.port = (System.getenv("PORT") != null && !System.getenv("PORT").isEmpty())
                ? System.getenv("PORT")
                : configFile.getProperty("PORT");
        if (this.port == null) throw new IllegalArgumentException("Please set Environment variable: PORT");
        LOG.info("Port Number :" + port);

    }

    public static Config getConfig() {

        if (INSTANCE == null) {
            synchronized (Config.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Config();
                }
            }
        }
        return INSTANCE;

    }

    public String getPort() {
        return this.port;
    }
}