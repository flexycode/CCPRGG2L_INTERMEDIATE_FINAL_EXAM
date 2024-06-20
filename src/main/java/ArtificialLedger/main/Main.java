package ArtificialLedger.main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;
import ArtificialLedger.forms.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class serves as the entry point for the ArtificialLedger application.
 * It sets up the main application window and initializes the user interface.

 * Key features:
 * 1. Uses FlatLaf library for a modern, flat look and feel with a dark theme.
 * 2. Implements a custom undecorated JFrame as the main window.
 * 3. Initializes and displays the Home component as the main content.
 * 4. Handles window events for proper initialization and cleanup.
 * 5. Scales the UI for different display resolutions.

 * The main window is set to a fixed size of 1365x768 pixels (scaled) and
 * positioned at the center of the screen. The application uses the Roboto
 * font family as its default font.
 */

public class Main extends JFrame {

    private Home home;

    /**
     * Constructor for the Main class. Calls the init() method to set up the frame.
     */
    public Main() {
        init();
    }

    /**
     * Initializes the main application window and its components.
     * This method sets up the JFrame properties, creates the Home component,
     * and adds necessary window listeners for proper functionality.
     */
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(UIScale.scale(new Dimension(1365, 768)));
        setLocationRelativeTo(null);
        home = new Home();
        setContentPane(home);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                home.initOverlay(Main.this);
                home.play(0);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                home.stop();
            }
        });
    }

    /**
     * The main method that launches the application.
     * It sets up the FlatLaf look and feel, registers custom themes,
     * configures the default font, and creates the main application window.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}

