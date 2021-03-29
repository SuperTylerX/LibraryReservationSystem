package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection conn;

    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Project3Persistence/src/main/resources/db.sqlite");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return conn;

    }
}
