package view;

import javax.swing.*;
import controller.Controller;
import java.awt.Font;

public class Login extends JFrame {
    JLabel titleLabel = new JLabel("LOGIN");
    JFrame window = new JFrame("Login");
    JLabel username = new JLabel("Username");
    JTextField usernameField = new JTextField();
    JLabel password = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    JButton login = new JButton("Login");
    private boolean loginSuccess = false;
    private boolean isAdmin = false;

    public Login(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(500, 400);
        window.setLocationRelativeTo(null);

        titleLabel.setBounds(240, 10, 180, 25);
        window.add(titleLabel);
        titleLabel.setBounds(210, 10, 180, 50); // Letakkan di bagian atas form dan sesuaikan ukuran font
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        window.add(titleLabel);

        username.setBounds(50, 60, 100, 25);
        window.add(username);

        usernameField.setBounds(160, 60, 180, 25);
        window.add(usernameField);

        password.setBounds(50, 100, 100, 25);
        window.add(password);

        passwordField.setBounds(160, 100, 180, 25);
        window.add(passwordField);

        login.setBounds(160, 150, 180, 25);
        window.add(login);

        login.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loginSuccess = controller.loginUser(username, password);
            isAdmin = controller.isAdmin(username);

            if (loginSuccess) {
                homepageWindow(controller, isAdmin);
            } else {
                JOptionPane.showMessageDialog(window, "Login failed. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        window.setVisible(true);
    }

    private void homepageWindow(Controller controller, boolean isAdmin) {
        window.dispose();
        if (loginSuccess) {
            if (isAdmin) {
                Adminpage adminHomepage = new Adminpage(controller);
            } else {
                UserPage userHomepage = new UserPage(controller);
            }
        }
    }
}
