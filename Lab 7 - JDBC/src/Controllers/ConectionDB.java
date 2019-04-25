package Controllers;

import Tables.Client;
import Tables.Service;
import Tables.Term;
import javafx.util.StringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConectionDB {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/JDBC";

    //  Database credentials
    static final String USER = "admin";
    static final String PASS = "admin";

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

//    public static List<Term> selectAllTerms() throws ClassNotFoundException, SQLException {
//        List<Term> terms = new ArrayList<Term>();
//        Connection conn = null;
//        Statement stmt = null;
//        Class.forName("com.mysql.jdbc.Driver");
//        conn = DriverManager.getConnection(DB_URL,USER,PASS);
//
//        //------------QUERY----------------
//        stmt = conn.createStatement();
//        String sql;
//        sql = "SELECT idTerms, date, time, idClients, idServices FROM terms";
//        ResultSet rs = stmt.executeQuery(sql);
//
//        //------------RESULT----------------
//        while(rs.next()){
//            int idTerms  = rs.getInt("idTerms");
//            String date = rs.getString("date");
//            String time = rs.getString("time");
//            int idClients  = rs.getInt("idClients");
//            int idServices  = rs.getInt("idServices");
//
//            Term tmpTerm = new Term(idTerms, date, time, idClients, idServices);
//
//            terms.add(tmpTerm);
//        }
//
//        rs.close();
//        stmt.close();
//        conn.close();
//
//        return terms;
//    }

    public static List<Client> selectAllClients() throws ClassNotFoundException, SQLException {
        List<Client> clients = new ArrayList<Client>();
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT idClients, name, surname FROM clients";
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()){
            int idClients  = rs.getInt("idClients");
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            clients.add(new Client(idClients, name, surname));
        }

        rs.close();
        stmt.close();
        conn.close();

        return clients;
    }

    public static List<Service> selectAllServices() throws ClassNotFoundException, SQLException {
        List<Service> services = new ArrayList<Service>();
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT idServices, price, name FROM services";
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()){
            int idServices  = rs.getInt("idServices");
            int price  = rs.getInt("price");
            String name = rs.getString("name");

            services.add(new Service(idServices, price, name));
        }

        rs.close();
        stmt.close();
        conn.close();

        return services;
    }

//    public static List<Term> selectTermsBetween(String start, String end) throws ClassNotFoundException, SQLException {
//        List<Term> terms = new ArrayList<Term>();
//        Connection conn = null;
//        Statement stmt = null;
//        Class.forName("com.mysql.jdbc.Driver");
//        conn = DriverManager.getConnection(DB_URL,USER,PASS);
//
//        //------------QUERY----------------
//        stmt = conn.createStatement();
//        String sql;
//        sql = "SELECT idTerms, date, time, idClients, idServices FROM terms WHERE (date BETWEEN '"+
//                start+
//                "' AND '"+
//                end+
//                "')";
//        ResultSet rs = stmt.executeQuery(sql);
//
//        //------------RESULT----------------
//        while(rs.next()){
//            int idTerms  = rs.getInt("idTerms");
//            String date = rs.getString("date");
//            String time = rs.getString("time");
//            int idClients  = rs.getInt("idClients");
//            int idServices  = rs.getInt("idServices");
//
//            terms.add(new Term(idTerms, date, time, idClients, idServices));
//        }
//
//        rs.close();
//        stmt.close();
//        conn.close();
//
//        return terms;
//    }

    public static void termData(Term term, int clientId, int serviceId) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql;

        sql = "SELECT name, surname FROM clients WHERE idClients = " + clientId;
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()){
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            term.setClientFullName(name + " " + surname);
        }

        //-----------NEXT QUERY---------------
        sql = "SELECT name FROM services WHERE idServices = " + serviceId;
        rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()){
            String name = rs.getString("name");

            term.setServiceName(name);
        }

        rs.close();
        stmt.close();
        conn.close();

    }

    public static void addServices(Service newService) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "INSERT INTO services (price, name) VALUES ("+
                newService.getPrice() +
                ", '"+
                newService.getName() +
                "')";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
    }

    public static void removeServices(String removedService) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "DELETE FROM services WHERE name = '"+
                removedService +
                "';";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
    }

    public static void editServices(String newPrice, String newName, String oldName) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "UPDATE services SET price = " +
                newPrice +
                ", name = '" +
                newName +
                "' WHERE name = '" +
                oldName +
                "';";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
    }

    public static void addTerms(Term newTerm) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "INSERT INTO terms (date, time) VALUES ('"+
                newTerm.getDate() +
                "', '"+
                newTerm.getTime() +
                "')";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
    }

    public static void removeTerms(int idTerm) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "DELETE FROM terms WHERE idTerms = "+
                idTerm +
                ";";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
    }
}
