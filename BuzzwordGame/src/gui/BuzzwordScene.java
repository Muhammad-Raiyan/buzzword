package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import state.ButtonState;
import ui.JFLAGScene;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordScene extends JFLAGScene{

    private String mode;
    private BorderPane primaryPane;
    private Button play;
    private Timeline timeLine;
    private int sec = 10;
    private SimpleIntegerProperty secProperty;
    public BuzzwordScene() {
        this("Dictionary Words");
    }

    public BuzzwordScene(String mode) {
        this.mode = mode;
        layout();
        initializeHandlers();
        initializeStyle();
    }

    @Override
    public void layout() {
        HBox topPane = new HBox();
        topPane.setId("topPane");
        topPane.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label(mode);
        title.setId("title");
        Label remainingTime = new Label("TIME REMAINING:");
        remainingTime.setStyle("-fx-underline: true; -fx-font-weight: bold");
        Label seconds = new Label();
        secProperty = new SimpleIntegerProperty(sec);
        seconds.textProperty().bind(secProperty.asString());
        HBox box = new HBox(remainingTime, seconds);
        //box.setPrefWidth(150);
        box.setId("timer");
        Region region = new Region();
        region.setPrefWidth(170);
        topPane.getChildren().addAll(region, title, box);
        play = new Button("Play");
        primaryPane = new BorderPane();
        primaryPane.setTop(topPane);
        primaryPane.setBottom(play);
    }
    @Override
    public void initializeHandlers() {
        play.setOnAction(event -> {
            timeLine = new Timeline(new KeyFrame(
                    Duration.seconds(sec),
                    ae -> System.out.println("Hello"),
                    new KeyValue(secProperty, 0)
            ));
            timeLine.play();
        });
    }

    @Override
    public void initializeStyle() {
        primaryPane.getStylesheets().add("gui/GameScene.css");
    }

    @Override
    public void init(ButtonState buttonState) {

    }

    @Override
    public Scene getScene() {
        return null;
    }

    public BorderPane  getPrimaryPane(){
        return primaryPane;
    }
}
