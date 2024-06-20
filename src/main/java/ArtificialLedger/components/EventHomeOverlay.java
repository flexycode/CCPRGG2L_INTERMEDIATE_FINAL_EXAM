package ArtificialLedger.components;

/**
 * The EventHomeOverlay interface defines a contract for handling changes
 * in the home overlay of the ArtificialLedger application.

 * This interface is typically used to notify listeners about changes
 * in the state or selection of home overlay components, such as
 * navigation items, tabs, or panels in the main view of the application.

 * Implementers of this interface can respond to these changes and
 * perform necessary actions, such as updating the UI, loading different
 * content, or triggering specific behaviors based on the selected index.

 * Usage:
 * 1. Implement this interface in classes that need to respond to home overlay changes.
 * 2. Register instances of implementing classes as listeners to the home overlay component.
 * 3. The home overlay component should call the onChanged method when relevant changes occur.
 */
public interface EventHomeOverlay {

    /**
     * Called when a change occurs in the home overlay.
     *
     * @param index The index representing the new state or selection in the home overlay.
     *              This could represent things like:
     *              - The index of a newly selected tab or panel
     *              - The index of a navigation item that was clicked
     *              - A state change identifier for the home overlay

     * Implementers should use this method to define the behavior that should occur
     * when the specified change happens in the home overlay.
     */
    void onChanged(int index);
}