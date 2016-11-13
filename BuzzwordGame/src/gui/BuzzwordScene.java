package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import state.ButtonState;
import ui.JFLAGScene;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordScene extends JFLAGScene{

    private String mode;
    private BorderPane primaryPane;
    private HBox topPane;
    private VBox rightPane;
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
        topLayout();
        rightLayout();
        play = new Button("Play");
        primaryPane = new BorderPane();
        primaryPane.setTop(topPane);
        primaryPane.setCenter(play);
        primaryPane.setRight(rightPane);
    }

    public void topLayout(){
        topPane = new HBox();
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
    }

    public void rightLayout(){
        rightPane = new VBox(10);
        rightPane.setId("rightPane");
        Label currentGuess = new Label("B U ");

        /*TableView guessedWords = new TableView();
        guessedWords.setId("table");
        guessedWords.setEditable(false);
        TableColumn word = new TableColumn();
        word.setPrefWidth(120);
        TableColumn score = new TableColumn();
        guessedWords.getColumns().addAll(word, score);*/

        HBox progress = new HBox();
        progress.setId("progressBox");
        progress.setMinWidth(200);

        BorderPane wordProgress = new BorderPane();
        wordProgress.setMinWidth(120);
        wordProgress.setMinHeight(300);
        Label totalScoreLabel = new Label("TOTAL SCORE: ");
        wordProgress.setBottom(totalScoreLabel);

        BorderPane scoreProgress = new BorderPane();
        scoreProgress.setMinWidth(80);
        scoreProgress.setMinHeight(300);
        Label totalScore = new Label("30");
        scoreProgress.setBottom(totalScore);

        progress.getChildren().addAll(wordProgress, scoreProgress);

        VBox targetBox = new VBox(10);
        targetBox.setId("targetBox");
        Label target = new Label("Target Score: ");
        //target.setMinWidth(150);
        Label points = new Label("75");
        points.setMinWidth(80);
        targetBox.getChildren().addAll(target, points);

        rightPane.getChildren().addAll(currentGuess, progress, targetBox);
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
