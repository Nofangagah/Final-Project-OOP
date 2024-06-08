package view;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.*;


public class showtimepageUser extends JFrame {
    private Controller controller;
    private JTable showtimesTable;
    private DefaultTableModel model;

    public showtimepageUser(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        this.setTitle("Showtimes");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Table columns
        String[] columnNames = {"Movie Name", "Cinema Location", "Showtime", "Duration", "Synopsis", "Genre", "Image"};
        model = new DefaultTableModel(columnNames, 0);
        showtimesTable = new JTable(model);

        // Custom renderer for the image column
        showtimesTable.getColumnModel().getColumn(6).setCellRenderer(new ImageRenderer());

        // Adjust the width of each column as needed
        showtimesTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Movie Name
        showtimesTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Cinema Location
        showtimesTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Showtime
        showtimesTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Duration
        showtimesTable.getColumnModel().getColumn(4).setPreferredWidth(300); // Synopsis
        showtimesTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Genre
        showtimesTable.getColumnModel().getColumn(6).setPreferredWidth(300); // Image
        showtimesTable.setRowHeight(200);

        JScrollPane scrollPane = new JScrollPane(showtimesTable);
        this.add(scrollPane, BorderLayout.CENTER);
        JPanel topPanel = new JPanel();
        this.add(topPanel, BorderLayout.NORTH);

        loadShowtimesData();

        this.setVisible(true);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new UserPage(controller);
            dispose();
        });
        topPanel.add(backButton);
    }
 
    

    private void loadShowtimesData() {
        // Fetch showtimes data from controller
        String[][] showtimesData = controller.getShowtimesData();

        // Populate the table model
        for (String[] row : showtimesData) {
            model.addRow(row);
        }
    }

    // ImageRenderer class to display images
    class ImageRenderer implements TableCellRenderer {
        JLabel lbl = new JLabel();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                String imagePath = (String) value;

                try {
                    BufferedImage img = ImageIO.read(new File(imagePath));
                    if (img != null) {
                        Image scaledImg = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Adjust size as needed
                        lbl.setIcon(new ImageIcon(scaledImg));
                    } else {
                        lbl.setIcon(null);
                        System.err.println("Failed to load image from path: " + imagePath);
                    }
                } catch (Exception e) {
                    lbl.setIcon(null);
                    System.err.println("Exception while loading image: " + e.getMessage());
                }
            } else {
                lbl.setIcon(null);
            }

            if (isSelected) {
                lbl.setBackground(table.getSelectionBackground());
            } else {
                lbl.setBackground(table.getBackground());
            }
            lbl.setOpaque(true);
            return lbl;
        }
    }
}
