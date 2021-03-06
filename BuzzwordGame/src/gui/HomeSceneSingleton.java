package gui;

import components.JFLAGWorkspaceComponent;
import gamecontroller.BuzzwordState;
import gamedata.BuzzwordData;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ui.AppMessageDialogSingleton;
import ui.InitialSceneSingleton;

import java.util.Observable;

/**
 * Created by ishmam on 11/11/2016.
 * @author ishmam
 */
public class HomeSceneSingleton extends Observable{

    private final static int minWords = 20;
    private Workspace workspace;
    private static HomeSceneSingleton homeSceneSingleton;

    private static Scene primaryScene;
    private BorderPane pane;
    private GridPane buttonGrid;
    private Button startPlaying, selectLevel, home, user, restartGame, nextLevel;
    private ChoiceBox<String> selectMode;

    private BuzzwordPane gamePane;
    private UserPane userPane;
    private LevelPane lp;
    private VBox rightBar, leftBar;
    private Region r;
    private final String userName;

    private String currentMode;
    private int currentLevel;

    public static HomeSceneSingleton getHomeSceneSingleton(){
        return homeSceneSingleton == null? new HomeSceneSingleton() : homeSceneSingleton;
    }

    public HomeSceneSingleton(){
        userName = "USER";
        layout();
        initializeHandlers();
        initializeStyle();
    }

    public HomeSceneSingleton(Workspace workspace){
        this.workspace = workspace;
        this.userName = workspace.getUserName();
        layout();
        initializeHandlers();
        initializeStyle();
    }

    private void layout() {
        pane = new BorderPane();
        pane.setPrefSize(800, 600);

        leftBar = new VBox(10);
        leftBar.setId("left-bar");

        Region filler = new Region();
        filler.setPrefSize(leftBar.getPrefWidth(), 120);

        startPlaying = new Button("New Game");
        restartGame = new Button("Restart Level");
        selectLevel = new Button("Select Level");
        home = new Button("Home");
        nextLevel = new Button("Next Level");
        user = new Button(userName);
        selectMode = new ChoiceBox<>();
        selectMode.getStylesheets().add("css/ChoiceBoxStyle.css");
        selectMode.setTooltip(new Tooltip("Select Game Mode"));
        selectMode.setValue("Dictionary");
        selectMode.getItems().addAll("Dictionary", "Famous", "Places", "Science");


        leftBar.getChildren().addAll(filler, startPlaying, selectMode, selectLevel, home, user);

        rightBar = new VBox();
        rightBar.setId("right-bar");

        BorderPane topPane = InitialSceneSingleton.getInitialSceneSingleton().createTopPane();
        buttonGrid = new GridPane();
        buildGrid();
        r = new Region();
        r.setPrefHeight(150);
        VBox rightPane = new VBox(r, buttonGrid);
        rightPane.setAlignment(Pos.CENTER);
        rightBar.getChildren().addAll(topPane, rightPane);

        pane.setCenter(rightBar);
        pane.setLeft(leftBar);
        primaryScene = new Scene(pane);
    }

    private void initializeHandlers() {
        startPlaying.setOnAction(event -> {
            workspace.gameState = BuzzwordState.START;
            setChanged();
            notifyObservers();
        });
        restartGame.setOnAction(event -> {
            restartGame();
        });
        selectLevel.setOnAction(event -> {
            if(gamePane!=null) gamePane.stopTimer();
            levelSelection();
        });

        selectMode.setOnAction(event -> levelSelection());
        home.setOnAction(event -> {
            if(gamePane!=null) gamePane.stopTimer();
            rightBar.getChildren().set(1, new VBox(r, buttonGrid));
            switchToStartButton();
        });
        user.setOnAction(event -> {
            if(gamePane!=null) gamePane.stopTimer();
            userPane = new UserPane(workspace);
            rightBar.getChildren().set(1, userPane.getPrimaryPane());
        });

        nextLevel.setOnAction(event -> {
            if(gamePane!=null) gamePane.stopTimer();
            startGame(++currentLevel);
        });


        try {
            primaryScene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN),
                    () -> startPlaying.fire());
            primaryScene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN),
                    () -> restartGame.fire());
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    public void levelSelection() {
        lp = new LevelPane(selectMode.getValue(), workspace.getUnlockedLevels(selectMode.getValue()));
        switchToStartButton();
        rightBar.getChildren().set(1, lp.getPrimaryPane());
    }

    public void startGame(){
        startGame(lp.getSelectedLevel());
    }

    public void startGame(int level){
        do {
            gamePane = new BuzzwordPane(selectMode.getValue(), level);
        }while(gamePane.getSolution().size()<minWords);
        currentLevel = level;
        currentMode = selectMode.getValue();
        rightBar.getChildren().set(1, gamePane.getPrimaryPane());
    }

    public void startGame(BuzzwordData data) {
        currentLevel = data.getCurrentLevel();
        currentMode = data.getCurrentMode();
        do {
            gamePane = new BuzzwordPane(data.getCurrentMode(), data.getCurrentLevel());
        }while(gamePane.getSolution().size()<minWords);
        rightBar.getChildren().set(1, gamePane.getPrimaryPane());
    }

    public void restartGame(){
        do {
            gamePane = new BuzzwordPane(currentMode, currentLevel);
        }
        while(gamePane.getSolution().size()<minWords);
        rightBar.getChildren().set(1, gamePane.getPrimaryPane());
    }

    public VBox getLeftBar(){
        return leftBar;
    }

    public Button getStartPlaying() {
        return startPlaying;
    }

    public Button getRestartGame() {
        return restartGame;
    }

    public void switchToRestartButton(){
        if(leftBar.getChildren().get(1) == startPlaying) leftBar.getChildren().set(1, restartGame);
    }

    public void switchToStartButton(){
        if(leftBar.getChildren().get(1) == restartGame) leftBar.getChildren().set(1, startPlaying);
    }

    public int getSelectedLevel(){
        return lp.getSelectedLevel();
    }

    public String getSelectedMode(){
        return selectMode.getValue();
    }

    private void initializeStyle() {
        primaryScene.getStylesheets().add("css/SceneStyle.css");
    }

    public void init(JFLAGWorkspaceComponent workspace) {
        this.workspace = (Workspace) workspace;
        homeSceneSingleton = new HomeSceneSingleton(this.workspace);
        homeSceneSingleton.addObserver(((Workspace) workspace).getBuzzwordObserver());
    }

    private void buildGrid() {
        buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setVgap(20);
        buttonGrid.setHgap(20);

        String s = "BUZZWORD";
        int pos = 0;
        for(int i = 0; i< 4; i++){
            for(int j = 0; j<4; j++){

                Circle gameButton = new Circle(20);
                gameButton.setFill(Color.DARKSLATEGRAY);
                Text t = new Text();
                if(pos < s.length()) t.setText(Character.toString(s.charAt(pos)));
                pos++;
                t.setFill(Color.WHITE);
                StackPane st = new StackPane(gameButton, t);
                buttonGrid.add(st, i, j);
            }
        }
    }


    public GridPane getButtonGrid(){
        return buttonGrid;
    }
    //@Override
    public Scene getScene() {
        return primaryScene;
    }

    public void pauseGame() {
        if(gamePane!= null)
            gamePane.pauseTime();
    }

    public void playGame() {
        if(gamePane!= null)
            gamePane.playGame();
    }

    public Button getNextLevelButton() {
        return nextLevel;
    }

    public void saveProgress(int levelScore, int level, String mode) {
        BuzzwordData gameData = workspace.getBuzzwordData();
        int oldScore = gameData.getProgress().get(mode).get(level-1);
        if(oldScore != 0 && levelScore > oldScore){
            Platform.runLater(() -> {
                AppMessageDialogSingleton appMessageDialogSingleton = AppMessageDialogSingleton.getSingleton();
                appMessageDialogSingleton.setMessageLabel( levelScore + " is your highest score.");
                appMessageDialogSingleton.showAndWait();
            });

        }
        gameData.setLevelScore(levelScore, level, mode);
        workspace.gameState = BuzzwordState.SAVE;
        setChanged();
        notifyObservers();
    }

    public int getScore(){
        return gamePane.getScore();
    }
}
