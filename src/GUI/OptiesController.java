package GUI;

import domein.DomeinController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptiesController implements Initializable
    {
    @FXML Slider volumeSliderBgMusic;
    @FXML Slider volumeSliderSoundFX;
    private MediaPlayer mediaPlayer;
    private DomeinController dc;
    private SceneSwitchController ssc;
    private TaalController tc;
    public OptiesController(DomeinController dc, TaalController tc)
        {
        this.dc = dc;
        this.tc = tc;
        this.ssc = new SceneSwitchController(dc);
        this.mediaPlayer = mediaPlayer;
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
        {
            volumeSliderBgMusic.setValue(MediaPlayerSingleton.getInstanceBgMusic().getVolume() * 100);
            volumeSliderBgMusic.valueProperty().addListener(new InvalidationListener()
                {
                @Override
                public void invalidated(Observable observable)
                    {
                        MediaPlayerSingleton.getInstanceBgMusic().setVolume(volumeSliderBgMusic.getValue() / 100);
                    }
                });

            volumeSliderSoundFX.setValue(MediaPlayerSingleton.getInstanceSoundFX().getVolume() * 100);
            volumeSliderSoundFX.valueProperty().addListener(new InvalidationListener()
            {
            @Override
            public void invalidated(Observable observable)
                {
                MediaPlayerSingleton.getInstanceSoundFX().setVolume(volumeSliderBgMusic.getValue() / 100);
                }
            });
        }

        public void switchToHomescreen(MouseEvent event) throws IOException
            {
                ssc.switchToHomescreen(event,this.dc, tc);
            }
    }
