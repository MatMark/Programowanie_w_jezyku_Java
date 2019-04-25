package Controllers;

import Tables.Service;
import Tables.Term;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserConnection {

    static String activeUserName = "";
    static String activeUserSurname = "";

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

    public void bookTerms(String date, String time, String clientName, String clientSurname, String serviceName, int termId) throws SQLException {

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

    public boolean findUser(String name, String surname) throws SQLException {
        boolean result = false;
        stmt = conn.createStatement();

        String sql = "SELECT idClients FROM clients WHERE (clients.name = '"+name+"' AND clients.surname = '"+surname+"')";

        System.out.println(sql);

        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            int idClients  = rs.getInt("idClients");

            if(idClients != 0) result = true;
        }

        System.out.println(result);
        rs.close();
        stmt.close();
        return result;
    }

    public void addUser(String name, String surname) throws SQLException {

        //------------QUERY----------------
        stmt = conn.createStatement();

        String sql = "INSERT INTO clients\n" +
                "(name,\n" +
                "surname)\n" +
                "VALUES\n" +
                "('"+name+"',\n" +
                "'"+surname+"');";

        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
    }

}
