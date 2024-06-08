package view;
import controller.Controller;

import java.awt.Font;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updatemovie  extends JFrame{
    JFrame window = new JFrame("Update Movie");
    JLabel titleLabel = new JLabel("Update Movie");
    JLabel name = new JLabel("Name");
    JTextField nameField = new JTextField();
    JLabel title = new JLabel("Title");
    JTextField titleField = new JTextField();
    JLabel genre = new JLabel("Genre");
    JTextField genreField = new JTextField();
    JLabel sinopsis = new JLabel("Sinopsis");
    JTextArea sinopsisField = new JTextArea();
    JLabel image = new JLabel("Image");
    JTextField imageField = new JTextField();
    JButton browseButton = new JButton("Browse...");
    JLabel rating = new JLabel("Rating");
    JTextField ratingField = new JTextField();
    JLabel duration = new JLabel("Duration");
    JTextField durationField = new JTextField();
    JButton Update = new JButton("Submit");
    public updatemovie(Controller controller) {
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
        titleLabel.setBounds(210, 10, 180, 50); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        window.add(titleLabel);

        name.setBounds(50, 60, 100, 25);
        window.add(name);

        nameField.setBounds(160, 60, 180, 25);
        window.add(nameField);

        title.setBounds(50, 100, 100, 25);
        window.add(title);

        titleField.setBounds(160, 100, 180, 25);
        window.add(titleField);

        genre.setBounds(50, 140, 100, 25);
        window.add(genre);

        genreField.setBounds(160, 140, 180, 25);
        window.add(genreField);

        sinopsis.setBounds(50, 180, 100, 25);
        window.add(sinopsis);

        sinopsisField.setBounds(160, 180, 180, 25);
        window.add(sinopsisField);

        image.setBounds(50, 220, 100, 25);
        window.add(image);

        imageField.setBounds(160, 220, 180, 25);
        window.add(imageField);

        browseButton.setBounds(350, 220, 100, 25);
        window.add(browseButton);

        rating.setBounds(50, 260, 100, 25);
        window.add(rating);

        ratingField.setBounds(160, 260, 180, 25);
        window.add(ratingField);

        duration.setBounds(50, 300, 100, 25);
        window.add(duration);

        durationField.setBounds(160, 300, 180, 25);
        window.add(durationField);

        Update.setBounds(160, 340, 100, 25);
        window.add(Update);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    imageField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });

        Update.addActionListener(e -> {
            String name = nameField.getText();
            String title = titleField.getText();
            String genre = genreField.getText();
            String sinopsis = sinopsisField.getText();
            String image = imageField.getText();
            String rating = ratingField.getText();
            String duration = durationField.getText();
            controller.updateMovie(getDefaultCloseOperation(), name, title, genre, sinopsis, image, rating, duration);
            window.dispose();
            new updatemovie(controller);
        });
        



        
        

    }

    
}
