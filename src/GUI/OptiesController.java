package GUI;

import domein.DomeinController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class OptiesController implements Initializable
    {
    @FXML Slider volumeSlider;
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
            volumeSlider.setValue(MediaPlayerSingleton.getInstance().getVolume() * 100);
            volumeSlider.valueProperty().addListener(new InvalidationListener()
                {
                @Override
                public void invalidated(Observable observable)
                    {
                        MediaPlayerSingleton.getInstance().setVolume(volumeSlider.getValue() / 100);
                    }
                });
        }
    }
