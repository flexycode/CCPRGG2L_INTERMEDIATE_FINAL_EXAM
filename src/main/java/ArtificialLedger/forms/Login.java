package ArtificialLedger.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
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
 * 5. Navigates to the Account page upon successful login.
 * 6. Utilizes custom painting for a semi-transparent, rounded rectangle background.

 * The login form is styled using FlatLaf client properties for a consistent
 * look and feel across the application. It uses MigLayout for flexible
 * component positioning.
 */


public class Login extends JPanel {

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
        // Component initialization and styling code...

        // Event listeners for login and registration button clicks...
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
        });
        setLayout(new MigLayout("wrap,fillx,insets 45 45 50 45", "[fill]"));
        JLabel title = new JLabel("Login to your account", SwingConstants.CENTER);
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
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
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your account number");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        add(title);
        add(new JLabel("Account Number"), "gapy 20");
        add(txtUsername);
        add(new JLabel("Password"), "gapy 10");
        add(txtPassword);
        add(chRememberMe);
        add(cmdLogin, "gapy 30");
        add(cmdForgotPassword, "gapy 10");
        add(cmdRegister, "gapy 10");

        cmdRegister.addActionListener(e -> {
            // Registration form creation and display...
            RegistrationForm registrationForm = new RegistrationForm();
            JFrame frame = new JFrame("Registration Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(registrationForm);
            frame.pack();
            frame.setVisible(true);
        });

        cmdLogin.addActionListener(e -> {
            // User authentication and navigation logic...
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            if (authenticateUser(username, password)) {
                // User authentication successful
                // Add your logic for what happens after successful login
                HomeOverlay homeOverlay = new HomeOverlay();
                homeOverlay.setVisible(false);


                // Open the Account window
                Account account = new Account();
                account.setVisible(true);
            } else {
                // User authentication failed
                // Add your logic for what happens after failed login
                JOptionPane.showMessageDialog(Login.this, "Invalid username or password");
            }
        });
    }

    /**
     * Authenticates a user based on the provided account number and password.
     * This method reads account details from a file and compares the stored
     * password with the user input.
     *
     * @param accountNo The account number entered by the user
     * @param password The password entered by the user
     * @return true if authentication is successful, false otherwise
     */

    private boolean authenticateUser(String accountNo, String password) {
        try {

            // File reading and password verification logic...
            // Set the file path to your desired location within the project codebase
            String filePath = "src/main/resources/account-details/" + accountNo + "_details.txt";

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Password:")) {
                    String storedPassword = line.substring(line.indexOf(" ") + 1);
                    if (password.equals(storedPassword)) {
                        reader.close();
                        return true;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
