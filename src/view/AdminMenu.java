package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import controller.Controller;
import java.awt.event.*;

public class AdminMenu extends JFrame {
    private Controller controller;
    private JTable table;
    private DefaultTableModel model;

    public AdminMenu(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.setTitle("Admin Menu - Movies CRUD");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        Object[] columnNames = {"id", "Name", "Title", "Genre", "Rating", "Duration", "Sinopsis", "Image"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Custom renderer for the image column
        table.getColumnModel().getColumn(7).setCellRenderer(new ImageRenderer());

        // Adjust the width of each column as needed
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Title
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Genre
        table.getColumnModel().getColumn(4).setPreferredWidth(50);  // Rating
        table.getColumnModel().getColumn(5).setPreferredWidth(50);  // Duration
        table.getColumnModel().getColumn(6).setPreferredWidth(300); // Sinopsis
        table.getColumnModel().getColumn(7).setPreferredWidth(300); // Image
        table.setRowHeight(300);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadData());
        topPanel.add(refreshButton);
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            this.dispose();
            new Adminpage(controller);
        });
            
            
        topPanel.add(back);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String idStr = table.getValueAt(row, 0).toString();
                try {
                    int id = Integer.parseInt(idStr);
                    String name = table.getValueAt(row, 1).toString();
                    int input = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete " + name + " with ID: " + id + "?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (input == 0) {
                        System.out.println("Deleting movie with ID: " + id);
                        controller.deleteMovie(id);
                        loadData();
                    } else {
                        input = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to update " + name + " with ID: " + id + "?",
                                "Confirm Update", JOptionPane.YES_NO_OPTION);
                        if (input == 0) {
                            new updatemovie(controller);
                            loadData();
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID format: " + idStr, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.addActionListener(e -> new addmovie(controller));
        topPanel.add(addMovieButton);

        this.add(topPanel, BorderLayout.NORTH);

        loadData();
        this.setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        String[][] data = controller.showMovies();
        for (String[] row : data) {
            if (row[0] != null) {
                try {
                    Integer.parseInt(row[0]); // Ensure ID is an integer
                    model.addRow(row);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid ID format in data: " + row[0]);
                }
            }
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
