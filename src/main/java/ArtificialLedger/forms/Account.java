package ArtificialLedger.forms;
import ArtificialLedger.utils.AccountManager;
import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

/**
 * This class represents the Account management interface for the ArtificialLedger application.
 * It provides a graphical user interface for users to perform various banking operations.

 * Key features:
 * 1. Provides a main dashboard with navigation options for different banking operations.
 * 2. Implements panels for deposit, withdrawal, and balance inquiry operations.
 * 3. Includes voice effects for enhanced user experience.
 * 4. Uses Swing components for creating the GUI.

 * The interface is designed with a title panel, a side navigation panel, and a main content panel
 * that changes based on the selected operation.
 */

public class Account extends JPanel {
    private JTextField amountField;
    private String loggedInUsername;
    private String userAccountNumber;
    private double balance;
    private JPanel mainPanel;


    public Account(String username, String accountNumber) {
        this.loggedInUsername = username;
        this.userAccountNumber = accountNumber;
        this.balance = AccountManager.getBalance(accountNumber);
        initializeUI();
    }


    /**
     * Constructor for the Account class.
     * Initializes and sets up the main account interface.
     */

    public void initializeUI() {
        setLayout(new BorderLayout());
        setSize(600, 400);


        // Create and add the title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // Create and add the side navigation panel
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);

        // Create and add the main content panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Add functionality to navigation buttons
        addNavigationFunctionality(sidePanel, mainPanel);
    }

    /**
     * Creates the title panel for the account interface.
     *
     * @return JPanel The created title panel
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 255, 255));
        JLabel titleLabel = new JLabel("Bank Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    /**
     * Creates the side navigation panel with operation buttons.
     *
     * @return JPanel The created side panel
     */
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(5, 1));
        String[] buttonLabels = {"Home", "Deposit", "Withdraw", "Balance Inquiry", "Logout"};
        for (String label : buttonLabels) {
            sidePanel.add(new JButton(label));
        }
        return sidePanel;
    }

    /**
     * Creates the main content panel for displaying operation-specific interfaces.
     *
     * @return JPanel The created main panel
     */
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel homePanel = createHomePanel();
        mainPanel.add(homePanel, BorderLayout.CENTER);
        return mainPanel;
    }

    /**
     * Creates the home panel displayed when the account interface is first opened.
     *
     * @return JPanel The created home panel
     */
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(2, 1));
        JLabel welcomeLabel = new JLabel("Welcome to your Bank Account!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel instructionLabel = new JLabel("Please select an operation from the sidebar.", JLabel.CENTER);
        homePanel.add(welcomeLabel);
        homePanel.add(instructionLabel);
        return homePanel;
    }

    /**
     * Adds functionality to the navigation buttons in the side panel.
     *
     * @param sidePanel The side panel containing navigation buttons
     * @param mainPanel The main panel where content will be updated
     */
    private void addNavigationFunctionality(JPanel sidePanel, JPanel mainPanel) {
        Component[] buttons = sidePanel.getComponents();
        for (Component component : buttons) {
            if (component instanceof JButton button) {
                button.addActionListener(e -> handleNavigation(button.getText(), mainPanel));
            }
        }
    }

    /**
     * Handles navigation actions when a button is clicked.
     *
     * @param buttonText The text of the clicked button
     * @param mainPanel The main panel to update with new content
     */
    private void handleNavigation(String buttonText, JPanel mainPanel) {
        mainPanel.removeAll();
        switch (buttonText) {
            case "Home":
                mainPanel.add(createHomePanel(), BorderLayout.CENTER);
                playVoiceEffect("src/main/resources/voice-effect/welcome_voice_effect.wav");
                break;
            case "Deposit":
                mainPanel.add(createDepositPanel(), BorderLayout.CENTER);
                playVoiceEffect("src/main/resources/voice-effect/deposit_panel.wav");
                break;
            case "Withdraw":
                mainPanel.add(createWithdrawPanel(), BorderLayout.CENTER);
                playVoiceEffect("src/main/resources/voice-effect/withdrawal_panel.wav");
                break;
            case "Balance Inquiry":
                mainPanel.add(createBalanceInquiryPanel(), BorderLayout.CENTER);
                playVoiceEffect("src/main/resources/voice-effect/balance_voice_effect.wav");
                break;
            case "Logout":
                logout();
                return;
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    //method for Logout
    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        playVoiceEffect("src/main/resources/voice-effect/goodbye_voice_effect.wav");
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setContentPane(new Login());
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        });
    }

    /**
     * Plays a voice effect sound file.
     *
     * @param filePath The path to the sound file
     */
    private void playVoiceEffect(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the deposit panel for handling deposit operations.
     *
     * @return JPanel The created deposit panel
     * @ Revision of 1.0.0 - 06/22/2024 - Gabriel - Initial coding
     */
    private JPanel createDepositPanel() {
        JPanel depositPanel = new JPanel(new GridLayout(3, 1));
        JLabel titleLabel = new JLabel("Deposit", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        amountField = new JTextField(10);
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> deposit());

        depositPanel.add(titleLabel);
        depositPanel.add(createLabeledField("Amount:", amountField));
        depositPanel.add(depositButton);

        return depositPanel;
    }

    /**
     * Creates the withdrawal panel for handling withdrawal operations.
     *
     * @return JPanel The created withdraw panel
     * @ Revision of 1.0.0 - 06/22/2024 - Gabriel - Initial coding
     */
    private JPanel createWithdrawPanel() {
        JPanel withdrawPanel = new JPanel(new GridLayout(3, 1));
        JLabel titleLabel = new JLabel("Withdraw", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        amountField = new JTextField(10);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> withdraw());

        withdrawPanel.add(titleLabel);
        withdrawPanel.add(createLabeledField("Amount:", amountField));
        withdrawPanel.add(withdrawButton);

        return withdrawPanel;
    }

    /**
     * Creates the balance inquiry panel for checking account balance.
     *
     * @return JPanel The created balance inquiry panel
     */
    private JPanel createBalanceInquiryPanel() {
        JPanel balanceInquiryPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Balance Inquiry", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel balanceLabel = new JLabel("Current Balance: ₱" + balance, JLabel.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        balanceInquiryPanel.add(titleLabel);
        balanceInquiryPanel.add(balanceLabel);

        return balanceInquiryPanel;
    }

    /**
     * Creates a labeled field combining a JLabel and a JTextField.
     *
     * @param labelText The text for the label
     * @param textField The JTextField to be labeled
     * @return JPanel A panel containing the label and text field
     * @ Revision of 1.0.0 - 06/22/2024 - Gabriel - Initial coding
     */
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                boolean success = AccountManager.deposit(userAccountNumber, amount);
                if (success) {
                    balance = AccountManager.getBalance(userAccountNumber);
                    JOptionPane.showMessageDialog(this, "Deposit successful! Amount: ₱" + amount);
                    playVoiceEffect("src/main/resources/voice-effect/deposit_voice_effect.wav");
                } else {
                    JOptionPane.showMessageDialog(this, "Deposit failed. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                boolean success = AccountManager.withdraw(userAccountNumber, amount);
                if (success) {
                    balance = AccountManager.getBalance(userAccountNumber);
                    JOptionPane.showMessageDialog(this, "Withdrawal successful! Amount: ₱" + amount);
                    playVoiceEffect("src/main/resources/voice-effect/withdraw_voice_effect.wav");
                } else {
                    JOptionPane.showMessageDialog(this, "Withdrawal failed. Insufficient funds or invalid amount.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
        }
    }


/**
 private void checkBalance() {
 String accountNumber = accountNumberField.getText();
 if (AccountManager.accountExists(accountNumber)) {
 double balance = AccountManager.getBalance(accountNumber);
 JOptionPane.showMessageDialog(this, "Balance for Account Number " + accountNumber + " is " + balance);
 playVoiceEffect("src/main/resources/voice-effect/balance_voice_effect.wav");
 } else {
 JOptionPane.showMessageDialog(this, "Account not found.");
 }
 }
 **/

}
