package gui;

import gamedata.Populate;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import state.ButtonState;
import trie.BuzzwordSolver;
import ui.JFLAGScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by ishmam on 10/30/2016.
 * @author ishmam
 */
public class BuzzwordPane extends JFLAGScene{

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

    private String mode;
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
    private ArrayList<ArrayList<Integer>> pressedPath;
    private int sec, baseTime = 14500;
    private IntegerProperty sumOfScore;
    private Label levelLabel, currentGuess;
    private SimpleIntegerProperty secProperty;
    private IntegerProperty targetScore;
    private Pane linePane;
    private ObservableList<WordPair> tableData;
    private HashSet<String> guessedWords;
    private ArrayList<String> solution;
    private Populate populate;

    public BuzzwordPane() {
        this("Dictionary Words", 1);
    }

    public BuzzwordPane(String mode, int level) {
        this.mode = mode;
        this.level = (level);
        this.levelLabel = new Label("LEVEL " + level);
        sumOfScore = new SimpleIntegerProperty(0);
        sec = getTime(level);
        buttonList = new ArrayList<>();
        draggedPath = new ArrayList<>();
        pressedPath = new ArrayList<>();
        guessedWords = new HashSet<>();
        tableData = FXCollections.observableArrayList();
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
        currentGuess.setPrefWidth(200);

        TableView scoreTable = new TableView();
        scoreTable.setPrefWidth(200);
        scoreTable.setEditable(false);

        TableColumn wordsColumn = new TableColumn("Guessed words");
        wordsColumn.setPrefWidth(120);
        wordsColumn.setCellValueFactory(
                new PropertyValueFactory<WordPair, String>("word")
        );
        TableColumn scoreColumn = new TableColumn("Points");
        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<WordPair, Integer>( "score")
        );

        scoreTable.setItems(tableData);
        scoreTable.getColumns().addAll(wordsColumn, scoreColumn);

        HBox sumBox = new HBox();
        sumBox.setPrefWidth(200);
        Label totalScoreLabel = new Label("Total Score: ");
        Label sumLabel = new Label();
        sumLabel.textProperty().bind(sumOfScore.asString());
        sumBox.getChildren().addAll(totalScoreLabel, sumLabel);

        VBox targetBox = new VBox();
        targetBox.setId("targetBox");
        Label target = new Label("TARGET: ");
        target.setStyle("-fx-underline: true");
        Label points = new Label(targetScore.getValue().toString());
        points.setMinWidth(80);
        targetBox.getChildren().addAll(target, points);

        rightPane.setPadding(new Insets(0));
        rightPane.getChildren().addAll(currentGuess, scoreTable, sumBox, targetBox);
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
        populate = new Populate(mode);

        HashMap<Integer, String> alphabets = populate.getMap();
        int pos = 0;
        for(int i = 0; i< 4; i++){
            for(int j = 0; j<4; j++){
                String c = alphabets.get(pos);
                pos++;
                Button gameButton = new Button(c);
                gameButton.setShape(new Circle(70, Color.DARKSLATEGREY));
                gameButton.setPrefSize(45, 45);
                gameButton.setStyle("-fx-padding: 0; -fx-background-insets: 0");
                //gameButton.setId("gameButton");
                setupHandler(gameButton);
                gameButton.setVisible(false);
                buttonGrid.add(gameButton, i, j);
                buttonList.add(gameButton);
            }
        }

        findSolution(populate);
        targetScore= new SimpleIntegerProperty(calculateTargetScore());
    }

    private void findSolution(Populate populate) {
        BuzzwordSolver buzzwordSolver = new BuzzwordSolver();
        buzzwordSolver.solve(populate);
        solution = buzzwordSolver.getSolution();
    }

    public void showSolution(){
        GridSolutionSingleton gridSolutionSingleton = GridSolutionSingleton.getSingleton();
        gridSolutionSingleton.setSolutionList(solution);
        gridSolutionSingleton.show(mode);
    }

    private int getTime(int level) {
        return baseTime -5*level;
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
                        new KeyValue(secProperty, 0)
                ));

                timeLine.setOnFinished(event2 -> {
                    stopBuzzword();
                });
            }
           playGame();
        });

        pause.setOnAction(event -> {
            centerPane.getChildren().set(3, play);
            pauseTime();

        });

        for(int i=0; i<buttonList.size(); i++){
            Effect dragShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.YELLOW, 10, .8, 0, 0);
            Button btn = buttonList.get(i);
            final int index = i;
            btn.setOnDragDetected(event -> {
                btn.startFullDrag();
                dynamicDisable(btn, index);
                btn.setEffect(dragShadow);
                addToPath(btn);

            });

            btn.setOnMouseDragged(event -> btn.setEffect(dragShadow));
            btn.setOnMouseDragOver(event -> {
                    btn.setEffect(dragShadow);
                    dynamicDisable(btn, index);
                    addToPath(btn);
            });

            btn.setOnMouseReleased(event -> {
                clearPath();
            });
        }

        Scene primaryScene = HomeSceneSingleton.getHomeSceneSingleton().getScene();

        primaryScene.setOnKeyPressed(event -> {
            ArrayList<Integer> selectedButtons = new ArrayList<>();
            if(centerPane.getChildren().get(3) == pause){
                if(event.getCode() == KeyCode.ENTER){
                    pressedPath.clear();
                    clearHighlight();
                }
                else {
                    ArrayList<Integer> pos = findButtonPositions(event.getText());
                    if(pos.size()==0) clearHighlight();
                    pressedPath.add(pos);
                    if(pressedPath.size()>1)trim(pressedPath.size());
                }
                //clearHighlight();
                highlight();
                /*for(int i=0; i<buttonList.size(); i++){
                    Effect dragShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.YELLOW, 10, .8, 0, 0);
                    Button btn = buttonList.get(i);
                    if(isPressValid(i) && btn.getText().equals(event.getText())){
                        btn.setEffect(dragShadow);
                    }
                }*/
            }
            //clearPath();


        });
    }

    private void clearHighlight() {
        buttonList.forEach(node -> {
            node.setStyle(null);
            node.setEffect(null);
        });
    }

    private void trim(int cap) {
        if(cap == 1) return;
        ArrayList<Integer> oldPath = pressedPath.get(cap-2);
        ArrayList<Integer> newPath = pressedPath.get(cap-1);
        HashSet<Integer> bad = new HashSet<>();
        for(Integer i : oldPath){
            boolean isSafe = false;
            for(int j=0; j < neighbours[i].length; j++){
                if(newPath.contains(i + neighbours[i][j])){
                    isSafe = true;
                }
            }
            if(!isSafe) bad.add(i);
        }
        bad.forEach(node ->{
            oldPath.remove(node);
        });
        trim(cap-1);
    }

    private void highlight() {
        Effect dragShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.YELLOW, 10, .8, 0, 0);
        for (Button button : buttonList) {
            button.setEffect(null);
        }
        for(int i=0; i<pressedPath.size(); i++){
            for (Integer pos : pressedPath.get(i)){
                buttonList.get(pos).setEffect(dragShadow);
            }
        }
    }

    private ArrayList<Integer> findButtonPositions(String text) {
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i=0; i<buttonList.size(); i++){
            if(buttonList.get(i).getText().equals(text) && isPressValid(i)){
                temp.add(i);
            }
        }
        return temp;
    }

    private boolean isPressValid(int i) {
        if(pressedPath.size() == 0) return true;
        else {
            ArrayList<Integer> previous = pressedPath.get(pressedPath.size()-1);
            for(Integer matchAgainst : previous){
                for(int j = 0; j<neighbours[i].length; j++){
                    if(neighbours[i][j]+i == matchAgainst) return true;
                }
            }

        }
        return false;
    }

    private void clearPath(){
        buttonList.forEach(node -> {
            node.setStyle(null);
            node.setEffect(null);
            node.setDisable(false);
        });
        updateScore();
        linePane.getChildren().clear();
        currentGuess.setText("");
        draggedPath.clear();
    }

    private void stopBuzzword() {
        play.setDisable(true);
        pause.setDisable(true);
        buttonList.forEach(node -> {
            node.setDisable(true);
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showSolution();
            }
        });
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

    private void updateScore() {
        String word = currentGuess.getText();
        if(isValid(word)){
            guessedWords.add(word);
            tableData.add(new WordPair(word, getScore(word)));
            sumOfScore.setValue(sumOfScore.getValue() + getScore(word));
        }
    }

    private int getScore(String word) {
        int base = 10, increment = 5;

        return base + (word.length()-3)*increment;
    }


    private boolean isValid(String word) {
        return word.length()>2 && !guessedWords.contains(word) && populate.getTrie().search(word);
    }

    private void dynamicDisable(Button btn, int index) {
        if(draggedPath.contains(btn)) {
            return;
        }

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

    public ArrayList<String> getSolution() {
        return solution;
    }

    public void reset() {
        sumOfScore = new SimpleIntegerProperty(0);
        buttonList = new ArrayList<>();
        draggedPath = new ArrayList<>();
        guessedWords = new HashSet<>();
        tableData = FXCollections.observableArrayList();
    }

    public int calculateTargetScore() {
        int sum = 0;
        for(String word: solution){
            sum += getScore(word);
        }
        if(sum>1000) sum-=800;
        double[] levelPerc = new double[]{.6, .8, .10, .12, .14, .16, .18, .20};
        return (int) (sum * levelPerc[level]);
    }

    public static class WordPair {
        String word;
        int score;

        public WordPair(String word, int score) {
            this.word = word;
            this.score = score;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

    }
}
