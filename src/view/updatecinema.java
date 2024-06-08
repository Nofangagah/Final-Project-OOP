package view;

import javax.swing.*;
import java.awt.*;
import controller.Controller;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updatecinema extends JFrame {

    public updatecinema(Controller controller) {

        setTitle("Update Cinema");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel location = new JLabel("Location");

        JTextField locationField = new JTextField();

        JLabel seatingCapacity = new JLabel("Seating Capacity");

        JTextField seatingCapacityField = new JTextField();

        JButton submit = new JButton("Submit");

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String location = locationField.getText();
                String seatingCapacity = seatingCapacityField.getText();

                if (location.isEmpty() || seatingCapacity.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                try {
                    int seatingCapacityInt = Integer.parseInt(seatingCapacity);

                    if (seatingCapacityInt < 0) {
                        JOptionPane.showMessageDialog(null, "Seating capacity cannot be negative.");
                        return;
                    }

                    controller.updateCinema(seatingCapacityInt, location, seatingCapacityInt);
                    JOptionPane.showMessageDialog(null, "Cinema updated successfully.");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid seating capacity format.");
                }
            }
        });
    }
        

    
}
