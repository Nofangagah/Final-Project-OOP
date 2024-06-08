package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShowtimesDetails extends JFrame {

    private Controller controller;
    private JTable table;
    private DefaultTableModel model;

    public ShowtimesDetails(Controller controller, int showtimeId) {
        super("Showtimes Details");

        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        Object[] columnNames = {"ID", "Title", "Duration", "Showtime"};
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            new Adminmenu3(controller);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(back);

        this.add(buttonPanel, BorderLayout.NORTH);

        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Load data into the table
        loadData(showtimeId);

        this.setVisible(true);
    }

    private void loadData(int id) {
        model.setRowCount(0); 
        String[][] data = controller.showtimeDetails(id);
        if (data != null) {
            for (String[] row : data) {
                model.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No details found for the selected showtime.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
