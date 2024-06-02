package view;

import javax.swing.*;
import controller.Controller;

public class Adminpage extends JFrame {
    public Adminpage(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);

        JLabel tes = new JLabel("Homepage");

        tes.setBounds(160, 100, 100, 25);
        this.add(tes);

        JButton logoutButton = new JButton("Logout");

        logoutButton.setBounds(870, 10, 100, 25);
        this.add(logoutButton);

        this.setVisible(true);

        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                controller.Logout(this);
            }
        });
    }
}
