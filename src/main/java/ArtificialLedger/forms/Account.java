package ArtificialLedger.forms;

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

public class Account extends JFrame {
    private JTextField accountNumberField;
    private JTextField amountField;

    /**
     * Constructor for the Account class.
     * Initializes and sets up the main account interface.
     */
    public Account() {
        setTitle("Bank Account");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

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
                dispose();
                playVoiceEffect("src/main/resources/voice-effect/goodbye_voice_effect.wav");
                return;
        }
        mainPanel.revalidate();
        mainPanel.repaint();
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
     */
    private JPanel createDepositPanel() {
        JPanel depositPanel = new JPanel(new GridLayout(4, 1));
        JLabel titleLabel = new JLabel("Deposit", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        accountNumberField = new JTextField(10);
        amountField = new JTextField(10);
        JButton depositButton = createOperationButton("Deposit", "Deposit successful! Amount: ", "src/main/resources/voice-effect/deposit_voice_effect.wav");

        depositPanel.add(titleLabel);
        depositPanel.add(createLabeledField("Account Number:", accountNumberField));
        depositPanel.add(createLabeledField("Amount:", amountField));
        depositPanel.add(depositButton);

        return depositPanel;
    }

    /**
     * Creates the withdrawal panel for handling withdrawal operations.
     *
     * @return JPanel The created withdraw panel
     */
    private JPanel createWithdrawPanel() {
        JPanel withdrawPanel = new JPanel(new GridLayout(4, 1));
        JLabel titleLabel = new JLabel("Withdraw", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        accountNumberField = new JTextField(10);
        amountField = new JTextField(10);
        JButton withdrawButton = createOperationButton("Withdraw", "Withdraw successful! Amount: ", "src/main/resources/voice-effect/withdraw_voice_effect.wav");

        withdrawPanel.add(titleLabel);
        withdrawPanel.add(createLabeledField("Account Number:", accountNumberField));
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
        JPanel balanceInquiryPanel = new JPanel(new GridLayout(4, 1));
        JLabel titleLabel = new JLabel("Balance Inquiry", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        accountNumberField = new JTextField(10);
        JButton balanceInquiryButton = createBalanceInquiryButton();

        balanceInquiryPanel.add(titleLabel);
        balanceInquiryPanel.add(createLabeledField("Account Number:", accountNumberField));
        balanceInquiryPanel.add(balanceInquiryButton);

        return balanceInquiryPanel;
    }

    /**
     * Creates a labeled field combining a JLabel and a JTextField.
     *
     * @param labelText The text for the label
     * @param textField The JTextField to be labeled
     * @return JPanel A panel containing the label and text field
     */
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    /**
     * Creates a button for deposit or withdraw operations.
     *
     * @param buttonText The text to display on the button
     * @param successMessage The message to display on successful operation
     * @param voiceEffectPath The path to the voice effect file
     * @return JButton The created operation button
     */
    private JButton createOperationButton(String buttonText, String successMessage, String voiceEffectPath) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> {
            double amount = Double.parseDouble(amountField.getText());
            JOptionPane.showMessageDialog(Account.this, successMessage + amount);
            playVoiceEffect(voiceEffectPath);
        });
        return button;
    }

    /**
     * Creates a button for balance inquiry operation.
     *
     * @return JButton The created balance inquiry button
     */
    private JButton createBalanceInquiryButton() {
        JButton balanceInquiryButton = new JButton("Check Balance");
        balanceInquiryButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            JOptionPane.showMessageDialog(Account.this, "Balance for Account Number " + accountNumber + " is ");
            playVoiceEffect("src/main/resources/voice-effect/balance_voice_effect.wav");
        });
        return balanceInquiryButton;
    }

    /**
     * Main method to run the Account application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Account().setVisible(true));
    }
}