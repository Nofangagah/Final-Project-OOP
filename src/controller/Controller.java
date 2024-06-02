package controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Model;
import view.Login;

public class Controller {
    Model model;

    public Controller() {
        this.model = new Model(this);
    }

    public void registerUser(String username, String password, String confirmPassword) {
        model.registerUser(username, password, confirmPassword);
    }

    public void registerAdmin(String username, String password, String confirmPassword) {
        model.registerAdmin(username, password, confirmPassword);
    }

    public boolean loginUser(String username, String password) {
        return model.loginUser(username, password);
    }

    public boolean isAdmin(String username) {
        return model.isAdmin(username);
    }
    


    public void Logout(JFrame currentFrame) {
        JOptionPane.showMessageDialog(null, "Logout Success");
        currentFrame.dispose();  // Close the current window
        new Login(this);  // Show the login window
    }
}
