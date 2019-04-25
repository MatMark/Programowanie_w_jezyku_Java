package Controllers;

import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserConnection {

    static String activeUserName = "";
    static String activeUserSurname = "";

    static final StringConverter<LocalDate> mySQLDate = new StringConverter<LocalDate>() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        @Override public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    private final String DB_URL = "jdbc:mysql://localhost/JDBC";

    //  Database credentials
    private final String USER = "user";
    private final String PASS = "user";

    private Connection conn = null;
    private Statement stmt = null;

    public UserConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }
}
