package controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Model;
import view.Login;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class Controller {
    Model model;
    private int currentUserId;  // Ubah dari static ke instance variable

    public Controller() {
        this.model = new Model(this);
        
    }

    public void registerUser(String username, String password, String confirmPassword) {
        model.registerUser(username, password, confirmPassword);
    }

    public void registerAdmin(String username, String password, String confirmPassword) {
        model.registerAdmin(username, password, confirmPassword);
    }

    public boolean loginUser(String username, String password) {
        return model.loginUser(username, password);
    }

    public boolean isAdmin(String username) {
        return model.isAdmin(username);
    }

    public void insertMovie(String name, String title, String genre, String sinopsis, String image, String rating, String duration) {
        model.insertMovie(name, title, genre, sinopsis, image, rating, duration);
    }

    public String[][] showMovies() {
        return model.showMovies();
    }

    public void updateMovie(int id, String movie_name, String title, String genre, String sinopsis, String image, String rating, String duration) {
        model.updateMovie(id, movie_name, title, genre, sinopsis, image, rating, duration);
    }
    public void deleteMovie(int id) {
        model.deleteMovie(id);
    }
    

    public void insertCinema(String location, int seatingCapacity) {
        model.insertCinema(location, seatingCapacity);
    }

    public String[][] showCinema() {
        return model.showCinema();
    }

    public void updateCinema(int id, String Location, int seatingCapacity) {
        model.updateCinema(id, Location, seatingCapacity);
    }

    public void deleteCinema(int id) {
        model.deleteCinema(id);
    }

    public void insertShowtime(int movieId, int cinemaId, Timestamp showtime, BigDecimal ticketprice) {
        model.insertShowtime(movieId, cinemaId, showtime, ticketprice);
    }

    public String[][] showShowtime() {
        return model.showShowtime();
    }

    public void updateShowtime(int id, int movie_id, int cinema_id, Timestamp showtime) {
        model.updateShowtime(id, movie_id, cinema_id, showtime);
    }

    public String[][] showtimeDetails(int id) {
        return model.getShowtimeDetails(id);
    }

    public int getCinemaCapacity(int showtime) {
        return model.getCinemaCapacity(showtime);
    }

    public ArrayList<Integer> getBookedSeats(int showtimeId) {
        return model.getBookedSeats(showtimeId);
    }

    public boolean bookTicket(int user_id, int showtime_id, int seat) {
        return model.bookTicket(user_id, showtime_id, seat) > 0;
    }

    public void showAllBookingsForUser(int user_id) {
        model.getAllBookingForUser(user_id);
    }

    public boolean deleteBooking(int id) {
        return model.removeBokking(id) > 0;
    }

    public String[][] getShowtimesData() {
        return model.getShowtimesData();
    }

    public int getShowtimeByMovieAndTimestamp(String movieName, Timestamp showtime) {
        return model.getShowtimeByMovieAndTimestamp(movieName, showtime);
    }

    public BigDecimal getTicketPrice(int showtimeId) {
        return model.getTicketPrice(showtimeId);
    }

    public boolean bookSeat(int showtimeId, int seatNumber, String username, BigDecimal price) {
        return model.bookSeat(showtimeId, seatNumber, username, price);
    }

    public String[][] seeTicket(int userId) {
        return model.seeTicket(userId);
    }

    public boolean cancelTicket(int ticketId) {
        return model.cancelTicket(ticketId);
    }

    public void confirmCheckout(int ticketId) {
        model.confirmCheckout(ticketId);
    }

    public BigDecimal calculateTotalPrice(int userId) {
        return model.calculateTotalPrice(userId);
    }

    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }

    public int getCurrentUserId() {
        return this.currentUserId;
    }

    public void Logout(JFrame currentFrame) {
        JOptionPane.showMessageDialog(null, "Logout Success");
        currentFrame.dispose();  // Close the current window
        new Login(this);  // Show the login window
    }
}
