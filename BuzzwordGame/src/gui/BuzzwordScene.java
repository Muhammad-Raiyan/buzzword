package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Collation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import state.ButtonState;
import ui.JFLAGScene;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordScene extends JFLAGScene{

    private String mode;
    private BorderPane primaryPane;
    private HBox topPane;
    private VBox rightPane, centerPane;
    private Button play;
    private GridPane buttonGrid;
    public StackPane gridStack;
    private Timeline timeLine;
    private ArrayList<Button> buttonList;
    private int sec = 10;
    private SimpleIntegerProperty secProperty;
    private ArrayList<Line> lineList;
    public BuzzwordScene() {
        this("Dictionary Words");
    }

    public BuzzwordScene(String mode) {
        this.mode = mode;
        buttonList = new ArrayList<>();
        lineList = new ArrayList<>();
        layout();
        initializeHandlers();
        initializeStyle();
    }

    @Override
    public void layout() {
        topLayout();
        rightLayout();
        centerLayout();
        primaryPane = new BorderPane();
        primaryPane.setTop(topPane);
        primaryPane.setCenter(centerPane);
        primaryPane.setRight(rightPane);
    }

    private void centerLayout() {
        centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        buildGrid();
        play = new Button("Play");
        Label level = new Label("Level 1");
        gridStack = new StackPane();
        gridStack.getChildren().addAll(buttonGrid);
        gridStack.getChildren().addAll(lineList);
        centerPane.getChildren().addAll(gridStack, level, play);
        centerPane.getChildren().addAll(lineList);
    }

    private void buildGrid() {
        buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setVgap(30);
        buttonGrid.setHgap(30);

        for(int i = 0; i< 4; i++){
            for(int j = 0; j<4; j++){
                Button gameButton = new Button("a");
                gameButton.setId("gameButton");
                gameButton.setShape(new Circle(30, Color.DARKSLATEGREY));
                gameButton.setMinSize(30, 30);
                gameButton.setOnMousePressed(event -> {
                    gameButton.setEffect(new Glow(0.8));
                });
                buttonGrid.add(gameButton, i, j);
                buttonList.add(gameButton);
            }
        }
        buildPath();
    }

    public void buildPath(){
        buttonList.forEach(node ->{
            for(Button target : buttonList){
                if(target != node){
                    int diff = Math.abs(buttonList.indexOf(target)-buttonList.indexOf(node));
                    if(diff == 1 || diff == 4) link(node, target);
                }
            }
        });
    }

    public void link(Node from, Node to){
        Line l = new Line(from.getLayoutX(), from.getLayoutY(), to.getLayoutX(), to.getLayoutY());
        System.out.println(from.getLayoutX() + " " + from.getLayoutY() + " " + to.getLayoutX()+ " " + to.getLayoutY());
        lineList.add(l);
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

        HBox progress = new HBox();
        progress.setId("progressBox");
        progress.setMinWidth(200);

        BorderPane wordProgress = new BorderPane();
        wordProgress.setMinWidth(120);
        wordProgress.setMinHeight(300);
        Label totalScoreLabel = new Label("TOTAL SCORE: ");
        totalScoreLabel.setMinWidth(wordProgress.getPrefWidth());
        wordProgress.setBottom(totalScoreLabel);

        BorderPane scoreProgress = new BorderPane();
        scoreProgress.setMinWidth(80);
        scoreProgress.setMinHeight(300);
        Label totalScore = new Label("30");
        scoreProgress.setBottom(totalScore);

        progress.getChildren().addAll(wordProgress, scoreProgress);

        VBox targetBox = new VBox();
        targetBox.setId("targetBox");
        Label target = new Label("TARGET: ");
        target.setStyle("-fx-underline: true");
        //target.setMinWidth(150);
        Label points = new Label("75 Points");
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
