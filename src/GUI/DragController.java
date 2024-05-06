package GUI;

import javafx.fxml.FXML;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DragController {
    
    @FXML
    private ImageView imageView;

    @FXML
    void imageViewDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

        event.consume();
    }

    @FXML
    void imageViewDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if(dragboard.hasImage() || dragboard.hasFiles()){
            event.acceptTransferModes(TransferMode.COPY);
        }

        event.consume();
    }

}
