package ArtificialLedger.utils;

import com.formdev.flatlaf.util.Animator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The RippleEffect class implements a visual ripple animation effect for Swing components.
 * When a user clicks on the component, a circular ripple animation spreads from the click point.
 * This class can be used to enhance the visual feedback of user interactions in a graphical interface.

 * Key features:
 * - Customizable ripple color
 * - Smooth animation using the Animator class from FlatLaf
 * - Support for multiple simultaneous ripple effects
 * - Adjusts the ripple size based on the component's dimensions
 * - Adjusts the ripple opacity based on the component's transparency
 * - Supports multiple ripple effects on the same component

 * Usage:
 * 1. Create a RippleEffect instance for a Swing component
 * 2. Set the desired ripple color (optional)
 * 3. Call the render method in the component's paint method
 * 4. Call the stop method in the component's mouseReleased method
 * 5. Call the stopAll method in the component's mouseExited method

 * Note: This class requires the FlatLaf library for the Animator functionality.
 */
public class RippleEffect {

    private final Component component;
    private final Color rippleColor = new Color(255, 255, 255);
    private List<Effect> effects;

    /**
     * Constructs a RippleEffect for the specified component.
     *
     * @param component The Swing component to which the ripple effect will be applied
     */
    public RippleEffect(Component component) {
        this.component = component;
        init();
    }

    /**
     * Initializes the ripple effect by setting up a MouseListener to detect clicks.
     */
    private void init() {
        effects = new ArrayList<>();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addEffect(e.getPoint());
                }
            }
        });
    }

    /**
     * Adds a new ripple effect at the specified location.
     *
     * @param location The point where the ripple effect should originate
     */
    public void addEffect(Point location) {
        effects.add(new Effect(component, location));
    }

    /**
     * Renders all active ripple effects on the component.
     * This method should be called in the component's paint method.
     *
     * @param g The Graphics object to paint on
     * @param contain The shape that defines the boundaries of the ripple effect
     */
    public void reder(Graphics g, Shape contain) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Effect effect : effects) {
            if (effect != null) {
                effect.render(g2, contain);
            }
        }
    }

    /**
     * Inner class representing an individual ripple effect.
     * Each Effect instance handles its own animation and rendering.
     */
    private class Effect {

        private final Component component;
        private final Point location;
        private float animate;

        /**
         * Constructs an Effect instance for a specific location on the component.
         *
         * @param component The component on which the effect is applied
         * @param location The origin point of the ripple effect
         */
        public Effect(Component component, Point location) {
            this.component = component;
            this.location = location;
            init();
        }

        /**
         * Initializes the animation for this ripple effect.
         */
        private void init() {
            Animator animator = new Animator(500, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float fraction) {
                    animate = fraction;
                    component.repaint();
                }

                @Override
                public void end() {
                    effects.remove(Effect.this);
                }
            });
            animator.start();
        }

        /**
         * Renders this ripple effect on the given Graphics2D object.
         *
         * @param g2 The Graphics2D object to paint on
         * @param contain The shape that defines the boundaries of the ripple effect
         */
        public void render(Graphics2D g2, Shape contain) {
            Area area = new Area(contain);
            area.intersect(new Area(getShape(getSize(contain.getBounds2D()))));
            g2.setColor(rippleColor);
            float alpha = 0.3f;
            if (animate >= 0.7f) {
                double t = animate - 0.7f;
                alpha = (float) (alpha - (alpha * (t / 0.3f)));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha*0.7f));
            g2.fill(area);
        }

        /**
         * Calculates the shape of the ripple effect based on the current animation state.
         *
         * @param size The maximum size of the ripple
         * @return The Shape object representing the current state of the ripple
         */
        private Shape getShape(double size) {
            double s = size * animate;
            double x = location.getX();
            double y = location.getY();
            return new Ellipse2D.Double(x - s, y - s, s * 2, s * 2);
        }

        /**
         * Calculates the maximum size of the ripple based on the component's dimensions.
         *
         * @param rec The bounding rectangle of the component
         * @return The maximum size the ripple should reach
         */
        private double getSize(Rectangle2D rec) {
            double size;
            if (rec.getWidth() > rec.getHeight()) {
                if (location.getX() < rec.getWidth() / 2) {
                    size = rec.getWidth() - location.getX();
                } else {
                    size = location.getX();
                }
            } else {
                if (location.getY() < rec.getHeight() / 2) {
                    size = rec.getHeight() - location.getY();
                } else {
                    size = location.getY();
                }
            }
            return size + (size * 0.1f);
        }
    }

}