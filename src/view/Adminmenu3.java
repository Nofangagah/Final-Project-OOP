package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Adminmenu3 extends JFrame {
    private Controller controller;
    private JTable table;
    private DefaultTableModel model;

    public Adminmenu3(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
        this.setTitle("Admin Menu - Showtimes CRUD");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        Object[] columnNames = {"ID", "Movie ID", "Cinema ID", "Showtime"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            new Adminpage(controller);
            dispose();
        });
        this.add(back, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton showtimeDetails = new JButton("Showtime Details");
        showtimeDetails.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int showtimeId = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
                new ShowtimesDetails(controller, showtimeId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a showtime to view details.", "No Showtime Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(showtimeDetails);

        JButton addShowtimeButton = new JButton("Add Showtime");
        addShowtimeButton.addActionListener(e -> {
            new addshowtime(controller);
        });
        buttonPanel.add(addShowtimeButton);

        JButton cinemaCapacityButton = new JButton("Cinema Capacity");
        cinemaCapacityButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int showtimeId = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
                new Cinemacapacity(controller, showtimeId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a showtime to view cinema capacity.", "No Showtime Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(cinemaCapacityButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadData();

        this.setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0); // Clear existing data
        String[][] data = controller.showShowtime();
        for (String[] row : data) {
            model.addRow(row);
        }
    }
}
