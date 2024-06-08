package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Arrays;

public class TicketDetailsView extends JFrame {
    private JFrame window = new JFrame("Your Tickets");
    private JLabel titleLabel = new JLabel("Your Tickets");
    private JTable ticketTable;
    private JButton backButton = new JButton("Back");
    private JButton checkoutButton = new JButton("Checkout");
    private JLabel totalLabel = new JLabel("Total to Pay: Rp.0.00"); // Total label

    public TicketDetailsView(Controller controller, int userId) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(titleLabel, BorderLayout.NORTH);

        // DefaultTableModel without data
        DefaultTableModel model = new DefaultTableModel();
        ticketTable = new JTable(model);
        ticketTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        window.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        buttonPanel.add(backButton);

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String totalStr = JOptionPane.showInputDialog(window, "Enter total price for checkout:");
                if (totalStr != null && !totalStr.isEmpty()) {
                    try {
                        BigDecimal totalPrice = new BigDecimal(totalStr);
                        BigDecimal totalCalculatedPrice = controller.calculateTotalPrice(userId);
                        if (totalPrice.compareTo(totalCalculatedPrice) >= 0) {
                            // Jika pembayaran melebihi atau sama dengan total harga
                            BigDecimal change = totalPrice.subtract(totalCalculatedPrice);
                            controller.confirmCheckout(userId);
                            String message = "Checkout successful! Payment status updated to 'Confirmed'.";
                            if (change.compareTo(BigDecimal.ZERO) > 0) {
                                message += "\nChange: Rp." + change.toString();
                            }
                            JOptionPane.showMessageDialog(window, message);
                            populateTable(controller, userId); // Refresh ticket table
                        } else {
                            // Jika pembayaran kurang dari total harga
                            JOptionPane.showMessageDialog(window, "Insufficient payment. Please enter an amount equal to or greater than the total.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(window, "Invalid input. Please enter a valid number for total price.");
                    }
                }
            }
        });
        

        buttonPanel.add(checkoutButton);
        window.add(buttonPanel, BorderLayout.SOUTH);

        // Add total label to the button panel
        buttonPanel.add(totalLabel);

        window.setVisible(true);

        // Call the method to populate the table when the view is initialized
        populateTable(controller, userId);
    }

    private void populateTable(Controller controller, int userId) {
        String[] columnNames = {"Ticket ID", "Seats", "Payment Status", "Price", "Showtime", "Movie", "Location", "User Name"};
        String[][] ticketData = controller.seeTicket(userId);

        // Calculate total price
        BigDecimal totalCalculatedPrice = controller.calculateTotalPrice(userId);
        totalLabel.setText("Total to Pay: Rp." + totalCalculatedPrice.toString()); // Update total label

        // Print ticket data to check
        for (String[] row : ticketData) {
            System.out.println("Ticket Data: " + Arrays.toString(row));
        }

        if (ticketData.length == 0) {
            JOptionPane.showMessageDialog(this, "No tickets found for the user.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        // Update the model with the retrieved data
        DefaultTableModel model = new DefaultTableModel(ticketData, columnNames);
        ticketTable.setModel(model);
    }

    private boolean isTotalPriceCorrect(BigDecimal totalPrice, int userId, Controller controller) {
        BigDecimal totalCalculatedPrice = controller.calculateTotalPrice(userId);
        return totalCalculatedPrice.compareTo(totalPrice) == 0;
    }
}
