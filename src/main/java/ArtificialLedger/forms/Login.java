package ArtificialLedger.forms;

import ArtificialLedger.utils.AccountManager;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class represents the Login panel for the ArtificialLedger application.
 * It provides a user interface for authentication and navigation to other forms.

 * Key features:
 * 1. Uses FlatLaf library for modern UI components and styling.
 * 2. Implements a custom-designed login form with username and password fields.
 * 3. Provides options for remembering login, password recovery, and new user registration.
 * 4. Authenticates users against stored account details.
 * 5. Implements a secondary PIN code verification after successful login.
 * 6. Navigates to the Account page upon successful login and PIN verification.
 * 7. Utilizes custom painting for a semi-transparent, rounded rectangle background.

 * The login form is styled using FlatLaf client properties for a consistent
 * look and feel across the application. It uses MigLayout for flexible
 * component positioning.
 */
public class Login extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    /**
     * Constructor for the Login class. Calls the init() method to set up the panel.
     */
    public Login() {
        init();
    }

    /**
     * Initializes and configures the login panel and its components.
     * This method sets up the layout, creates and styles the UI components,
     * and adds necessary event listeners for user interactions.
     */
    private void init() {
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
        });
        setLayout(new MigLayout("wrap,fillx,insets 45 45 50 45", "[fill]"));
        JLabel title = new JLabel("Login to your account", SwingConstants.CENTER);
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        JCheckBox chRememberMe = new JCheckBox("Remember me");
        JButton cmdLogin = new JButton("Login");
        JButton cmdForgotPassword = new JButton("Forgot Password");
        JButton cmdRegister = new JButton("Register");

        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "margin:5,10,5,10;" +
                "focusWidth:1;" +
                "innerFocusWidth:0");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "margin:5,10,5,10;" +
                "focusWidth:1;" +
                "innerFocusWidth:0;" +
                "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "background:$Component.accentColor;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        cmdForgotPassword.putClientProperty(FlatClientProperties.STYLE, "background:$Component.accentColor;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "background:$Component.accentColor;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        add(title);
        add(new JLabel("Username"), "gapy 20");
        add(txtUsername);
        add(new JLabel("Password"), "gapy 10");
        add(txtPassword);
        add(chRememberMe);
        add(cmdLogin, "gapy 30");
        add(cmdForgotPassword, "gapy 10");
        add(cmdRegister, "gapy 10");

        cmdRegister.addActionListener(e -> {
            RegistrationForm registrationForm = new RegistrationForm();
            registrationForm.setVisible(true);
            // Close the current login window if needed
            Window loginWindow = SwingUtilities.getWindowAncestor(Login.this);
            if (loginWindow != null) {
                loginWindow.dispose();
            }
        });

        cmdLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            System.out.println("Attempting login with username: " + username); // Debug log

            if (AccountManager.authenticateUser(username, password)) {
                System.out.println("User authenticated successfully"); // Debug log
                String accountNumber = AccountManager.getAccountNumber(username);
                System.out.println("Retrieved account number: " + accountNumber); // Debug log

                if (accountNumber != null) {
                    // Prompt for PIN code
                    String pinCode = JOptionPane.showInputDialog(Login.this, "Enter your PIN code:", "PIN Verification", JOptionPane.PLAIN_MESSAGE);
                    if (pinCode != null && verifyPinCode(username, pinCode)) {
                        AccountManager.loadAccountData(accountNumber);
                        System.out.println("Account data loaded for account number: " + accountNumber); // Debug log

                        SwingUtilities.invokeLater(() -> {
                            JFrame accountFrame = new JFrame("Bank Account");
                            accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            Account accountPanel = new Account(username, accountNumber);
                            accountFrame.setContentPane(accountPanel);
                            accountFrame.pack();
                            accountFrame.setLocationRelativeTo(null);
                            accountFrame.setVisible(true);

                            Window loginWindow = SwingUtilities.getWindowAncestor(Login.this);
                            if (loginWindow != null) {
                                loginWindow.dispose();
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Invalid PIN code", "PIN Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.out.println("Account number not found for user: " + username); // Debug log
                    JOptionPane.showMessageDialog(Login.this, "Account number not found for this user", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Authentication failed for username: " + username); // Debug log
                JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Verifies the PIN code for a given username.
     * This method checks the PIN code against the stored data in the details.txt file.
     *
     * @param ignoredUsername The username to verify the PIN code for
     * @param pinCode The entered PIN code
     * @return true if the PIN code is correct, false otherwise
     */
    private boolean verifyPinCode(String ignoredUsername, String pinCode) {
        File accountDetailsDirectory = new File("src/main/resources/account-details/");
        File[] accountDetailFiles = accountDetailsDirectory.listFiles();
        if (accountDetailFiles != null) {
            for (File accountDetailFile : accountDetailFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(accountDetailFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("Pin Code: " + pinCode)) {
                            return true;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error reading account detail file: " + accountDetailFile.getName());
                }
            }
        }
        return false;
    }


    /**
     * Overrides the paintComponent method to create a custom background
     * for the login panel. This method paints a semi-transparent,
     * rounded rectangle as the panel's background.
     *
     * @param g The Graphics object to paint on
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = UIScale.scale(20);
        g2.setColor(getBackground());
        g2.setComposite(AlphaComposite.SrcOver.derive(0.6f));
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
        g2.dispose();
        super.paintComponent(g);
    }
}
