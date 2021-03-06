package Controllers;

import Tables.Service;
import Tables.Term;
import javafx.util.StringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminConnection {

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
    private final String USER = "admin";
    private final String PASS = "admin";

    private Connection conn = null;
    private Statement stmt = null;

    public AdminConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    public List<Term> selectReservedTerms() throws SQLException {
        List<Term> terms = new ArrayList<Term>();
        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql = "SELECT terms.idTerms, terms.date, terms.time, terms.idClients, terms.idServices, services.name, clients.name, clients.surname \n" +
                    "FROM terms, services, clients\n" +
                    "WHERE terms.idServices = services.idServices AND terms.idClients = clients.idClients;";
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()) {
            int idTerms = rs.getInt("terms.idTerms");
            String date = rs.getString("terms.date");
            String time = rs.getString("terms.time");
            int idClients = rs.getInt("terms.idClients");
            int idServices = rs.getInt("terms.idServices");
            String serviceName = rs.getString("services.name");
            String clientName = rs.getString("clients.name");
            String clientSurname = rs.getString("clients.surname");

            Term tmpTerm = new Term(idTerms, date, time, idClients, idServices, clientName + " " + clientSurname, serviceName);
            terms.add(tmpTerm);
        }
        rs.close();
        stmt.close();

        return terms;
    }

    public List<Term> selectFreeTerms() throws SQLException {
        List<Term> terms = new ArrayList<Term>();
        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql = "SELECT terms.idTerms, terms.date, terms.time\n" +
                "FROM terms\n" +
                "WHERE terms.idServices is NULL AND terms.idClients is NULL;\n";
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()) {
            int idTerms = rs.getInt("terms.idTerms");
            String date = rs.getString("terms.date");
            String time = rs.getString("terms.time");

            Term tmpTerm = new Term(idTerms, date, time);
            terms.add(tmpTerm);
        }
        rs.close();
        stmt.close();

        return terms;
    }

    public List<Service> selectAllServices() throws SQLException {
        List<Service> services = new ArrayList<Service>();

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

        return services;
    }

    public List<Term> selectTermsBetween(String start, String end) throws SQLException {
        List<Term> terms = new ArrayList<Term>();

        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT terms.idTerms, terms.date, terms.time, services.name, clients.name, clients.surname " +
                "FROM terms, services, clients " +
                "WHERE (date BETWEEN '"+
                start+
                "' AND '"+
                end+
                "') AND (terms.idServices = services.idServices AND terms.idClients = clients.idClients)" +
                "GROUP BY terms.idTerms";
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()){
            int idTerms = rs.getInt("terms.idTerms");
            String date = rs.getString("terms.date");
            String time = rs.getString("terms.time");
            String serviceName = rs.getString("services.name");
            String clientName = rs.getString("clients.name");
            String clientSurname = rs.getString("clients.surname");

            Term tmpTerm = new Term(idTerms, date, time, clientName + " " + clientSurname, serviceName);
            terms.add(tmpTerm);
        }

        rs.close();
        stmt.close();

        return terms;
    }

    public int selectSum(String start, String end) throws SQLException {
        int suma = 0;

        //------------QUERY----------------
        stmt = conn.createStatement();
        String sql = " SELECT SUM(services.price) AS Suma " +
                "FROM terms, services, clients " +
                "WHERE (date BETWEEN '"+
                start+
                "' AND '"+
                end+
                "') AND (terms.idServices = services.idServices AND terms.idClients = clients.idClients)" +
                "GROUP BY terms.idTerms";
        ResultSet rs = stmt.executeQuery(sql);

        //------------RESULT----------------
        while(rs.next()){
            suma = rs.getInt("Suma");
        }

        rs.close();
        stmt.close();

        return suma;
    }

    public void addServices(Service newService) throws SQLException {

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
    }

    public void removeServices(String removedService) throws SQLException {

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "DELETE FROM services WHERE name = '"+
                removedService +
                "';";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
    }

    public  void editServices(String newPrice, String newName, String oldName) throws SQLException {

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
    }

    public void addTerms(Term newTerm) throws SQLException {

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
    }

    public void removeTerms(int idTerm) throws SQLException {

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "DELETE FROM terms WHERE idTerms = "+
                idTerm +
                ";";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
    }

    public  void editTerms(String date, String time, String clientName, String clientSurname, String serviceName, int termId) throws SQLException {

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "UPDATE `terms`\n" +
                "SET\n" +
                "date = '" + date + "',\n" +
                "time = '" + time + "',\n" +
                "idClients = (SELECT idClients FROM clients WHERE (clients.name = '"+clientName+"' AND clients.surname = '"+clientSurname+"')),\n" +
                "idServices = (SELECT idServices FROM services WHERE services.name = '"+serviceName+"')\n" +
                "WHERE idTerms = "+termId+";";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
    }
}
