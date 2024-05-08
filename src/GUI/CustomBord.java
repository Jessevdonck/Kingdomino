package GUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomBord {

    private int sizeBoard = 390;
    private int aantalVeldenPerRij = 5;
    private int sizeSquare = sizeBoard / aantalVeldenPerRij;

    public void maakBord(AnchorPane pane)
    {
        for (int i = 0 ; i < sizeBoard; i += sizeSquare)
        {
            for (int j = 0 ; j < sizeBoard ; j += sizeSquare)
            {
                Rectangle r = new Rectangle(i, j, sizeSquare, sizeSquare);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                pane.getChildren().add(r);
            }
        }
    }
}
