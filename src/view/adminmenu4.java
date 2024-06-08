package view;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class adminmenu4 extends JFrame {
    private Controller controller;
    private JPanel seatPanel;

    public adminmenu4(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
        this.setTitle("Ticket Management");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JLabel titleLabel = new JLabel("TICKETS", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel showtimeLabel = new JLabel("Enter your showtime choice: ");
        JTextField showtimeInput = new JTextField(5);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            try {
                int showtimeChoice = Integer.parseInt(showtimeInput.getText());
                displayAvailableSeats(showtimeChoice);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid showtime choice.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(showtimeLabel);
        inputPanel.add(showtimeInput);
        inputPanel.add(submitButton);

        panel.add(inputPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("BACK");
        backButton.addActionListener(e -> {
            new Adminpage(controller);
            dispose();
        });

        buttonPanel.add(backButton);

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 10, 5, 5)); // 10 seats per row

        this.add(panel, BorderLayout.NORTH);
        this.add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void displayAvailableSeats(int showtimeChoice) {
        seatPanel.removeAll(); // Clear previous seats display

        int capacity = controller.getCinemaCapacity(showtimeChoice);
        ArrayList<Integer> bookedSeats = controller.getBookedSeats(showtimeChoice);

        for (int i = 1; i <= capacity; i++) {
            JButton seatButton = new JButton(String.valueOf(i));
            if (bookedSeats.contains(i)) {
                seatButton.setBackground(Color.RED);
                seatButton.setEnabled(false);
            } else {
                seatButton.setBackground(Color.GREEN);
            }

            seatButton.setPreferredSize(new Dimension(40, 30)); // Set seat button size
            seatPanel.add(seatButton);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }
}
