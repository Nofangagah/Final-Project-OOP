package view;

import javax.swing.*;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addmovie extends JFrame {
    JFrame window = new JFrame("Add Movie");
    JLabel titleLabel = new JLabel("Add Movie");
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
    JButton submit = new JButton("Submit");

    public addmovie(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(500, 500);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        titleLabel.setBounds(160, 10, 180, 25);
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

        sinopsisField.setBounds(160, 180, 180, 50);
        window.add(sinopsisField);

        image.setBounds(50, 240, 100, 25);
        window.add(image);

        imageField.setBounds(160, 240, 180, 25);
        window.add(imageField);

        browseButton.setBounds(350, 240, 100, 25);
        window.add(browseButton);

        rating.setBounds(50, 280, 100, 25);
        window.add(rating);

        ratingField.setBounds(160, 280, 180, 25);
        window.add(ratingField);

        duration.setBounds(50, 320, 100, 25);
        window.add(duration);

        durationField.setBounds(160, 320, 180, 25);
        window.add(durationField);

        submit.setBounds(160, 360, 100, 25);
        window.add(submit);

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

        submit.addActionListener(e -> {
            try {
               
                controller.insertMovie(
                    nameField.getText(),
                    titleField.getText(),
                    genreField.getText(),
                    sinopsisField.getText(),
                    imageField.getText(),
                    ratingField.getText(),
                    durationField.getText()
                );
                window.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Duration must be a number");
            }
        });
    }
}
