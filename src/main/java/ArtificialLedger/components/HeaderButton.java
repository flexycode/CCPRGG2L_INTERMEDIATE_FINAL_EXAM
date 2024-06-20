package ArtificialLedger.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import ArtificialLedger.utils.RippleEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The HeaderButton class extends JButton to create a custom button component
 * specifically designed for use in header sections of a user interface.
 * This button incorporates a ripple effect for enhanced visual feedback
 * and uses FlatLaf styling properties for a modern appearance.

 * Key features:
 * - Custom ripple effect on click
 * - Rounded corners
 * - Bold, slightly larger font
 * - Hand cursor on hover
 * - Transparent background (content area not filled)

 * This class is part of the ArtificialLedger.components package and is designed
 * to work seamlessly with the ArtificialLedger application's UI theme.
 */

public class HeaderButton extends JButton {

    private RippleEffect rippleEffect;

    /**
     * Constructs a new HeaderButton with the specified text.
     *
     * @param text The text to be displayed on the button
     */
    public HeaderButton(String text) {
        super(text);
        init();
    }

    /**
     * Initializes the button's properties and appearance.
     * This method is called from the constructor to set up the button.
     */
    private void init() {
        // Create and associate a RippleEffect with this button
        rippleEffect = new RippleEffect(this);

        // Make the button's background transparent
        setContentAreaFilled(false);

        // Set the cursor to a hand icon when hovering over the button
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Apply FlatLaf styling properties
        // This sets the font to bold and increases its size by 3 points
        putClientProperty(FlatClientProperties.STYLE, "font:bold +3");
    }

    /**
     * Overrides the paint method to customize the button's appearance.
     * This method is responsible for rendering the button and its ripple effect.
     *
     * @param g The Graphics object used for painting
     */
    @Override
    public void paint(Graphics g) {
        // Call the superclasses paint method to render the basic button
        super.paint(g);

        // Calculate the arc size for rounded corners, scaling it for high DPI displays
        int arc = UIScale.scale(20);

        // Render the ripple effect within a rounded rectangle shape
        rippleEffect.reder(g, new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
    }
}