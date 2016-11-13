package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import state.ButtonState;
import ui.JFLAGScene;

import java.util.ArrayList;

/**
 * Created by ishmam on 10/31/2016.
 */
public class LevelPane extends JFLAGScene {

    private String mode;
    private VBox primaryPane;
    private GridPane levelGrid;
    private ArrayList<Button> buttons;

    public LevelPane(){
        this("Dictionary");
    }

    public LevelPane(String mode){
        this.mode = mode;
        layout();
        initializeStyle();
    }

    @Override
    public void layout() {
        Region seperator = new Region();
        seperator.setPrefHeight(50);

        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label(mode);
        topPane.getChildren().add(title);

        levelGrid = new GridPane();
        levelGrid.setId("grid");
        //levelGrid.setAlignment(Pos.CENTER);
        //levelGrid.setHgap(10);
        buttons = new ArrayList<>(8);
        int count = 1;
        for(int i = 1; i< 3; i++){
            for(int j = 1; j< 5; j++){
                String l = Integer.toString(count++);
                Button button = new Button(l);
                button.setShape(new Circle(20));
                button.setDisable(true);
                buttons.add(button);
                levelGrid.add(button, j-1, i-1);
            }
        }
        buttons.get(0).setDisable(false);

        primaryPane = new VBox(10);
        primaryPane.setId("levelPane");
        primaryPane.getChildren().addAll(seperator, topPane, levelGrid);
    }

    @Override
    public Scene getScene() {
        return null;
    }

    @Override
    public void initializeHandlers() {

    }

    @Override
    public void initializeStyle() {
        primaryPane.getStylesheets().add("gui/LevelPaneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {

    }

    public VBox getPrimaryPane(){
        return primaryPane;
    }
}
