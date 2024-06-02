package view;

import javax.swing.*;
import controller.Controller;
import java.awt.*;

public class UserPage extends JFrame {
    JFrame window = new JFrame("Homepage");
    JLabel tes = new JLabel("Homepage");
    JButton logoutButton = new JButton("Logout");
    JLabel imageLabel;

    public UserPage(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(1000, 1000);
        window.setLocationRelativeTo(null);

        tes.setBounds(160, 100, 100, 25);
        window.add(tes);

        logoutButton.setBounds(870, 10, 100, 25);
        window.add(logoutButton);

        // Load the image using getResource
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("Assets/1105633.jpg"));

        // Check if the image is loaded successfully
        if (imageIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Image could not be loaded from path: Assets/image.jpg");
            return;
        }

        // Scale the image
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);

        // Add the image to JLabel
        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(200, 200, 300, 200 ); // Set position and size of the image
        window.add(imageLabel);

        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(window, "Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                controller.Logout(window);
            }
        });

        window.setVisible(true);
    }

    public static void main(String[] args) {
        // Dummy controller for testing purposes
        Controller controller = new Controller();
        new UserPage(controller);
    }
}
