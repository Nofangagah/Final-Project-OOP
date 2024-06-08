package view;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cancelTicket extends JFrame {
    private Controller controller;
    private JFrame window;

    public cancelTicket(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        this.setTitle("Cancel Ticket");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titleLabel = new JLabel("Cancel Ticket", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18)); // Mengatur ukuran font lebih kecil
        panel.add(titleLabel);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel ticketIdLabel = new JLabel("Ticket ID:");
        JTextField ticketIdField = new JTextField(20);
        
        // Set preferred size for JTextField
        Dimension preferredSize = new Dimension(200, 30);
        ticketIdField.setPreferredSize(preferredSize);
        
        inputPanel.add(ticketIdLabel);
        inputPanel.add(ticketIdField);
        panel.add(inputPanel);

        JButton cancelButton = new JButton("Cancel Ticket");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticketIdStr = ticketIdField.getText();
                if (ticketIdStr != null && !ticketIdStr.isEmpty()) {
                    try {
                        int ticketId = Integer.parseInt(ticketIdStr);
                        int confirmation = JOptionPane.showConfirmDialog(cancelTicket.this, "Are you sure you want to cancel ticket ID " + ticketId + "?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
                        if (confirmation == JOptionPane.YES_OPTION) {
                            boolean success = controller.cancelTicket(ticketId);
                            if (success) {
                                JOptionPane.showMessageDialog(cancelTicket.this, "Ticket ID " + ticketId + " has been cancelled.");
                            } else {
                                JOptionPane.showMessageDialog(cancelTicket.this, "Ticket ID " + ticketId + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(cancelTicket.this, "Invalid Ticket ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(cancelButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Menutup jendela saat tombol "Back" ditekan
            }
        });
        panel.add(backButton);

        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }

}
