package model;

import Connector.Connector;
import controller.Controller;
import java.sql.*;

public class Model extends Connector {
    private String username;
    private String password;

    public Model(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Model(Controller controller) {
        // If you need to use the controller instance, store it in a field
    }

    public void registerUser(String username, String password, String confirmPassword) {
        try {
            if (!password.equals(confirmPassword)) {
                System.out.println("Password and confirm password are not the same");
                return;
            }

            String query = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";

            statement.executeUpdate(query);

            System.out.println("Register Success");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean loginUser(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Success");
                return true;
            } else {
                // Check in admin table
                query = "SELECT * FROM admin WHERE username = ? AND password = ?";
                statement = conn.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Admin Login Success");
                    return true;
                }
                System.out.println("Login Failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false; // Mengembalikan false jika terjadi kesalahan
        }
    }


    public void registerAdmin (String username, String password, String confirmPassword) {
        try {
            if (!password.equals(confirmPassword)) {
                System.out.println("Password and confirm password are not the same");
                return;
            }

            String query = "INSERT INTO admin (username, password) VALUES ('" + username + "', '" + password + "')";

            statement.executeUpdate(query);

            System.out.println("Register Success");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean loginAdmin(String username, String password) {
        try {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
        
            if (resultSet.next()) {
                System.out.println("Login Success");
                return true; 
            } else {
                System.out.println("Login Failed");
                return false; 
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false; // Mengembalikan false jika terjadi kesalahan
        }
    }

    public boolean isAdmin (String username) {  
        try {
            String query = "SELECT * FROM admin WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
        
            if (resultSet.next()) {
                return true; 
            } else {
                return false; 
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false; 
        }
    }
    
    
}
