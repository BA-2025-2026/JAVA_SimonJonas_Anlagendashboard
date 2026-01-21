package net.ictcampus.semodul.anlagendashboard.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Static variables and methods used for config info: Class will be available, as soon as it's properties
 * (DB-Access-Information) will be requested for the first time. After that, these variables will be
 * available until the application ends.
 *
 * To enable db connection add a file "config.properties" to your project folder.
 * File needs to follow this structure:
 *
 * db.url=jdbc:mysql://localhost:3306/Casino
 * db.user=[YOUR USERNAME]
 * db.password=[YOUR PASSWORD]
 */
public class DbProperties {
    private static final Properties properties = new Properties();

    // Read properties
    // Static block gets executed on first load of class
    static {
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading database properties from property file.", e);
        }
    }

    // Access property data
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
