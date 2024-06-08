package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import controller.Controller;

public class bookticket extends JFrame {

    private Controller controller;
    private JPanel seatPanel;
    private JLabel priceLabel;
    private String selectedMovie;
    private Timestamp selectedTimestamp;
    private int showtimeChoice;
    private BigDecimal ticketPrice;

    public bookticket(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
        this.setTitle("Book Ticket");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JLabel movieLabel = new JLabel("Enter the movie name: ");
        JTextField movieInput = new JTextField(20);
        
        JLabel timestampLabel = new JLabel("Enter the showtime (YYYY-MM-DD HH:MM:SS): ");
        JTextField timestampInput = new JTextField(20);

        priceLabel = new JLabel("Ticket Price: Rp.0.00");

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            String movieName = movieInput.getText().trim();
            String timestampStr = timestampInput.getText().trim();
            if (!movieName.isEmpty() && !timestampStr.isEmpty()) {
                try {
                    Timestamp timestamp = Timestamp.valueOf(timestampStr);
                    displayAvailableSeats(movieName, timestamp);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid timestamp format (YYYY-MM-DD HH:MM:SS).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid movie name and showtime.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(movieLabel);
        inputPanel.add(movieInput);
        inputPanel.add(timestampLabel);
        inputPanel.add(timestampInput);

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(priceLabel, BorderLayout.CENTER);

        // Panel tempat duduk
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 10, 5, 5)); // 10 kursi per baris

        JScrollPane scrollPane = new JScrollPane(seatPanel);

        this.add(mainPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void displayAvailableSeats(String movieName, Timestamp timestamp) {
        this.selectedMovie = movieName;
        this.selectedTimestamp = timestamp;
        seatPanel.removeAll(); // Hapus tampilan kursi sebelumnya

        showtimeChoice = controller.getShowtimeByMovieAndTimestamp(movieName, timestamp);
        ticketPrice = controller.getTicketPrice(showtimeChoice);
        
        // Update price label
        priceLabel.setText("Ticket Price: Rp." + ticketPrice.toString());

        int capacity = controller.getCinemaCapacity(showtimeChoice);
        ArrayList<Integer> bookedSeats = controller.getBookedSeats(showtimeChoice);

        for (int i = 1; i <= capacity; i++) {
            JButton seatButton = new JButton(String.valueOf(i));
            if (bookedSeats.contains(i)) {
                seatButton.setBackground(Color.RED);
                seatButton.setEnabled(false);
            } else {
                seatButton.setBackground(Color.GREEN);
                final int seatNum = i; // Assign seat number to a final variable
                seatButton.addActionListener(e -> bookSeat(seatNum));
            }

            seatButton.setPreferredSize(new Dimension(40, 30)); // Atur ukuran tombol kursi
            seatPanel.add(seatButton);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private void bookSeat(int seatNumber) {
        // Display a form to enter user details and show ticket price
        JTextField nameField = new JTextField();
        JLabel priceLabel = new JLabel("Ticket Price: Rp" + ticketPrice.toString());
        Object[] message = {
            "Name:", nameField,
            priceLabel
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Enter your details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();

            // Perform booking and payment logic
            boolean isBooked = controller.bookSeat(showtimeChoice, seatNumber, name, ticketPrice);
            if (isBooked) {
                JOptionPane.showMessageDialog(this, "Seat booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Update the seat to show it is booked
                displayAvailableSeats(selectedMovie, selectedTimestamp);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to book the seat. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
