package GUI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerSingleton {
private static MediaPlayer mediaPlayerInstance;

private MediaPlayerSingleton() {}

public static MediaPlayer getInstance() {
if (mediaPlayerInstance == null) {
    String muziekPath = "/sounds/bg_music.mp3";
    Media bgMusic = new Media(MediaPlayerSingleton.class.getResource(muziekPath).toString());
    mediaPlayerInstance = new MediaPlayer(bgMusic);
    mediaPlayerInstance.play();
}
return mediaPlayerInstance;
}
}
