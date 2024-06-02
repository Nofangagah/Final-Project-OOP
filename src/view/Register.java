package view;

import javax.swing.*;
import controller.Controller;
import java.awt.Font;

public class Register extends JFrame {
    JFrame window = new JFrame("Register");
    JLabel titleLabel = new JLabel("REGISTER");
    JLabel username = new JLabel("Username");
    JTextField usernameField = new JTextField();
    JLabel password = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    JLabel confirmPassword = new JLabel("Confirm Password");
    JPasswordField confirmPasswordField = new JPasswordField();

    JLabel roleLabel = new JLabel("Role");
    String[] roles = {"User", "Admin"};
    JComboBox<String> roleComboBox = new JComboBox<>(roles);

    JButton register = new JButton("Register");
    JButton login = new JButton("Already have an account? Login");

    public Register(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(500, 400); // Adjust the size to fit the form nicely
        window.setLocationRelativeTo(null); // Center the window on the screen

        // Set the title label
        titleLabel.setBounds(180, 10, 180, 50); // Letakkan di bagian atas form dan sesuaikan ukuran font
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        window.add(titleLabel);

        int formPaddingTop = 20; // Padding tambahan di atas form untuk memberi ruang di bawah title

        // Set bounds for the labels and text fields with additional padding
        username.setBounds(50, formPaddingTop + 50, 100, 25);
        window.add(username);

        usernameField.setBounds(160, formPaddingTop + 50, 180, 25);
        window.add(usernameField);

        password.setBounds(50, formPaddingTop + 100, 100, 25);
        window.add(password);

        passwordField.setBounds(160, formPaddingTop + 100, 180, 25);
        window.add(passwordField);

        confirmPassword.setBounds(50, formPaddingTop + 150, 130, 25);
        window.add(confirmPassword);

        confirmPasswordField.setBounds(160, formPaddingTop + 150, 180, 25);
        window.add(confirmPasswordField);

        roleLabel.setBounds(50, formPaddingTop + 200, 100, 25);
        window.add(roleLabel);

        roleComboBox.setBounds(160, formPaddingTop + 200, 180, 25);
        window.add(roleComboBox);

        register.setBounds(160, formPaddingTop + 250, 150, 25);
        window.add(register);

        login.setBounds(160, formPaddingTop + 290, 200, 25);
        window.add(login);

        register.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            if (role.equals("Admin")) {
                controller.registerAdmin(username, password, confirmPassword);
            } else {
                controller.registerUser(username, password, confirmPassword);
            }
            openLoginWindow();
        });

        login.addActionListener(e -> {
            openLoginWindow();
        });

        window.setVisible(true);
    }

    private void openLoginWindow() {
        window.dispose();
        Controller controller = new Controller();
        Login login = new Login(controller);
    }
}
