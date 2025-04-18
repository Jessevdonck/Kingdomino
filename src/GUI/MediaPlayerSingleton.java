package GUI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaPlayerSingleton {
private static MediaPlayer mediaPlayerBgMusic;
private static MediaPlayer mediaPlayerSoundFX;

private MediaPlayerSingleton() {}

    public static MediaPlayer getInstanceBgMusic() {
    if (mediaPlayerBgMusic == null) {
        String muziekPath = "/sounds/bg_music.mp3";
        Media bgMusic = new Media(MediaPlayerSingleton.class.getResource(muziekPath).toString());
        mediaPlayerBgMusic = new MediaPlayer(bgMusic);

        mediaPlayerBgMusic.setVolume(0.03); // normaal 0.03

        mediaPlayerBgMusic.setOnEndOfMedia(() -> mediaPlayerBgMusic.seek(Duration.ZERO));
    }

    return mediaPlayerBgMusic;
    }

    public static MediaPlayer getInstanceSoundFX() {

        String muziekPath = "/sounds/buttonClick.mp3";
        Media bgMusic = new Media(MediaPlayerSingleton.class.getResource(muziekPath).toString());
        mediaPlayerSoundFX = new MediaPlayer(bgMusic);

        mediaPlayerSoundFX.setVolume(0.05);

        return mediaPlayerSoundFX;
    }
}
