package ArtificialLedger.forms;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Home page of the ArtificialLedger application.
 * It provides a user interface that includes video playback functionality
 * and information about different locations or features of the application.

 * Key features:
 * 1. Utilizes the VLCJ library for video playback.
 * 2. Manages a list of ModelLocation objects representing different app features.
 * 3. Implements a custom overlay for displaying information alongside videos.
 * 4. Provides methods for initializing, playing, and stopping video content.

 * This class serves as the main landing page for users, offering an
 * interactive and informative introduction to the application's features.
 */

public class Home extends JPanel {

    private List<ModelLocation> locations;
    private HomeOverlay homeOverlay;

    private MediaPlayerFactory factory;
    private EmbeddedMediaPlayer mediaPlayer;

    /**
     * Constructor for the Home class.
     * Initializes the component and populates it with test data.
     */
    public Home() {
        init();
        testData();
    }

    /**
     * Initializes the media player and sets up the video playback component.
     * This method creates the necessary objects for video playback and sets up
     * event listeners for seamless looping of videos.
     */
    private void init() {
        factory = new MediaPlayerFactory();
        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
        Canvas canvas = new Canvas();
        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                if (newTime >= mediaPlayer.status().length() - 1000) {
                    mediaPlayer.controls().setPosition(0);
                }
            }
        });
        setLayout(new BorderLayout());
        add(canvas);
    }

    /**
     * Populates the locations list with test data.
     * This method creates ModelLocation objects representing different features
     * or aspects of the ArtificialLedger application, including titles, descriptions,
     * and associated video paths.
     */
    private void testData() {
        locations = new ArrayList<>();
        locations.add(new ModelLocation("Artificial Ledger Technology\nBank",
                "Artificial Ledger Technology Bank is a online banking system powered by Web3 / Blockchain technology. Our goal is to explore the potential of decentralized finance and enhance the security and transparency of the banking system. Experience the ease of sending money to anyone, whether it's to other ALT accounts for free, or to other banks and wallets via InstaPay..",
                "video/video 1.mp4"));

        locations.add(new ModelLocation("Crypto Wallet\nBlockchain",
                "Buy, store, send and swap tokens. Available as a decentralized application and as a mobile app, ALTBank equips you with a key vault, secure login, token wallet and token exchange everything you need to manage your digital assets.",
                "video/video 2.mp4"));

        locations.add(new ModelLocation("Bank From Anywhere\nRule your Business",
                "Send money, deposit checks, pay bills, and manage accounts with Artificial Ledger Bank Online. Enjoy endless rewards with the new ALTBank Go Rewards Visa Credit Card. Credit to thousands of employees, open employee payroll accounts, deposit business checks, pay taxes – it's going to be legendary",
                "video/video 3.mp4"));
    }

    /**
     * Initializes the overlay component for the home page.
     * This method sets up the HomeOverlay object, which displays information
     * alongside the video playback.
     *
     * @param frame The JFrame in which the Home component is displayed
     */
    public void initOverlay(JFrame frame) {
        homeOverlay = new HomeOverlay(frame, locations);
        homeOverlay.getOverlay().setEventHomeOverlay(this::play);
        mediaPlayer.overlay().set(homeOverlay);
        mediaPlayer.overlay().enable(true);
    }

    /**
     * Plays a video corresponding to the specified index in the locations list.
     * This method stops any currently playing video, starts the new video,
     * and updates the overlay to display the corresponding information.
     *
     * @param index The index of the ModelLocation object in the locations list
     */
    public void play(int index) {
        ModelLocation location = locations.get(index);
        if (mediaPlayer.status().isPlaying()) {
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play(location.videoPath());
        mediaPlayer.controls().play();
        homeOverlay.getOverlay().setIndex(index);
    }

    /**
     * Stops the current video playback and releases resources.
     * This method should be called when the Home component is no longer needed
     * to ensure proper cleanup of media player resources.
     */
    public void stop() {
        mediaPlayer.controls().stop();
        mediaPlayer.release();
        factory.release();
    }
}