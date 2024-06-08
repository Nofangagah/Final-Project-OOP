package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Cinemacapacity extends JFrame {
    private Controller controller;
    private JLabel capacityLabel;

    public Cinemacapacity(Controller controller, int showtimeId) {
        super("Cinema Capacity");

        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;

        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            new Adminmenu3(controller);
            dispose();
        });
        back.setBounds(10, 10, 100, 30);
        add(back);

        capacityLabel = new JLabel();
        capacityLabel.setBounds(10, 50, 580, 30);
        add(capacityLabel);

        loadCapacity(showtimeId);

        setVisible(true);
    }

    private void loadCapacity(int showtimeId) {
        int capacity = controller.getCinemaCapacity(showtimeId);
        capacityLabel.setText("Seating Capacity: " + capacity);
    }
}
