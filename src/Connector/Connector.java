package Connector;



import java.sql.*;


public class Connector {

    String dbUrl = "jdbc:mysql://localhost:3306/db_project";
    String username = "root";
    String password = "";

    protected Connection conn;
    protected Statement statement;

    public Connector() {

        getConnection();


    }

      public void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, username, password);
            statement = conn.createStatement();
            System.out.println("Connected to database");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            
        }
    }



}
