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
    private int selectedLevel;
    private ArrayList<Button> buttonArrayList;
    private ArrayList<Integer> unlockedLevels;
    public LevelPane(){
        this("Dictionary", null);
    }

    public LevelPane(String mode, ArrayList<Integer> unlockedLevels){
        this.mode = mode;
        selectedLevel = 1;
        this.unlockedLevels = unlockedLevels;
        layout();
        initializeStyle();
    }


    @Override
    public void layout() {
        Region separator = new Region();
        separator.setPrefHeight(50);

        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label(mode);
        topPane.getChildren().add(title);

        levelGrid = new GridPane();
        levelGrid.setId("grid");
        buttonArrayList = new ArrayList<>(8);
        int count = 1;
        for(int i = 1; i< 3; i++){
            for(int j = 1; j< 5; j++){
                String l = Integer.toString(count++);
                Button button = new Button(l);
                button.setOnAction(event -> {
                    selectedLevel = Integer.parseInt(button.getText());
                    HomeSceneSingleton.getHomeSceneSingleton().startGame();
                });
                button.setShape(new Circle(20));
                button.setDisable(true);
                buttonArrayList.add(button);
                levelGrid.add(button, j-1, i-1);
            }
        }

        for(int i=0; i<unlockedLevels.size(); i++){
            buttonArrayList.get(i).setDisable(false);
        }

       /* buttonArrayList.get(1).setDisable(false);
        buttonArrayList.get(2).setDisable(false);*/

        primaryPane = new VBox(10);
        primaryPane.setId("levelPane");
        primaryPane.getChildren().addAll(separator, topPane, levelGrid);
    }

    public int getSelectedLevel(){
        return selectedLevel;
    }

    public String getMode() {
        return mode;
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
        primaryPane.getStylesheets().add("css/LevelPaneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {

    }

    public VBox getPrimaryPane(){
        return primaryPane;
    }

    public ArrayList<Button> getButtonArrayList() {
        return buttonArrayList;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}
