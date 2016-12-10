package gui;

import gamedata.Populate;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
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
    //private StringBuffer dynamicWord;
    private int level;
    private BorderPane primaryPane;
    private BorderPane topPane;
    private VBox rightPane, centerPane;
    private Button play, pause;
    private GridPane buttonGrid;
    public StackPane gridStack;
    public Pane buttonPane;
    private Timeline timeLine;
    private ArrayList<Button> buttonList, draggedPath;
    private int sec = 10;
    private Label levelLabel, currentGuess;
    private SimpleIntegerProperty secProperty;
    //private ArrayList<Line> lineList;
    private IntegerProperty targetScore;
    private Pane linePane;

    public BuzzwordPane() {
        this("Dictionary Words", 1);
    }

    public BuzzwordPane(String mode, int level) {
        this.mode = mode;
        //this.dynamicWord = new StringBuffer();
        this.level = (level);
        this.levelLabel = new Label("LEVEL " + level);
        buttonList = new ArrayList<>();
        draggedPath = new ArrayList<>();
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
        currentGuess = new Label("");
        //currentGuess.textProperty().bind(Bindings.format("%s", dynamicWord));
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
        //gridStack.getChildren().addAll(lineList);
        Region r = new Region();
        r.setPrefHeight(30);
        linePane = new Pane();
        buttonPane = new StackPane();
        buttonPane.getChildren().addAll(linePane, gridStack);
        centerPane.getChildren().addAll(buttonPane, r, levelLabel, play);
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
                String c = alphabets.get(pos);
                pos++;
                Button gameButton = new Button(c);
                gameButton.setShape(new Circle(70, Color.DARKSLATEGREY));
                gameButton.setPrefSize(45, 45);
                //System.out.println(gameButton.getPrefHeight() + " " + gameButton.getPrefWidth());
                gameButton.setStyle("-fx-padding: 0; -fx-background-insets: 0");
                //gameButton.setId("gameButton");
                setupHandler(gameButton);
                gameButton.setVisible(false);
                buttonGrid.add(gameButton, i, j);
                buttonList.add(gameButton);
            }
        }
    }


    /*public void link(Node from, Node to){
        Line l = new Line(from.getLayoutX(), from.getLayoutY(), to.getLayoutX(), to.getLayoutY());
        //System.out.println(from.getLayoutX() + " " + from.getLayoutY() + " " + to.getLayoutX()+ " " + to.getLayoutY());
        lineList.add(l);
    }*/

    @Override
    public void initializeHandlers() {
        play.setOnAction(event -> {
            centerPane.getChildren().set(3, pause);
            VBox temp = HomeSceneSingleton.getHomeSceneSingleton().getLeftBar();
            HomeSceneSingleton.getHomeSceneSingleton().switchToRestartButton();
            if(timeLine == null) {
                timeLine = new Timeline(new KeyFrame(
                        Duration.seconds(sec),
                        //ae -> System.out.println("Hello"),
                        new KeyValue(secProperty, 0)
                ));
            }
           /* timeLine.play();
            buttonList.forEach(node ->{
                if(!node.isVisible())node.setVisible(true);
            });*/
           playGame();
        });

        pause.setOnAction(event -> {
            centerPane.getChildren().set(3, play);
            /*timeLine.pause();
            buttonList.forEach(node ->{
                if(node.isVisible())node.setVisible(false);
            });*/
            pauseTime();
        });
        /*buttonGrid.setOnDragDetected(event -> {
            System.out.println("Hello");
        });*/
        /*for(Button btn : buttonList){
            btn.setOnMouseDragged(event -> {
                System.out.println(event.getX());
            });
        }
        */

        for(int i=0; i<buttonList.size(); i++){
            Effect dragShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.YELLOW, 10, .8, 0, 0);
            Button btn = buttonList.get(i);
            final int index = i;
            btn.setOnDragDetected(event -> {
                btn.startFullDrag();
                //btn.setStyle("dropshadow( three-pass-box , yellow , 10, 0.8 , 0 , 0 )");
                dynamicDisable(btn, index);
                btn.setEffect(dragShadow);
                addToPath(btn);

            });

            btn.setOnMouseDragged(event -> btn.setEffect(dragShadow));
            btn.setOnMouseDragOver(event -> {
                    btn.setEffect(dragShadow);
                    //btn.setStyle("-fx-background-color: BLUE");
                    dynamicDisable(btn, index);
                    //btn.setStyle("dropshadow( three-pass-box , yellow , 10, 0.8 , 0 , 0 )");

                    //btn.setEffect(dragShadow);
                    addToPath(btn);
            });

            btn.setOnMouseReleased(event -> {
                buttonList.forEach(node -> {
                    node.setStyle(null);
                    node.setEffect(null);
                    node.setDisable(false);
                });
                linePane.getChildren().clear();
                currentGuess.setText("");
                draggedPath.clear();
            });
        }
    }

    private void dynamicDisable(Button btn, int index) {
        if(draggedPath.contains(btn)) {
            return;
        }
        int neighbours[][] = new int[][]{
                {0, 1, 4, 5},                           // 0
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 1
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 2
                {-1, 0, 3, 4},                          // 3
                {-4, -3, 0, +1, +4, +5},                // 4
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 5
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 6
                {-5, -4, -1, 0, +3, +4},                // 7
                {-4, -3, 0, +1, +4, +5},                // 8
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 9
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 10
                {-5, -4, -1, 0, +3, +4},                // 11
                {-4, -3, 0, +1},                        // 12
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 13
                {-5, -1, +3, -4, 0, +4, -3, +1, +5},    // 14
                {-5, -4, -1, 0}                         // 15

        };
        for(int i=0; i<buttonList.size(); i++){
            Button current = buttonList.get(i);
            if(!draggedPath.contains(current)) current.setDisable(true);
            for(int k=0; k<neighbours[i].length; k++){
                if((index-i) == neighbours[i][k])
                    current.setDisable(false);
            }
        }
    }

    private synchronized void addToPath(Button btn) {
        Effect dragShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.YELLOW, 10, .8, 0, 0);
        if(!draggedPath.isEmpty() && !draggedPath.contains(btn)) {
            draggedPath.add(btn);
            currentGuess.setText(currentGuess.getText()+btn.getText());
            Button startTarget = draggedPath.get(draggedPath.size()-2);
            Button endTarget = draggedPath.get(draggedPath.size()-1);

            double offset = 20;
            Line link = new Line(startTarget.getLayoutX() + offset, startTarget.getLayoutY() + offset, endTarget.getLayoutX() + offset, endTarget.getLayoutY() + offset);
            link.setEffect(dragShadow);
            linePane.getChildren().add(link);
        }
        else if(!draggedPath.contains(btn)) {
            draggedPath.add(btn);
            currentGuess.setText(currentGuess.getText()+btn.getText());
        }
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

    public void pauseTime() {
        if(timeLine != null) {
            timeLine.pause();
            buttonList.forEach(node ->{
                if(node.isVisible())node.setVisible(false);
            });
        }
    }

    public void playGame() {
        if(timeLine!=null) {
            timeLine.play();
            buttonList.forEach(node ->{
                if(!node.isVisible())node.setVisible(true);
            });
        }
    }
}
