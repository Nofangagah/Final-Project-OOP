package view;

import javax.swing.*;
import java.awt.*;
import controller.Controller;

public class addcinema extends JFrame {
    JFrame window = new JFrame("Add Cinema");
    JLabel titleLabel = new JLabel("Add Cinema");
    JLabel location = new JLabel("Location");
    JTextField locationField = new JTextField();
    JLabel seatingCapacity = new JLabel("Seating Capacity");
    JTextField seatingCapacityField = new JTextField();
    JButton submit = new JButton("Submit");
    public addcinema(Controller controller) {

        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(500, 500);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        titleLabel.setBounds(240, 10, 180, 25);
        window.add(titleLabel);
        titleLabel.setBounds(210, 10, 180, 50); // Letakkan di bagian atas form dan sesuaikan ukuran font
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        window.add(titleLabel);

        location.setBounds(50, 60, 100, 25);
        window.add(location);

        locationField.setBounds(160, 60, 180, 25);
        window.add(locationField);

        seatingCapacity.setBounds(50, 100, 100, 25);
        window.add(seatingCapacity);

        seatingCapacityField.setBounds(160, 100, 180, 25);
        window.add(seatingCapacityField);

        submit.setBounds(150, 150, 100, 25);
        window.add(submit);

        submit.addActionListener(e -> {
            controller.insertCinema(locationField.getText(), Integer.parseInt(seatingCapacityField.getText()));
            window.dispose();
        });

    }

    
}
