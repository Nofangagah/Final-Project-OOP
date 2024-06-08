package view;
import controller.Controller;

import java.awt.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import javax.swing.*;

public class Adminmenu2 extends JFrame {
    private Controller controller;
    private JTable table;
    private DefaultTableModel model;

    public Adminmenu2(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.setTitle("Admin Menu - Cinema CRUD");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        Object[] columnNames = { "Location", "Seating Capacity"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addCinemaButton = new JButton("Add Cinema");
        addCinemaButton.addActionListener(e -> new addcinema(controller));
        buttonPanel.add(addCinemaButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadData());
        buttonPanel.add(refreshButton);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            this.dispose();
            new Adminpage(controller);
        });
        buttonPanel.add(back);
        

        this.add(buttonPanel, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String idStr = table.getValueAt(row, 0).toString();
                try {
                    int id = Integer.parseInt(idStr);
                    String location = table.getValueAt(row, 1).toString();
                    int input = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete " + location + " with ID: " + id + "?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (input == 0) {
                        System.out.println("Deleting cinema with ID: " + id);
                        controller.deleteCinema(id);
                        loadData();
                    } else {
                        input = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to update " + location + " with ID: " + id + "?",
                                "Confirm Update", JOptionPane.YES_NO_OPTION);
                        if (input == 0) {
                            new updatecinema(controller);
                            loadData();
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID format: " + idStr, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadData();
        this.setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        String[][] data = controller.showCinema();
        for (String[] row : data) {
            if (row[0] != null) {
                try {
                    model.addRow(row);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data format: " + row[0]);
                }
            }
        }
    }

   
}
