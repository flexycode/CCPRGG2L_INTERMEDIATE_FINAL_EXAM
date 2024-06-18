package ArtificialLedger.forms;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.Serializable;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationForm extends JFrame {
    private static final long serialVersionUID = 1L;

    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBackground(new Color(0, 0, 25, 255));
        getContentPane().add(mainPanel);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(5, 5, 5, 5);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;

        // Add the titleLabel with background images
        JLabel titleLabel = new JLabel("ALTBank Registration Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/image-2.png");
        titleLabel.setIcon(imageIcon);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.BOTTOM);

        // Set the maximum size to the original image dimensions
        titleLabel.setMaximumSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 2;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbcMain);

        // Create the registration form panel with GridBagLayout
        BackgroundPanel formPanel = new BackgroundPanel("src/main/resources/images/background-image-3.jpg");
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(5, 5, 5, 5);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;

        // Labels and text fields
        String[] labels = {"First Name:", "Last Name:", "Middle Name:", "Birthday:",
                "Gender:", "Father's Name:", "Mother's Name:",
                "Contact Number:", "Address:", "Pin Code:",
                "Username:", "Password:", "Initial Deposit (PHP):"};

        int y = 0;
        JTextField[] textFields = new JTextField[labels.length]; // Array to store the text fields

        for (String label : labels) {
            gbcForm.gridx = 0;
            gbcForm.gridy = y;
            gbcForm.anchor = GridBagConstraints.EAST;
            formPanel.add(new JLabel(label), gbcForm);

            gbcForm.gridx = 1;
            gbcForm.gridy = y;
            gbcForm.anchor = GridBagConstraints.WEST;

            if (label.equals("Password:")) {
                JPasswordField passwordField = new JPasswordField(20);
                formPanel.add(passwordField, gbcForm);
                textFields[y] = passwordField;
            } else {
                JTextField textField = new JTextField(20);
                if (label.equals("Initial Deposit (PHP):")) {
                    textField.setText("500");
                }
                formPanel.add(textField, gbcForm);
                textFields[y] = textField;
            }
            y++;
        }

        // Register button
        gbcForm.gridx = 1;
        gbcForm.gridy = y;
        gbcForm.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        formPanel.add(registerButton, gbcForm);

        // Action listener for the register button
        registerButton.addActionListener(e -> {
            // Store the details
            String[] details = new String[labels.length];
            for (int i = 0; i < labels.length; i++) {
                details[i] = labels[i] + " " + textFields[i].getText();
            }
            String username = textFields[10].getText(); // Get the username from the text field
            storeDetails(details, username);
        });

        // Add form panel to main panel
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 2;
        mainPanel.add(formPanel, gbcMain);

        setVisible(true);
    }

    // Method to store the registration form details
    private void storeDetails(String[] details, String username) {
        try {
            // Set the file path to your desired location within the project codebase
            String filePath = "src/main/resources/account-details/" + username + "_details.txt";

            FileWriter fileWriter = new FileWriter(filePath, true); // Append to the existing file

            // Write the details to the file
            for (String detail : details) {
                fileWriter.write(detail + "\n");
            }

            // Add default account value
            fileWriter.write("Account No.: 2024100000\n");

            fileWriter.close();
            System.out.println("Registration form details for user " + username + " stored successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing the registration form details for user " + username + ".");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        backgroundImage = imageIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}


