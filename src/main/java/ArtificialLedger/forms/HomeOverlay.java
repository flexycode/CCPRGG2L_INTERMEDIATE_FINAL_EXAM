package ArtificialLedger.forms;

import ArtificialLedger.components.EventHomeOverlay;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.CubicBezierEasing;
import net.miginfocom.swing.MigLayout;
import ArtificialLedger.components.HeaderButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 * This class represents an overlay for the home screen of the ArtificialLedger application.
 * It provides a visually appealing and interactive interface for users to navigate through different sections.

 * Key features:
 * 1. Creates a semi-transparent overlay window with dynamic content.
 * 2. Implements smooth animations for transitions between different locations/pages.
 * 3. Provides a header with navigation buttons and a login option.
 * 4. Displays location-specific information with title, description, and a "Read More" button.
 * 5. Includes an animated login panel that can be toggled on and off.
 * 6. Uses custom styling with FlatLaf properties for a modern look.
 */
public class HomeOverlay extends JWindow {
    private PanelOverlay overlay;
    private List<ModelLocation> locations;

    /**
     * Default constructor for HomeOverlay.
     */
    public HomeOverlay() {
    }

    /**
     * Getter for the PanelOverlay instance.
     * @return The PanelOverlay instance.
     */
    public PanelOverlay getOverlay() {
        return overlay;
    }

    /**
     * Constructor that initializes the HomeOverlay with a parent frame and locations.
     * @param frame The parent JFrame.
     * @param locations List of ModelLocation objects representing different pages/sections.
     */

    public HomeOverlay(JFrame frame, List<ModelLocation> locations) {
        super(frame);
        this.locations = locations;
        init();
    }

    /**
     * Initializes the HomeOverlay by setting up the background and adding the PanelOverlay.
     */
    private void init() {
        setBackground(new Color(35, 96, 135, 80));
        setLayout(new BorderLayout());
        overlay = new PanelOverlay();
        add(overlay);
    }

    /**
     * Inner class representing the main panel of the overlay.
     * This class handles the layout, animations, and interaction of the overlay components.
     */
    public class PanelOverlay extends JPanel {
        private MigLayout migLayout;
        private EventHomeOverlay eventHomeOverlay;
        private AnimationType animationType = AnimationType.NONE;
        private Animator animator;
        private Animator loginAnimator;
        private float animate;
        private int index;
        private boolean showLogin;
        private JTextPane textTitle;
        private JTextPane textDescription;
        private Login panelLogin;

        /**
         * Sets the EventHomeOverlay listener.
         * @param eventHomeOverlay The EventHomeOverlay instance to set.
         */
        public void setEventHomeOverlay(EventHomeOverlay eventHomeOverlay) {
            this.eventHomeOverlay = eventHomeOverlay;
        }

        /**
         * Sets the current index and updates the displayed location information.
         * @param index The index of the current location.
         */
        public void setIndex(int index) {
            this.index = index;
            ModelLocation location = locations.get(index);
            textTitle.setText(location.title());
            textDescription.setText(location.description());
        }

        /**
         * Constructor for PanelOverlay. Initializes the panel components and layout.
         */
        public PanelOverlay() {
            init();
        }

        /**
         * Initializes the PanelOverlay by setting up the layout and components.
         */
        private void init() {
            setOpaque(false);
            migLayout = new MigLayout("fill,insets 10 180 10 180", "fill", "[grow 0][]");
            setLayout(migLayout);
            createHeader();
            createPageButton();
            createLogin();
            createContentPanel();
            setupMouseListener();
            setupAnimators();
        }

        /**
         * Creates the main content panel with title, description, and "Read More" button.
         */
        private void createContentPanel() {
            JPanel panel = new JPanel(new MigLayout("wrap", "", "[]30[]"));
            panel.setOpaque(false);
            textTitle = new JTextPane();
            textDescription = new JTextPane();
            JButton cmdReadMore = new JButton("Read More");

            setupTextComponents(textTitle, "+40");
            setupTextComponents(textDescription, "+2");
            setupReadMoreButton(cmdReadMore);

            panel.add(textTitle);
            panel.add(textDescription);
            panel.add(cmdReadMore);
            add(panel, "width 50%!");
        }

        /**
         * Sets up text components with specified font size and style.
         * @param textPane The JTextPane to set up.
         * @param fontSize The font size to apply.
         */
        private void setupTextComponents(JTextPane textPane, String fontSize) {
            textPane.setOpaque(false);
            textPane.setEditable(false);
            textPane.putClientProperty(FlatClientProperties.STYLE, "font:bold " + fontSize + ";" +
                    "border:0,0,0,0");
        }

        /**
         * Sets up the "Read More" button with custom styling.
         * @param button The JButton to set up.
         */
        private void setupReadMoreButton(JButton button) {
            button.putClientProperty(FlatClientProperties.STYLE, "background:$Component.accentColor;" +
                    "borderWidth:0;" +
                    "margin:5,15,5,15;" +
                    "focusWidth:0;" +
                    "innerFocusWidth:0;" +
                    "arc:999");
        }

        /**
         * Sets up the mouse listener for the panel to handle login animation.
         */
        private void setupMouseListener() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    runLoginAnimation(false);
                }
            });
        }

        /**
         * Sets up the animators for page transitions and login panel animation.
         */
        private void setupAnimators() {
            animator = new Animator(500, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v) {
                    animate = v;
                    repaint();
                }

                @Override
                public void end() {
                    if (animationType == AnimationType.CLOSE_VIDEO) {
                        eventHomeOverlay.onChanged(index);
                        SwingUtilities.invokeLater(() -> {
                            sleep();
                            runAnimation(index, AnimationType.SHOW_VIDEO);
                        });
                    } else {
                        animationType = AnimationType.NONE;
                    }
                }
            });
            loginAnimator = new Animator(500, v -> {
                float f = showLogin ? v : 1f - v;
                int x = (int) ((350 + 180) * f);
                migLayout.setComponentConstraints(panelLogin, "pos 100%-" + x + " 0.5al, w 350");
                revalidate();
            });
            animator.setInterpolator(CubicBezierEasing.EASE_IN);
            loginAnimator.setInterpolator(CubicBezierEasing.EASE);
        }

        /**
         * Introduces a small delay between animations.
         */
        private void sleep() {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        /**
         * Creates the header panel with navigation buttons and login option.
         */
        private void createHeader() {
            JPanel header = new JPanel(new MigLayout("fill", "[]push[][]"));
            header.setOpaque(false);
            JLabel title = new JLabel("ALTBank");
            title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
            HeaderButton home = new HeaderButton("Wallet");
            HeaderButton about = new HeaderButton("Exchange");
            HeaderButton explore = new HeaderButton("Explore");
            HeaderButton login = new HeaderButton("Login");

            login.addActionListener(e -> runLoginAnimation(true));

            header.add(title);
            header.add(home);
            header.add(about);
            header.add(explore);
            header.add(login);
            add(header, "wrap");
        }

        /**
         * Creates and adds the login panel to the overlay.
         */
        private void createLogin() {
            panelLogin = new Login();
            add(panelLogin, "pos 100% 0.5al,w 350");
        }

        /**
         * Creates the page selection buttons for navigating between different locations.
         */
        private void createPageButton() {
            JPanel panel = new JPanel(new MigLayout("gapx 20"));
            panel.setOpaque(false);
            for (int i = 0; i < locations.size(); i++) {
                JButton cmd = new JButton("");
                cmd.putClientProperty(FlatClientProperties.STYLE, "margin:5,5,5,5;" +
                        "arc:999;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "selectedBackground:$Component.accentColor");
                cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                final int index = i;
                cmd.addActionListener(e -> {
                    if (this.index != index) {
                        boolean act = runAnimation(index, AnimationType.CLOSE_VIDEO);
                        if (act) {
                            setSelectedButton(panel, index);
                        }
                    }
                });
                panel.add(cmd);
            }
            add(panel, "pos 0.5al 80%");
            setSelectedButton(panel, index);
        }

        /**
         * Sets the selected state of the page selection buttons.
         * @param panel The panel containing the buttons.
         * @param index The index of the button to select.
         */
        private void setSelectedButton(JPanel panel, int index) {
            int count = panel.getComponentCount();
            for (int i = 0; i < count; i++) {
                JButton cmd = (JButton) panel.getComponent(i);
                cmd.setSelected(i == index);
            }
        }

        /**
         * Runs the animation for transitioning between pages.
         * @param index The index of the target page.
         * @param animationType The type of animation to run.
         * @return True if the animation started, false otherwise.
         */
        private boolean runAnimation(int index, AnimationType animationType) {
            if (!animator.isRunning()) {
                this.animate = 0;
                this.animationType = animationType;
                this.index = index;
                animator.start();
                return true;
            } else {
                return false;
            }
        }

        /**
         * Runs the animation for showing or hiding the login panel.
         * @param show True to show the login panel, false to hide it.
         */
        private void runLoginAnimation(boolean show) {
            if (showLogin != show) {
                if (!loginAnimator.isRunning()) {
                    showLogin = show;
                    loginAnimator.start();
                }
            }
        }

        /**
         * Overrides the paintComponent method to handle custom drawing for animations.
         * @param g The Graphics object to paint on.
         */
        @Override
        protected void paintComponent(Graphics g) {
            if (animationType != AnimationType.NONE) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                g2.setColor(UIManager.getColor("Component.accentColor"));
                Rectangle rec = new Rectangle(0, 0, width, height);
                if (animationType == AnimationType.CLOSE_VIDEO) {
                    g2.setComposite(AlphaComposite.SrcOver.derive(animate));
                    g2.fill(rec);
                } else {
                    Area area = new Area(rec);
                    area.subtract(new Area(createRec(rec)));
                    g2.fill(area);
                }
                g2.dispose();
            }
            super.paintComponent(g);
        }

        /**
         * Creates a circular shape for the reveal animation.
         * @param rec The rectangle defining the bounds of the shape.
         * @return The circular Shape object.
         */
        private Shape createRec(Rectangle rec) {
            int maxSize = Math.max(rec.width, rec.height);
            float size = maxSize * animate;
            float x = (rec.width - size) / 2;
            float y = (rec.height - size) / 2;
            return new Ellipse2D.Double(x, y, size, size);
        }
    }

    /**
     * Enum representing different types of animations used in the overlay.
     */
    public enum AnimationType {
        CLOSE_VIDEO, SHOW_VIDEO, NONE
    }
}