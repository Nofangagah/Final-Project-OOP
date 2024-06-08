package view;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Adminpage extends JFrame {
    private Controller controller;

    public Adminpage(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.setTitle("Admin Homepage");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titleLabel = new JLabel("Admin Homepage", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(titleLabel);

        JButton crudMoviesButton = new JButton("CRUD Movies");
        crudMoviesButton.addActionListener(e -> new AdminMenu(controller));
        panel.add(crudMoviesButton);

        JButton showTimesButton = new JButton(" Showtimes");
        showTimesButton.addActionListener(e -> {
            new Adminmenu3(controller);
            
        });
        panel.add(showTimesButton);

        JButton addTheaterButton = new JButton("CRUDTheater");
        addTheaterButton.addActionListener(e -> {
            new Adminmenu2(controller);
        
        });
        panel.add(addTheaterButton);


        JButton TicketButton = new JButton("Ticket");
        TicketButton.addActionListener(e -> {
            new adminmenu4(controller);
        });
        panel.add(TicketButton);

       

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener((ActionEvent e) -> {
            int option = JOptionPane.showConfirmDialog(this, "Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                controller.Logout(this);
            }
        });
        panel.add(logoutButton);

        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
