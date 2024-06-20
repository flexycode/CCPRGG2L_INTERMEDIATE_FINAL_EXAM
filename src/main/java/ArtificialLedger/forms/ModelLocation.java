package ArtificialLedger.forms;

/**
 * This record class represents a model location in the ArtificialLedger application.
 * It encapsulates information about a specific location or feature within the application,
 * including its title, description, and associated video path.

 * Key features:
 * 1. Immutable data structure using Java's record feature.
 * 2. Stores three pieces of information: title, description, and video path.
 * 3. Automatically generates constructor, accessor methods, equals(), hashCode(), and toString().

 * Usage:
 * This record is typically used to represent different sections or features of the application,
 * possibly for navigation purposes or to display information about various functionalities.
 * The video path could be used to associate instructional or promotional videos with each location.
 *
 * @param title The title or name of the location or feature
 * @param description A brief description of the location or feature
 * @param videoPath The file path or URL to a video associated with this location
 */

record ModelLocation(String title, String description, String videoPath) {
    // No additional methods or fields are needed as the record provides
    // all necessary functionality for this simple data structure.
}