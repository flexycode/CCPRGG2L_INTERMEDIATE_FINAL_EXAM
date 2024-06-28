package ArtificialLedger.forms;

import ArtificialLedger.utils.AccountManager;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;

/**
 * This class represents the Registration Form for the ArtificialLedger application.
 * It provides a user interface for new users to create an account by entering their details.

 * Key features:
 * 1. Creates a visually appealing registration form with a custom background.
 * 2. Collects various user details including personal information and account credentials.
 * 3. Generates a unique account number for each new registration.
 * 4. Stores user details in a text file for future reference and authentication.
 * 5. Provides immediate feedback upon successful registration.
 * 6. Navigates to the Login page upon successful registration.
 * 7. Utilizes custom painting for a semi-transparent, rounded rectangle background.

 * The form uses a combination of GridBagLayout and GridLayout for flexible and
 * responsive component positioning. It also implements custom background painting
 * for enhanced visual appeal.
 */
public class RegistrationForm extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int accountNo = 2024100000; // Starting account number
    private final JTextField[] textFields;

    /**
     * Constructor for the RegistrationForm class.
     * Initializes and sets up the registration form window.
     */
    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create and set up the main panel
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBackground(new Color(0, 0, 25, 255));
        getContentPane().add(mainPanel);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(5, 5, 5, 5);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;

        // Add title label with background image
        JLabel titleLabel = getjLabel();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 2;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbcMain);

        // Create the registration form panel
        BackgroundPanel formPanel = new BackgroundPanel("src/main/resources/images/background-image-3.jpg");
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(5, 5, 5, 5);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;

        // Define labels for form fields
        String[] labels = {"First Name:", "Last Name:", "Middle Name:", "Birthday:",
                "Gender:", "Father's Name:", "Mother's Name:",
                "Contact Number:", "Address:", "Pin Code:",
                "Username:", "Password:", "Initial Deposit (PHP):"};

        // Create and add form fields
        int y = 0;
        textFields = new JTextField[labels.length];

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

        // Add register button
        gbcForm.gridx = 1;
        gbcForm.gridy = y;
        gbcForm.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        formPanel.add(registerButton, gbcForm);

        // Action listener for register button
        registerButton.addActionListener(e -> {
            if (validateInputs()) {
                String username = textFields[10].getText();
                String password = new String(((JPasswordField) textFields[11]).getPassword());
                String initialDeposit = textFields[12].getText();

                storeDetails(username, password, initialDeposit);
                JOptionPane.showMessageDialog(this, "Thank you! You have successfully registered an account.");
                dispose();
                new Login().setVisible(true);  // Open the Login form after successful registration
            }
        });

        // Add form panel to main panel
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 2;
        mainPanel.add(formPanel, gbcMain);

        setVisible(true);
    }

    /**
     * Creates and returns a styled JLabel for the form title.
     *
     * @return JLabel A styled label with title text and background image
     * Checks if the pin code is 6 characters long.
     */
    private static JLabel getjLabel() {
        JLabel titleLabel = new JLabel("ALTBank Registration Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/image-2.png");
        titleLabel.setIcon(imageIcon);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.BOTTOM);
        titleLabel.setMaximumSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        return titleLabel;
    }

    private boolean validateInputs() {
        for (JTextField field : textFields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        try {
            double deposit = Double.parseDouble(textFields[12].getText());
            if (deposit < 500) {
                JOptionPane.showMessageDialog(this, "Initial deposit must be at least 500 PHP.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid initial deposit amount.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Checks if the pin code is 6 characters long.
        String pinCode = textFields[9].getText();
        if (pinCode.length() != 6) {
            JOptionPane.showMessageDialog(this, "Pin code must be 6 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Stores the registration form details in a text file.
     *
     * @param username        The username entered in the registration form
     * @param password        The password entered in the registration form
     * @param initialDeposit  The initial deposit amount entered in the registration form
     */
    private void storeDetails(String username, String password, String initialDeposit) {
        try {
            String accountNumber = generateAccountNumber();
            String filePath = "src/main/resources/account-details/" + username + "_details.txt";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write("Username: " + username + "\n");
                fileWriter.write("Password: " + password + "\n");
                fileWriter.write("Account No.: " + accountNumber + "\n");
                fileWriter.write("Balance: " + initialDeposit + "\n");

                String[] labels = {"First Name:", "Last Name:", "Middle Name:", "Birthday:",
                        "Gender:", "Father's Name:", "Mother's Name:",
                        "Contact Number:", "Address:", "Pin Code:"};

                for (int i = 0; i < labels.length; i++) {
                    fileWriter.write(labels[i] + " " + textFields[i].getText() + "\n");
                }
            }
            System.out.println("Registration details for user " + username + " stored successfully.");
            // Add this line after successfully writing the file:
            AccountManager.registerNewAccount(username, accountNumber, Double.parseDouble(initialDeposit));
        } catch (IOException ex) {
            System.out.println("An error occurred while storing the registration details for user " + username + ".");
            ex.printStackTrace();
        }
    }

    /**
     * Generates a unique account number for new registrations.
     *
     * @return String A unique account number
     */
    private String generateAccountNumber() {
        accountNo++;
        return String.valueOf(accountNo);
    }
}

/**
 * Custom JPanel class that paints a background image.
 * Used to create a visually appealing background for the registration form.
 */
class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    /**
     * Constructor for BackgroundPanel.
     *
     * @param imagePath Path to the background image file
     */
    public BackgroundPanel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        backgroundImage = imageIcon.getImage();
    }

    /**
     * Overrides paintComponent to draw the background image.
     *
     * @param g The Graphics object to paint on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
