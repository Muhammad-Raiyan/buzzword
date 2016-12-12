package ui;


import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ishmam on 12/11/2016.
 *
 * @author ishmam
 */
public class HelpPane {
    TextArea textArea;
    ScrollPane scrollPane;
    HelpPane(){
        textArea = new TextArea();
        textArea.setStyle("-fx-background-color: gray");
        readDcox();
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(600, 400);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textArea);

        scrollPane.setStyle("-fx-background-color: gray");
    }

    public void readDcox(){
        try(BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ishmam\\Documents\\Programming\\CSE219\\BuzzwordProject\\BuzzwordGame\\resources\\properties\\help.txt"))) {
            String line;
            while ((line = br.readLine()) != null){
                textArea.appendText(line + "\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pane getPane() {
        Pane p = new Pane();
        p.setPadding(new Insets(15));
        p.getChildren().add(scrollPane);
        return p;
    }
}
