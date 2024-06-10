package ArtificialLedger.forms;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Home extends JPanel {

    private List<ModelLocation> locations;
    private int index = 0;
    private HomeOverlay homeOverlay;

    private MediaPlayerFactory factory;
    private EmbeddedMediaPlayer mediaPlayer;

    public Home() {
        init();
        testData();
    }

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

    private void testData() {
        locations = new ArrayList<>();
        locations.add(new ModelLocation("Artificial Ledger Technology\nBank",
                "Artificial Ledger Technology Bank is a online banking system powered by Web3 / Blockchain technology. Our goal is to explore the potential of decentralized finance and enhance the security and transparency of the banking system. Experience the ease of sending money to anyone, whether it's to other ALT accounts for free, or to other banks and wallets via InstaPay..",
                "video/video 1.mp4"));

        locations.add(new ModelLocation("Crypto Wallet\nBlockchain",
                "Buy, store, send and swap tokens. Available as a decentralized application and as a mobile app, ALTBank equips you with a key vault, secure login, token wallet and token exchange everything you need to manage your digital assets.",
                "video/video 2.mp4"));

        locations.add(new ModelLocation("Bank From Anywhere\nRule your Business",
                "Send money, deposit checks, pay bills, and manage accounts with Artificial Ledger Bank Online. Enjoy endless rewards with the new ALTBank Go Rewards Visa Credit Card. Credit to thousands of employees, open employee payroll accounts, deposit business checks, pay taxes – it’s going to be legendary",
                "video/video 3.mp4"));
    }

    public void initOverlay(JFrame frame) {
        homeOverlay = new HomeOverlay(frame, locations);
        homeOverlay.getOverlay().setEventHomeOverlay(index1 -> {
            play(index1);
        });
        mediaPlayer.overlay().set(homeOverlay);
        mediaPlayer.overlay().enable(true);
    }

    public void play(int index) {
        this.index = index;
        ModelLocation location = locations.get(index);
        if (mediaPlayer.status().isPlaying()) {
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play(location.getVideoPath());
        mediaPlayer.controls().play();
        homeOverlay.getOverlay().setIndex(index);
    }

    public void stop() {
        mediaPlayer.controls().stop();
        mediaPlayer.release();
        factory.release();
    }
}