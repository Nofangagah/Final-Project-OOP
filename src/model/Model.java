package model;

import Connector.Connector;
import controller.Controller;

import java.awt.Image;
import java.awt.MediaTracker;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

public class Model extends Connector {
    private Controller controller;
    private String username;
    private String password;
    private String movie_name, title, genre, sinopsis, image, duration, rating;
    private String location;
    private int seatingCapacity;

    public Model(String username, String password, String movie_name, String title, String genre, String sinopsis, String image, String rating, String duration, String location, int seatingCapacity) {
        this.username = username;
        this.password = password;
        this.movie_name = movie_name;
        this.title = title;
        this.genre = genre;
        this.sinopsis = sinopsis;
        this.image = image;
        this.rating = rating;
        this.duration = duration;
        this.location = location;
        this.seatingCapacity = seatingCapacity;
    }

    public Model(Controller controller) {
        super(); 
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
            String query = "SELECT id FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                System.out.println("User ID " + userId + " logged in."); // Print userID saat berhasil login
                System.out.println("Login Success");
                return true;
            } else {
                query = "SELECT id FROM admin WHERE username = ? AND password = ?";
                statement = conn.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    System.out.println("Admin ID " + userId + " logged in."); // Print adminID saat berhasil login
                    System.out.println("Admin Login Success");
                    return true;
                }
                System.out.println("Login Failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    

    public void registerAdmin(String username, String password, String confirmPassword) {
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
            return false;
        }
    }

    public boolean isAdmin(String username) {
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

    public void insertMovie(String movie_name, String title, String genre, String sinopsis, String image, String rating, String duration) {
        try {
            String query = "INSERT INTO movie (movie_name, title, genre, rating, duration, sinopsis, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, movie_name);
            statement.setString(2, title);
            statement.setString(3, genre);
            statement.setString(4, rating);
            statement.setString(5, duration);
            statement.setString(6, sinopsis);
            statement.setString(7, image);
            statement.executeUpdate();
            System.out.println("Insert Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String[][] showMovies() {
        String data[][] = new String[100][8];
        try {
            int i = 0;
            String query = "SELECT * FROM movie";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("id");
                data[i][1] = resultSet.getString("movie_name");
                data[i][2] = resultSet.getString("title");
                data[i][3] = resultSet.getString("genre");
                data[i][4] = resultSet.getString("rating");
                data[i][5] = resultSet.getString("duration");
                data[i][6] = resultSet.getString("sinopsis");
                data[i][7] = resultSet.getString("image");
                i++;
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }

    public void updateMovie(int id, String movie_name, String title, String genre, String sinopsis, String image, String rating, String duration) {
        try {
            String query = "UPDATE movie SET movie_name = ?, title = ?, genre = ?, rating = ?, duration = ?, sinopsis = ?, image = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, movie_name);
            statement.setString(2, title);
            statement.setString(3, genre);
            statement.setString(4, rating);
            statement.setString(5, duration);
            statement.setString(6, sinopsis);
            statement.setString(7, image);
            statement.setInt(8, id); 
            statement.executeUpdate();
            System.out.println("Update Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void deleteMovie(int id) {
        try {
           
            conn.setAutoCommit(false);
    
            String deleteShowtimeQuery = "DELETE FROM showtime WHERE movie_id = ?";
            PreparedStatement deleteShowtimeStmt = conn.prepareStatement(deleteShowtimeQuery);
            deleteShowtimeStmt.setInt(1, id);
            deleteShowtimeStmt.executeUpdate();
    
          
            String deleteMovieQuery = "DELETE FROM movie WHERE id = ?";
            PreparedStatement deleteMovieStmt = conn.prepareStatement(deleteMovieQuery);
            deleteMovieStmt.setInt(1, id);
            deleteMovieStmt.executeUpdate();
    
            conn.commit();
            System.out.println("Delete Success");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception rollbackEx) {
                System.out.println("Rollback Error: " + rollbackEx.getMessage());
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Restore auto-commit mode
            try {
                conn.setAutoCommit(true);
            } catch (Exception ex) {
                System.out.println("Error setting auto-commit: " + ex.getMessage());
            }
        }
    }
    

    public void insertCinema(String Location, int  seatingCapacity) {
        try {
            String query = "INSERT INTO cinema (Location, seating_capacity) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, Location);
            statement.setInt(2, seatingCapacity);
            statement.executeUpdate();
            System.out.println("Insert Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
          
        }

        public String [][] showCinema() {
        String data[][] = new String[100][3];
        try {
            int i = 0;
            String query = "SELECT * FROM cinema";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("id");
                data[i][1] = resultSet.getString("Location");
                data[i][2] = resultSet.getString("seating_capacity");
                i++;
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }

    public void updateCinema(int id, String Location, int seatingCapacity) {
        try {
            String query = "UPDATE cinema SET Location = ?, seating_capacity = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, Location);
            statement.setInt(2, seatingCapacity);
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println("Update Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }   

    public void deleteCinema(int id) {
        try {
            String query = "DELETE FROM cinema WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Delete Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void insertShowtime(int movieId, int cinemaId, Timestamp showtime, BigDecimal ticketPrice) {
        try {
            String query = "INSERT INTO showtime (movie_id, cinema_id, showtime, ticket_price) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, movieId);
            statement.setInt(2, cinemaId);
            statement.setTimestamp(3, showtime);
            statement.setBigDecimal(4, ticketPrice);
            statement.executeUpdate();
            System.out.println("Insert Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    

    public String [][] showShowtime () {
        String data[][] = new String[100][4];
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            int i = 0;
            String query = "SELECT * FROM showtime";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("id");
                data[i][1] = resultSet.getString("movie_id");
                data[i][2] = resultSet.getString("cinema_id");
                Timestamp showtime = resultSet.getTimestamp("showtime");
                data[i][3] = dateFormat.format(showtime);
                i++;
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }

    public void updateShowtime (int id, int movie_id, int cinema_id, Timestamp showtime) {
        try {
            String query = "UPDATE showtime SET movie_id = ?, cinema_id = ?, showtime = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, movie_id);
            statement.setInt(2, cinema_id);
            statement.setTimestamp(3, showtime);
            statement.setInt(4, id);
            statement.executeUpdate();
            System.out.println("Update Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteShowtime (int id) {
        try {
            String query = "DELETE FROM showtime WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Delete Success");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String[][] getShowtimeDetails(int id) {
        List<String[]> details = new ArrayList<>();
        try {
            String query = "SELECT s.id, m.title, m.duration, s.showtime FROM showtime s JOIN movie m ON s.movie_id = m.id WHERE s.id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(resultSet.getInt("id"));
                row[1] = resultSet.getString("title");
                row[2] = resultSet.getString("duration");
                row[3] = resultSet.getTimestamp("showtime").toString();
                details.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return details.toArray(new String[0][]);
    }


    public int getCinemaCapacity(int showtimeId) {
        int capacity = 0;
        try {
            String query = "SELECT c.seating_capacity FROM cinema c " +
                           "JOIN showtime s ON c.id = s.cinema_id " +
                           "WHERE s.id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, showtimeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                capacity = resultSet.getInt("seating_capacity");
            } else {
                throw new Exception("Showtime ID not found: " + showtimeId);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return capacity;
    }
    
    
    public ArrayList<Integer> getBookedSeats(int showtimeId) {
        ArrayList<Integer> bookedSeats = new ArrayList<>();
        try {
            String query = "SELECT selected_seats FROM ticket WHERE showtime_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, showtimeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookedSeats.add(resultSet.getInt("selected_seats"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return bookedSeats;
    }

    public int bookTicket (int user_id, int showtime_id, int seat) {
        try {
            String query = "INSERT INTO ticket (userid, showtimeid, selected_seats) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user_id);
            statement.setInt(2, showtime_id);
            statement.setInt(3, seat);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
           
        }
        return 0;
    }

    public void getAllBookingForUser (int user_id) {
        try {
            String query = "SELECT * FROM ticket WHERE userid = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getInt("userid") + " " + resultSet.getInt("showtimeid") + " " + resultSet.getInt("selected_seats"));
            }   
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
           
        }


    }

    public int removeBokking (int id) {
        try {
            String query = "DELETE FROM ticket WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return 1;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
    
   
    


    public String[][] getShowtimesData() {
        List<String[]> details = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT " +
                "m.movie_name, " +
                "c.location, " +
                "s.showtime, " + // Added missing comma before s.showtime
                "m.duration, " +
                "m.sinopsis, " +
                "m.genre, " +
                "m.image " +
                "FROM showtime s " +
                "JOIN movie m ON s.movie_id = m.id " +
                "JOIN cinema c ON s.cinema_id = c.id;";
    
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] row = new String[7]; // Updated array size to 7
                row[0] = resultSet.getString("movie_name");
                row[1] = resultSet.getString("location");
                row[2] = resultSet.getString("showtime");
                row[3] = resultSet.getString("duration");
                row[4] = resultSet.getString("sinopsis");
                row[5] = resultSet.getString("genre");
                row[6] = resultSet.getString("image");
                details.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return details.toArray(new String[0][]);
    }
    public int getShowtimeByMovieAndTimestamp(String movieName, Timestamp showtime) {
        int showtimeId = 0;
        try {
            String query = "SELECT s.id FROM showtime s " +
                           "JOIN movie m ON s.movie_id = m.id " +
                           "WHERE m.movie_name = ? AND s.showtime = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, movieName);
            statement.setTimestamp(2, showtime);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                showtimeId = resultSet.getInt("id");
            } else {
                throw new Exception("No showtime found for movie: " + movieName + " at " + showtime.toString());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return showtimeId;
    }
     public BigDecimal getTicketPrice(int showtimeId) {
        BigDecimal price = null;
        try {
            String query = "SELECT ticket_price FROM showtime WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, showtimeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getBigDecimal("ticket_price");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return price;
    }
    public boolean bookSeat(int showtimeId, int seatNumber, String username, BigDecimal price) {
        try {
            // Check if the username exists
            String checkUserQuery = "SELECT id FROM users WHERE username = ?";
            PreparedStatement userStmt = conn.prepareStatement(checkUserQuery);
            userStmt.setString(1, username);
            ResultSet userResultSet = userStmt.executeQuery();
    
            int userId;
            if (userResultSet.next()) {
                userId = userResultSet.getInt("id");
            } else {
                System.out.println("Error: Username not found.");
                return false; // Username not found, booking failed
            }
    
            // Insert booking details into the ticket table
            String insertTicketQuery = "INSERT INTO ticket (selected_seats, payment_status, showtime_id, user_id, ticket_price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ticketStmt = conn.prepareStatement(insertTicketQuery);
            ticketStmt.setInt(1, seatNumber);
            ticketStmt.setString(2, "Pending"); // Set initial payment status to "Pending"
            ticketStmt.setInt(3, showtimeId);
            ticketStmt.setInt(4, userId);
            ticketStmt.setBigDecimal(5, price);
            ticketStmt.executeUpdate();
    
            return true; // Booking successful
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false; // Booking failed
        }
    }
    

    public String[][] seeTicket(int userId) {
        List<String[]> details = new ArrayList<>();
        try {
            String query = "SELECT t.id, t.selected_seats, t.payment_status, t.ticket_price, s.showtime, m.movie_name, c.location, u.username " +
                           "FROM ticket t " +
                           "JOIN showtime s ON t.showtime_id = s.id " +
                           "JOIN movie m ON s.movie_id = m.id " +
                           "JOIN cinema c ON s.cinema_id = c.id " +
                           "JOIN users u ON t.user_id = u.id " +
                           "WHERE t.user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] row = new String[8];
                row[0] = resultSet.getString("id");
                row[1] = resultSet.getString("selected_seats");
                row[2] = resultSet.getString("payment_status");
                row[3] = resultSet.getString("ticket_price");
                row[4] = resultSet.getString("showtime");
                row[5] = resultSet.getString("movie_name");
                row[6] = resultSet.getString("location");
                row[7] = resultSet.getString("username");
                details.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging purposes
        }
        System.out.println("Details for userId " + userId + ": " + details); // Debugging line
    
        String[][] result = details.toArray(new String[0][]);
    
        for (String[] row : result) {
            System.out.println(Arrays.toString(row));
        }
        return result;
    }
    
    
    
    public boolean cancelTicket(int ticketId) {
        try {
            // Check if ticket exists
            String checkQuery = "SELECT * FROM ticket WHERE id = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setInt(1, ticketId);
            ResultSet resultSet = checkStatement.executeQuery();
            
            if (resultSet.next()) {
                // Ticket exists, proceed with deletion
                String deleteQuery = "DELETE FROM ticket WHERE id = ?";
                PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, ticketId);
                deleteStatement.executeUpdate();
                return true; // Indicating that the ticket was successfully cancelled
            } else {
                return false; // Ticket does not exist
            }
        } catch (SQLException e) {
            System.out.println("Error cancelling ticket: " + e.getMessage());
            return false; // Indicating that there was an error
        }
    }

   public void confirmCheckout (int userId) {
        try {
            String query = "UPDATE ticket SET payment_status = 'Confirmed' WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
   public BigDecimal calculateTotalPrice(int userId) {
    BigDecimal totalPrice = BigDecimal.ZERO;
    try {
        String query = "SELECT ticket_price, payment_status FROM ticket WHERE user_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            BigDecimal ticketPrice = resultSet.getBigDecimal("ticket_price");
            String paymentStatus = resultSet.getString("payment_status");
            if ("Pending".equals(paymentStatus)) {
                totalPrice = totalPrice.add(ticketPrice);
            }
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
     
    }
    return totalPrice;
}

    
    

    

    

   
    
    
}
