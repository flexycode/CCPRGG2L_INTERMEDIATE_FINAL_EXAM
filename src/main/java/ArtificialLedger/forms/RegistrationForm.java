package ArtificialLedger.forms;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;

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

        // Add the title label with background images
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
        BackgroundPanel formPanel = new BackgroundPanel("src/main/resources/images/background-image-2.png");
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
        for (String label : labels) {
            gbcForm.gridx = 0;
            gbcForm.gridy = y;
            gbcForm.anchor = GridBagConstraints.EAST;
            formPanel.add(new JLabel(label), gbcForm);

            gbcForm.gridx = 1;
            gbcForm.gridy = y;
            gbcForm.anchor = GridBagConstraints.WEST;
            if (label.equals("Password:")) {
                formPanel.add(new JPasswordField(20), gbcForm);
            } else {
                JTextField textField = new JTextField(20);
                if (label.equals("Initial Deposit (PHP):")) {
                    textField.setText("500");
                }
                formPanel.add(textField, gbcForm);
            }
            y++;
        }

        // Register button
        gbcForm.gridx = 1;
        gbcForm.gridy = y;
        gbcForm.anchor = GridBagConstraints.CENTER;
        formPanel.add(new JButton("Register"), gbcForm);

        // Add form panel to main panel
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 2;
        mainPanel.add(formPanel, gbcMain);

        setVisible(true);
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