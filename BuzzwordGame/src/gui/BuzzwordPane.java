package gui;

import gamedata.Populate;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import state.ButtonState;
import ui.JFLAGScene;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordPane extends JFLAGScene{

    private String mode;
    private int level;
    private BorderPane primaryPane;
    private BorderPane topPane;
    private VBox rightPane, centerPane;
    private Button play, pause;
    private GridPane buttonGrid;
    public StackPane gridStack;
    private Timeline timeLine;
    private ArrayList<Button> buttonList;
    private int sec = 10;
    private Label levelLabel;
    private SimpleIntegerProperty secProperty;
    private ArrayList<Line> lineList;
    private IntegerProperty targetScore;

    public BuzzwordPane() {
        this("Dictionary Words", 1);
    }

    public BuzzwordPane(String mode, int level) {
        this.mode = mode;
        this.level = (level);
        this.levelLabel = new Label("LEVEL " + level);
        buttonList = new ArrayList<>();
        lineList = new ArrayList<>();
        layout();
        initializeHandlers();
        initializeStyle();
    }

    @Override
    public void layout() {
        topLayout();
        centerLayout();
        rightLayout();
        targetScore = new SimpleIntegerProperty();
        primaryPane = new BorderPane();
        primaryPane.setTop(topPane);
        primaryPane.setCenter(centerPane);
        primaryPane.setRight(rightPane);
    }

    public void topLayout(){
        topPane = new BorderPane();
        topPane.setId("topPane");

        Label title = new Label(mode);
        title.setId("title");

        Label remainingTime = new Label("TIME REMAINING: ");
        remainingTime.setStyle("-fx-underline: true; -fx-font-weight: bold");
        Label seconds = new Label();
        secProperty = new SimpleIntegerProperty(sec);
        seconds.textProperty().bind(secProperty.asString());
        HBox box = new HBox(remainingTime, seconds, new Label(" seconds"));
        box.setPadding(new Insets(0));
        //box.setMinWidth(200);
        box.setId("timer");
        Region leftRegion = new Region();
        leftRegion.setPrefWidth(50);

        topPane.setRight(box);
        topPane.setCenter(title);
        topPane.setLeft(leftRegion);
    }

    public void rightLayout(){
        rightPane = new VBox(10);
        rightPane.setId("rightPane");
        Label currentGuess = new Label("B U ");
        currentGuess.setPrefWidth(200);

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
        Label points = new Label(targetScore.getValue().toString());
        points.setMinWidth(80);
        targetBox.getChildren().addAll(target, points);

        rightPane.getChildren().addAll(currentGuess, progress, targetBox);
    }

    private void centerLayout() {
        centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        buildGrid();

        play = new Button("Play");
        Image playImage = new Image(getClass().getClassLoader().getResourceAsStream("images/PlayIcon.png"));
        play.setGraphic(new ImageView(playImage));
        pause = new Button("Pause");
        Image pauseImage = new Image(getClass().getClassLoader().getResourceAsStream("images/PauseIcon.png"));
        pause.setGraphic(new ImageView(pauseImage));

        levelLabel.setStyle("-fx-text-fill: white");
        gridStack = new StackPane();
        gridStack.getChildren().addAll(buttonGrid);
        gridStack.getChildren().addAll(lineList);
        Region r = new Region();
        r.setPrefHeight(30);
        centerPane.getChildren().addAll(gridStack, r, levelLabel, play);
    }

    private void buildGrid() {
        buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setVgap(25);
        buttonGrid.setHgap(25);
        Populate ob = new Populate();
        targetScore= new SimpleIntegerProperty(ob.getTargetScore());
        HashMap<Integer, String> alphabets = ob.getMap();
        int pos = 0;
        for(int i = 0; i< 4; i++){
            for(int j = 0; j<4; j++){
                //Random r = new Random();
                String c = alphabets.get(pos);
                pos++;
                Button gameButton = new Button(c);
                gameButton.setShape(new Circle(70, Color.DARKSLATEGREY));
                gameButton.setPrefSize(40, 40);
                gameButton.setStyle("-fx-padding: 0; -fx-background-insets: 0");
                gameButton.setId("gameButton");
                gameButton.setOnMousePressed(event -> {
                    gameButton.setEffect(new Glow(0.8));
                });
                /*gameButton.setOnDragDetected(event -> {
                    System.out.println("Drag");
                });*/
                setupHandler(gameButton);
                buttonGrid.add(gameButton, i, j);
                buttonList.add(gameButton);
            }
        }
        //buildPath();
        ObservableList<Button> list = FXCollections.observableArrayList(buttonList);
        for (Button btn : list){
            btn.setOnDragDetected(event -> System.out.println("Drag"));
        }
    }

    public void buildPath(){
        buttonList.forEach(node ->{
            buttonList.stream().filter(target -> target != node).forEachOrdered(target -> {
                int diff = Math.abs(buttonList.indexOf(target) - buttonList.indexOf(node));
                if (diff == 1 || diff == 4) link(node, target);
            });
        });
    }

    public void link(Node from, Node to){
        Line l = new Line(from.getLayoutX(), from.getLayoutY(), to.getLayoutX(), to.getLayoutY());
        //System.out.println(from.getLayoutX() + " " + from.getLayoutY() + " " + to.getLayoutX()+ " " + to.getLayoutY());
        lineList.add(l);
    }

    @Override
    public void initializeHandlers() {
        play.setOnAction(event -> {
            centerPane.getChildren().set(3, pause);
            VBox temp = HomeSceneSingleton.getHomeSceneSingleton().getLeftBar();
            HomeSceneSingleton.getHomeSceneSingleton().switchToRestartButton();
            if(timeLine == null) {
                timeLine = new Timeline(new KeyFrame(
                        Duration.seconds(sec),
                        ae -> System.out.println("Hello"),
                        new KeyValue(secProperty, 0)
                ));
            }
            timeLine.play();
        });

        pause.setOnAction(event -> {
            centerPane.getChildren().set(3, play);
            timeLine.pause();
        });
    }

    @Override
    public void initializeStyle() {
        primaryPane.getStylesheets().add("css/GameScene.css");
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

    public String getMode() {
        return mode;
    }

    public int getLevel() {
        return level;
    }

    public void setupHandler(Button upHandler) {

    }

}
