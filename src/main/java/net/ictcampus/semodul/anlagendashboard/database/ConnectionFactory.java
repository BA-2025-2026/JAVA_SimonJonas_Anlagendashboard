package net.ictcampus.semodul.anlagendashboard.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gets DB config info from config file. Creates a connection to the database.
 * Singleton Design: Object exists only once to prevent from having multiple connections running.
 */
public class ConnectionFactory {
    String dbUrl;
    String dbUser;
    String dbPassword;

    private static ConnectionFactory connectionFactory;

    private ConnectionFactory(String url, String user, String pwd) {
        this.dbUrl = url;
        this.dbUser = user;
        this.dbPassword = pwd;
    }

    /**
     * Give access to the ONE AND ONLY Connection Factory instance that is allowed to exist.
     * If it's not there yet, create it.
     * @return ConnectionFactory instance
     */
    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            // Create connection factory with config info loaded from property file.
            connectionFactory = new ConnectionFactory(DbProperties.get("db.url"), DbProperties.get("db.user"), DbProperties.get("db.password"));
        }
        return connectionFactory;
    }

    /**
     * Create connection to the db.
     * @return connection
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(this.getDbUrl(), this.getDbUser(), this.getDbPassword());
        return conn;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return this.dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
