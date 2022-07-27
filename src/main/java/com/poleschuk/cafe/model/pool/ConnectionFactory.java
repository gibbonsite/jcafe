package com.poleschuk.cafe.model.pool;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.ConnectionPoolException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConnectionFactory class loads database properties and creates connections.
 */
class ConnectionFactory {
	private static final Logger logger = LogManager.getLogger();
	private static final String DB_URL = "db.url";
    private static final String DB_DRIVER = "db.driver";
    private static final String FILE_NAME = "sqldata/database.properties";
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static String fileProperties;

    static {
        try {
            ClassLoader loader = ConnectionFactory.class.getClassLoader();
            URL resource = loader.getResource(FILE_NAME);
            if (resource == null) {
                logger.log(Level.ERROR, "Resource is null! " + FILE_NAME);
                throw new IllegalArgumentException();
            }
            fileProperties = resource.getFile();
            properties.load(new FileReader(fileProperties));
            String driverName = (String) properties.get(DB_DRIVER);
            Class.forName(driverName);
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.FATAL, "Database driver class can not be registered");
            throw new RuntimeException("Database driver class can not be registered." + e.getMessage());
        }
        DATABASE_URL = (String) properties.get(DB_URL);
    }
    
    private ConnectionFactory() {}

    static Connection createConnection() throws ConnectionPoolException {
        try {
            return DriverManager.getConnection(DATABASE_URL, properties);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Connection is not received: " + e.getMessage());
        }
    }
}