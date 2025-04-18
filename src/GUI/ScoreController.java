package GUI;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import util.Kleur;
import util.LandschapType;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ScoreController implements Initializable
{

    @FXML private Button quitBtn, menuBtn, playAgainBtn;
    @FXML
    private Label SpelerEen, SpelerTwee, SpelerDrie, SpelerVier;
    private Label[] spelerNaamLabels;

    @FXML
    private Label ScoreGraanSpelerEen, ScoreGraanSpelerTwee, ScoreGraanSpelerDrie, ScoreGraanSpelerVier;
    private Label[] scoreGraanLabels;

    @FXML
    private Label ScoreBosSpelerEen, ScoreBosSpelerTwee, ScoreBosSpelerDrie, ScoreBosSpelerVier;
    private Label[] scoreBosLabels;

    @FXML
    private Label ScoreWaterSpelerEen, ScoreWaterSpelerTwee, ScoreWaterSpelerDrie, ScoreWaterSpelerVier;
    private Label[] scoreWaterLabels;

    @FXML
    private Label ScoreGrasSpelerEen, ScoreGrasSpelerTwee, ScoreGrasSpelerDrie, ScoreGrasSpelerVier;
    private Label[] scoreGrasLabels;

    @FXML
    private Label ScoreModderSpelerEen, ScoreModderSpelerTwee, ScoreModderSpelerDrie, ScoreModderSpelerVier;
    private Label[] scoreModderLabels;

    @FXML
    private Label ScoreKoolmijnSpelerEen, ScoreKoolmijnSpelerTwee, ScoreKoolmijnSpelerDrie, ScoreKoolmijnSpelerVier;
    private Label[] scoreKoolmijnLabels;

    @FXML
    private Label TotaalScoreSpelerEen, TotaalScoreSpelerTwee, TotaalScoreSpelerDrie, TotaalScoreSpelerVier;
    private Label[] totaalScoreLabels;


    private DomeinController dc;
    private TaalController tc;
    private HashMap<Kleur, HashMap<LandschapType, Integer>> scoreMap;
    private SceneSwitchController ssc;


    public ScoreController(DomeinController dc, TaalController tc)
    {
        this.ssc = new SceneSwitchController(dc);
        this.tc = tc;
        this.dc = dc;
        this.scoreMap = new HashMap<Kleur, HashMap<LandschapType, Integer>>();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        laadLanguage();
        spelerNaamLabels = new Label[]{SpelerEen, SpelerTwee, SpelerDrie, SpelerVier};
        scoreGraanLabels = new Label[]{ScoreGraanSpelerEen, ScoreGraanSpelerTwee, ScoreGraanSpelerDrie, ScoreGraanSpelerVier};
        scoreBosLabels = new Label[]{ScoreBosSpelerEen, ScoreBosSpelerTwee, ScoreBosSpelerDrie, ScoreBosSpelerVier};
        scoreGrasLabels = new Label[]{ScoreGrasSpelerEen, ScoreGrasSpelerTwee, ScoreGrasSpelerDrie, ScoreGrasSpelerVier};
        scoreKoolmijnLabels = new Label[]{ScoreKoolmijnSpelerEen, ScoreKoolmijnSpelerTwee, ScoreKoolmijnSpelerDrie, ScoreKoolmijnSpelerVier};
        scoreModderLabels = new Label[]{ScoreModderSpelerEen, ScoreModderSpelerTwee, ScoreModderSpelerDrie, ScoreModderSpelerVier};
        scoreWaterLabels = new Label[]{ScoreWaterSpelerEen, ScoreWaterSpelerTwee, ScoreWaterSpelerDrie, ScoreWaterSpelerVier};
        totaalScoreLabels = new Label[]{TotaalScoreSpelerEen, TotaalScoreSpelerTwee, TotaalScoreSpelerDrie, TotaalScoreSpelerVier};
        setEindScores();
        setScorePlayerNames();
        setScoresGraan();
        setScoresBos();
        setScoresGras();
        setScoresKoolmijn();
        setScoresModder();
        setScoresWater();




    }


    private void setEindScores()
    {
        scoreMap = dc.geefScores();
        System.out.println(scoreMap);
        int index = 0;
        for (Kleur kleur : scoreMap.keySet()) {
            int scoreTotaal = 0;
            for (LandschapType type : LandschapType.values()) {
                if (type != LandschapType.KONING) {
                    System.out.println(scoreMap.get(kleur).toString());
                    scoreTotaal += scoreMap.get(kleur).get(type);
                }
            }
            totaalScoreLabels[index].setText(String.format("%d", scoreTotaal));
            index++;
        }
    }


    private void setScorePlayerNames()
    {
        List<Kleur> kleurenLijst = scoreMap.keySet().stream().toList();
        int index = 0;
        for (Label speler : spelerNaamLabels) {
            System.out.println(index);

            if (index >= dc.getSpelendeSpelers().size()) {
                speler.setText("N/A");
            } else {
                speler.setText(kleurenLijst.get(index).toString());
            }

            index++;
        }
    }

    private void setScoresGraan()
    {
        int index = 0;
        Label[] label = scoreGraanLabels;
        for (Kleur kleur : scoreMap.keySet()) {
            System.out.println(index);
            if (index >= dc.getSpelendeSpelers().size()) {
                label[index].setText("N/A");
            } else {
                label[index].setText(scoreMap.
                        get(kleur).
                        get(LandschapType.GRAAN).
                        toString());
            }
            index++;
        }
    }

    private void setScoresBos()
    {
        int index = 0;
        Label[] label = scoreBosLabels;
        for (Kleur kleur : scoreMap.keySet()) {
            if (index >= dc.getSpelendeSpelers().size()) {
                label[index].setText("N/A");
            } else {
                label[index].setText(scoreMap.
                        get(kleur).
                        get(LandschapType.BOS).
                        toString());
            }
            index++;
        }
    }

    private void setScoresWater()
    {
        int index = 0;
        Label[] label = scoreWaterLabels;
        for (Kleur kleur : scoreMap.keySet()) {
            if (index >= dc.getSpelendeSpelers().size()) {
                label[index].setText("N/A");
            } else {
                label[index].setText(scoreMap.
                        get(kleur).
                        get(LandschapType.WATER).
                        toString());
            }
            index++;
        }
    }

    private void setScoresKoolmijn()
    {
        int index = 0;
        Label[] label = scoreKoolmijnLabels;
        for (Kleur kleur : scoreMap.keySet()) {
            if (index >= dc.getSpelendeSpelers().size()) {
                label[index].setText("N/A");
            } else {
                label[index].setText(scoreMap.
                        get(kleur).
                        get(LandschapType.GOUDMIJN).
                        toString());
            }
            index++;
        }
    }

    private void setScoresGras()
    {
        int index = 0;
        Label[] label = scoreGrasLabels;
        for (Kleur kleur : scoreMap.keySet()) {
            if (index >= dc.getSpelendeSpelers().size()) {
                label[index].setText("N/A");
            } else {
                label[index].setText(scoreMap.
                        get(kleur).
                        get(LandschapType.GRAS).
                        toString());
            }
            index++;
        }
    }

    private void setScoresModder()
    {
        int index = 0;
        Label[] label = scoreModderLabels;
        for (Kleur kleur : scoreMap.keySet()) {
            if (index >= dc.getSpelendeSpelers().size()) {
                label[index].setText("N/A");
            } else {
                label[index].setText(scoreMap.
                        get(kleur).
                        get(LandschapType.MODDER).
                        toString());
            }
            index++;
        }
    }

    /*---------------------------------------------------------------------SCENE SWITCH----------------------------------------------------------------*/
    public void switchToSpeelScene(ActionEvent event) throws IOException
    {
        /*for (int i = 1 ; i <= dc.getSpelendeSpelers().size() - 1 ; i++)
        {
            dc.updateSpeler(dc.vanKleurNaarSpeler(dc.geefWinnaars().get(i)), false);
        }
        dc.updateSpeler(dc.vanKleurNaarSpeler(dc.geefWinnaars().get(0)), true);*/
        dc.getSpelendeSpelers().clear();
        ssc.switchToSpeelScene(event, dc, tc);
    }
    public void switchToHomescreen(MouseEvent event) throws IOException
    {
        /*for (int i = 1 ; i <= dc.getSpelendeSpelers().size() - 1 ; i++)
        {
            dc.updateSpeler(dc.vanKleurNaarSpeler(dc.geefWinnaars().get(i)), false);
        }
        dc.updateSpeler(dc.vanKleurNaarSpeler(dc.geefWinnaars().get(0)), true);*/
        dc.getSpelendeSpelers().clear();
        ssc.switchToHomescreen(event, dc, tc);
    }
    public void afsluiten(ActionEvent event){
        /*for (int i = 1 ; i <= dc.getSpelendeSpelers().size() - 1 ; i++)
        {
            dc.updateSpeler(dc.vanKleurNaarSpeler(dc.geefWinnaars().get(i)), false);
        }
        dc.updateSpeler(dc.vanKleurNaarSpeler(dc.geefWinnaars().get(0)), true);*/
        dc.getSpelendeSpelers().clear();
        ssc.afsluiten(event);
    }

    private void laadLanguage() //Wordt gebruikt wanneer taal nog niet gekozen is
    {
        Locale locale = new Locale(tc.getLanguage());
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);
        quitBtn.setText(bundle.getString("afsluiten"));
        menuBtn.setText(bundle.getString("HoofdMenu"));
        playAgainBtn.setText(bundle.getString("SpeelNogEens"));

    }
}

