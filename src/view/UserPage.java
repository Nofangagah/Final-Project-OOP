package view;

import javax.swing.*;
import controller.Controller;
import java.awt.*;

public class UserPage extends JFrame {
    private Controller controller;

    public UserPage(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        this.setTitle("User Homepage");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titleLabel = new JLabel("HOMEPAGE", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(titleLabel);

        // Menu Buttons
        JButton showtimesButton = new JButton("Lihat Showtimes");
        showtimesButton.addActionListener(e -> {
            new showtimepageUser(controller);
        });
        panel.add(showtimesButton);

        JButton bookTicketButton = new JButton("Book Ticket");
        bookTicketButton.addActionListener(e -> {
            new bookticket(controller);
        });
        panel.add(bookTicketButton);

        JButton viewBookedTicketsButton = new JButton("Lihat Tiket yang Dibooking");
        viewBookedTicketsButton.addActionListener(e -> {
            new TicketDetailsView(controller, getDefaultCloseOperation());
        });
        panel.add(viewBookedTicketsButton);

        JButton cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.addActionListener(e -> {
            new cancelTicket(controller);
        });
        panel.add(cancelBookingButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                controller.Logout(this);
            }
        });
        panel.add(logoutButton);

        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Dummy controller for testing purposes
        Controller controller = new Controller();
        new UserPage(controller);
    }
}
