package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


public class DBConnector {

    final Logger logger = Logger.getLogger(DBConnector.class.getCanonicalName());
    public Connection connect() throws SQLException {
        String login = System.getenv("DB_LOGIN");
        String password = System.getenv("DB_PASSWORD");
        String host = System.getenv("DB_HOST");
        if (login == null || password == null || host == null) {
            logger.severe("Env variables DB_LOGIN or DB_PASSWORD are not matched!");
            return null;
        }
        return DriverManager.getConnection(host, login, password);
    }
}
