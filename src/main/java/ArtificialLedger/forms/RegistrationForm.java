package ArtificialLedger.forms;

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

 * The form uses a combination of GridBagLayout and GridLayout for flexible and
 * responsive component positioning. It also implements custom background painting
 * for enhanced visual appeal.
 */
public class RegistrationForm extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int accountNo = 2024100000; // Starting account number

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
        JTextField[] textFields = new JTextField[labels.length];

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
            // Collect and store user details
            String[] details = new String[labels.length];
            for (int i = 0; i < labels.length; i++) {
                details[i] = labels[i] + " " + textFields[i].getText();
            }
            String username = textFields[10].getText();
            storeDetails(details, username);

            // Display success message and close form
            JOptionPane.showMessageDialog(this, "Thank you! You have successfully registered an account.");
            dispose();
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

    /**
     * Stores the registration form details in a text file.
     *
     * @param details Array of user details collected from the form
     * @param accountNumber The username, used as part of the file name
     */
    private void storeDetails(String[] details, String accountNumber) {
        try {
            String filePath = "src/main/resources/account-details/" + accountNumber + "_details.txt";
            FileWriter fileWriter = new FileWriter(filePath, true);

            for (String detail : details) {
                fileWriter.write(detail + "\n");
            }

            String accountNo = generateAccountNumber();
            fileWriter.write("Account No.: " + accountNo + "\n");

            fileWriter.close();
            System.out.println("Registration form details for account number " + accountNumber + " stored successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing the registration form details for user " + accountNumber + ".");
            e.printStackTrace();
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