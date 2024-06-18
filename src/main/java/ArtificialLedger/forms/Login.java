package ArtificialLedger.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Login extends JPanel {

    public Login() {
        init();
    }

    private void init() {
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

        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "" +
                "margin:5,10,5,10;" +
                "focusWidth:1;" +
                "innerFocusWidth:0");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "margin:5,10,5,10;" +
                "focusWidth:1;" +
                "innerFocusWidth:0;" +
                "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:$Component.accentColor;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        cmdForgotPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:$Component.accentColor;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:$Component.accentColor;" +
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
        });

        cmdLogin.addActionListener(e -> {
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

    private boolean authenticateUser(String username, String password) {
        try {
            // Set the file path to your desired location within the project codebase
            String filePath = "src/main/resources/account-details/" + username + "_details.txt";

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
