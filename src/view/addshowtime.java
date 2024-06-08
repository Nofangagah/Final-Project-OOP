package view;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class addshowtime extends JFrame {
    private JFrame window = new JFrame("Add Showtime");
    private JLabel titleLabel = new JLabel("Add Showtime");
    private JLabel movieIdLabel = new JLabel("Movie ID");
    private JTextField movieIdField = new JTextField();
    private JLabel cinemaIdLabel = new JLabel("Cinema ID");
    private JTextField cinemaIdField = new JTextField();
    private JLabel showtimeLabel = new JLabel("Showtime");
    private JTextField showtimeField = new JTextField();
    private JLabel ticketPriceLabel = new JLabel("Ticket Price");
    private JTextField ticketPriceField = new JTextField();
    private JButton submitButton = new JButton("Submit");
    private JButton backButton = new JButton("Back");

    public addshowtime(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(500, 500);
        window.setLocationRelativeTo(null);

        titleLabel.setBounds(160, 10, 180, 50); // Center title label
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        window.add(titleLabel);

        movieIdLabel.setBounds(20, 100, 100, 25);
        window.add(movieIdLabel);

        movieIdField.setBounds(160, 100, 180, 25);
        window.add(movieIdField);

        cinemaIdLabel.setBounds(20, 150, 100, 25);
        window.add(cinemaIdLabel);

        cinemaIdField.setBounds(160, 150, 180, 25);
        window.add(cinemaIdField);

        showtimeLabel.setBounds(20, 200, 100, 25);
        window.add(showtimeLabel);

        showtimeField.setBounds(160, 200, 180, 25);
        window.add(showtimeField);

        ticketPriceLabel.setBounds(20, 250, 100, 25);
        window.add(ticketPriceLabel);

        ticketPriceField.setBounds(160, 250, 180, 25);
        window.add(ticketPriceField);

        submitButton.setBounds(160, 300, 100, 25);
        window.add(submitButton);

        backButton.setBounds(280, 300, 100, 25); // Tambahkan tombol Back
        window.add(backButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int movieId = Integer.parseInt(movieIdField.getText());
                    int cinemaId = Integer.parseInt(cinemaIdField.getText());
                    Timestamp showtime = Timestamp.valueOf(showtimeField.getText());
                    BigDecimal ticketPrice = new BigDecimal(ticketPriceField.getText());
                    controller.insertShowtime(movieId, cinemaId, showtime, ticketPrice);
                    JOptionPane.showMessageDialog(window, "Showtime added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Invalid input. Please enter numeric values for Movie ID, Cinema ID, and Ticket Price.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(window, "Invalid input. Please enter the correct values.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(window, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });

        window.setVisible(true);
    }
}
