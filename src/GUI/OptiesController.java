package GUI;

import domein.DomeinController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class OptiesController implements Initializable
    {
    @FXML Slider volumeSliderBgMusic;
    @FXML Slider volumeSliderSoundFX;
    @FXML Label volumeTitel;
    @FXML Label achtergrondmuziekLabel;
    @FXML Label geluidseffectenLabel;
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
            laadLanguage();

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
                    MediaPlayerSingleton.getInstanceSoundFX().setVolume(volumeSliderSoundFX.getValue() / 100);
                }
            });
        }

    public void laadLanguage()
        {
        String gekozenTaal = tc.getLanguage();
        Locale locale = new Locale(gekozenTaal);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);
        volumeTitel.setText(bundle.getString("volume"));
        achtergrondmuziekLabel.setText(bundle.getString("achtergrondmuziek"));
        geluidseffectenLabel.setText(bundle.getString("geluidseffecten"));
        }

        public void switchToHomescreen(MouseEvent event) throws IOException
            {
                ssc.switchToHomescreen(event,this.dc, tc);
            }
    }
