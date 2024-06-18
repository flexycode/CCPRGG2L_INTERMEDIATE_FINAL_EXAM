package ArtificialLedger.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;

import ArtificialLedger.components.BankAccount;
import ArtificialLedger.utils.AccountManager;


public class Account extends JFrame {
    private JTextField accountNumberField;
    private JTextField amountField;


    public Account() {
        setTitle("Bank Account");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the main layout
        setLayout(new BorderLayout());

        // Create the title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 255, 255));
        JLabel titleLabel = new JLabel("Bank Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        // Create the side panel for navigation
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(5, 1));
        JButton homeButton = new JButton("Home");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceInquiryButton = new JButton("Balance Inquiry");
        JButton logoutButton = new JButton("Logout");

        sidePanel.add(homeButton);
        sidePanel.add(depositButton);
        sidePanel.add(withdrawButton);
        sidePanel.add(balanceInquiryButton);
        sidePanel.add(logoutButton);

        // Create the main panel for account operations
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the home panel
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(2, 1));
        JLabel welcomeLabel = new JLabel("Welcome to your Bank Account!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel instructionLabel = new JLabel("Please select an operation from the sidebar.", JLabel.CENTER);
        homePanel.add(welcomeLabel);
        homePanel.add(instructionLabel);


        // Add the home panel to the main panel
        mainPanel.add(homePanel, BorderLayout.CENTER);

        // Add the panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Adding functionality to the buttons
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the home panel
                mainPanel.removeAll();
                mainPanel.add(homePanel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/welcome_voice_effect.wav");
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the deposit panel
                mainPanel.removeAll();
                mainPanel.add(createDepositPanel(), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/deposit_panel.wav");
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the withdrawal panel
                mainPanel.removeAll();
                mainPanel.add(createWithdrawPanel(), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/withdrawal_panel.wav");
            }
        });

        balanceInquiryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the balance inquiry panel
                mainPanel.removeAll();
                mainPanel.add(createBalanceInquiryPanel(), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/balance_voice_effect.wav");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click
                // You can add your logout logic here
                // For now, let's just close the application
                dispose();

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/goodbye_voice_effect.wav");
            }
        });
    }

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

    private JPanel createDepositPanel() {
        JPanel depositPanel = new JPanel();
        depositPanel.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Deposit", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(10);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle deposit button click
                // You can add your deposit logic here
                // For now, let's just display a message
                double amount = Double.parseDouble(amountField.getText());
                JOptionPane.showMessageDialog(Account.this, "Deposit successful! Amount: " + amount);

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/deposit_voice_effect.wav");
            }
        });

        depositPanel.add(titleLabel);
        depositPanel.add(accountNumberLabel);
        depositPanel.add(accountNumberField);
        depositPanel.add(amountLabel);
        depositPanel.add(amountField);
        depositPanel.add(depositButton);

        return depositPanel;
    }

    private JPanel createWithdrawPanel() {
        JPanel withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Withdraw", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(10);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle withdraw button click
                // You can add your withdrawal logic here
                // For now, let's just display a message
                double amount = Double.parseDouble(amountField.getText());
                JOptionPane.showMessageDialog(Account.this, "Withdraw successful! Amount: " + amount);

                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/withdraw_voice_effect.wav");
            }
        });

        withdrawPanel.add(titleLabel);
        withdrawPanel.add(accountNumberLabel);
        withdrawPanel.add(accountNumberField);
        withdrawPanel.add(amountLabel);
        withdrawPanel.add(amountField);
        withdrawPanel.add(withdrawButton);

        return withdrawPanel;
    }

    private JPanel createBalanceInquiryPanel() {
        JPanel balanceInquiryPanel = new JPanel();
        balanceInquiryPanel.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Balance Inquiry", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(10);

        JButton balanceInquiryButton = new JButton("Check Balance");
        balanceInquiryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle balance inquiry button click
                // You can add your balance inquiry logic here
                // For now, let's just display a message
                String accountNumber = accountNumberField.getText();
                JOptionPane.showMessageDialog(Account.this, "Balance for Account Number " + accountNumber + " is ");
                // Play the voice effect
                playVoiceEffect("src/main/resources/voice-effect/balance_voice_effect.wav");
            }
        });

        balanceInquiryPanel.add(titleLabel);
        balanceInquiryPanel.add(accountNumberLabel);
        balanceInquiryPanel.add(accountNumberField);
        balanceInquiryPanel.add(balanceInquiryButton);

        return balanceInquiryPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Account().setVisible(true);
            }
        });
    }
}
