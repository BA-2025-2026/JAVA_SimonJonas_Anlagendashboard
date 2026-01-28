package net.ictcampus.semodul.anlagendashboard.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Static variables and methods used for config info: Class will be available, as soon as it's properties
 * (DB-Access-Information) will be requested for the first time. After that, these variables will be
 * available until the application ends.
 *
 * To enable db connection add a file "config.properties" to your project folder.
 * File needs to follow this structure:
 *
 * db.url=jdbc:mysql://localhost:3306/java_simonjonas_anlagendashboard_db
 * db.user=[YOUR USERNAME]
 * db.password=[YOUR PASSWORD]
 */
public class DbProperties {
    private static Properties props;

    // Read properties
    public static Properties getProperties() {
        if (props == null) {
            props = new Properties();
            try (InputStream input = DbProperties.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    // No property file found
                    throw new RuntimeException("Could not find file config.properties in resources folder. " +
                            "File config.properties needs to be created in order to load username and password for " +
                            "the database. Did you forget to create it?");
                }
                props.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Error while reading property file with database properties.", e);
            }
        }
        return props;
    }
}
