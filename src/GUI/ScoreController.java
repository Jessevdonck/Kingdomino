package GUI;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.Kleur;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreController implements Initializable
{


    @FXML
    private Label SpelerEen, SpelerTwee, SpelerDrie, SpelerVier;
    private Label[] spelerNaamLabels;

    @FXML
    private Label ScoreGraanSpelerEen, ScoreGraanSpelerTwee, ScoreGraanSpelerDrie, ScoreGraanSpelerVier;
    private Label[] scoreGraanLabels = new Label[]{ScoreGraanSpelerEen, ScoreGraanSpelerTwee, ScoreGraanSpelerDrie, ScoreGraanSpelerVier};

    @FXML
    private Label ScoreBosSpelerEen, ScoreBosSpelerTwee, ScoreBosSpelerDrie, ScoreBosSpelerVier;
    private Label[] scoreBosLabels = new Label[]{ScoreBosSpelerEen, ScoreBosSpelerTwee, ScoreBosSpelerDrie, ScoreBosSpelerVier};

    @FXML
    private Label ScoreWaterSpelerEen, ScoreWaterSpelerTwee, ScoreWaterSpelerDrie, ScoreWaterSpelerVier;
    private Label[] scoreWaterLabels = new Label[]{ScoreWaterSpelerEen, ScoreWaterSpelerTwee, ScoreWaterSpelerDrie, ScoreWaterSpelerVier};

    @FXML
    private Label ScoreGrasSpelerEen, ScoreGrasSpelerTwee, ScoreGrasSpelerDrie, ScoreGrasSpelerVier;
    private Label[] scoreGrasLabels = new Label[]{ScoreGrasSpelerEen, ScoreGrasSpelerTwee, ScoreGrasSpelerDrie, ScoreGrasSpelerVier};

    @FXML
    private Label ScoreModderSpelerEen, ScoreModderSpelerTwee, ScoreModderSpelerDrie, ScoreModderSpelerVier;
    private Label[] scoreModderLabels = new Label[]{ScoreModderSpelerEen, ScoreModderSpelerTwee, ScoreModderSpelerDrie, ScoreModderSpelerVier};

    @FXML
    private Label ScoreKoolmijnSpelerEen, ScoreKoolmijnSpelerTwee, ScoreKoolmijnSpelerDrie, ScoreKoolmijnSpelerVier;
    private Label[] scoreKoolmijnLabels = new Label[]{ScoreKoolmijnSpelerEen, ScoreKoolmijnSpelerTwee, ScoreKoolmijnSpelerDrie, ScoreKoolmijnSpelerVier};

    @FXML
    private Label TotaalScoreSpelerEen, TotaalScoreSpelerTwee, TotaalScoreSpelerDrie, TotaalScoreSpelerVier;
    private Label[] totaalScoreLabels = new Label[]{TotaalScoreSpelerEen, TotaalScoreSpelerTwee, TotaalScoreSpelerDrie, TotaalScoreSpelerVier};

    private FXML loader;
    private DomeinController dc;
    private TaalController tc;
    public ScoreController(DomeinController dc, TaalController tc) {

        this.tc = tc;
        this.dc = dc;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        spelerNaamLabels = new Label[]{SpelerEen, SpelerTwee,SpelerDrie,SpelerVier};
        setScorePlayerNames();
    }

    private void setScorePlayerNames(){
        List<Kleur> kleurenLijst = dc.getSpelendeSpelers().values().stream().toList();
        int index = 0;
        for (Label speler: spelerNaamLabels) {
            System.out.println(index);
            if(kleurenLijst.get(index) == null){
                speler.setText("N/A");
            } else {
                speler.setText(kleurenLijst.get(index).toString());
            }

            index++;
        }

    }
}

