package gui;

import components.JFLAGWorkspaceComponent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import state.ButtonState;
import ui.InitialSceneSingleton;

/**
 * Created by ishmam on 11/11/2016.
 */
public class HomeSceneSingleton {

    private String sceneID;
    private Workspace workspace;
    public static HomeSceneSingleton homeSceneSingleton;

    private static Scene primaryScene;
    private BorderPane pane;
    private GridPane buttonGrid;
    private Button startPlaying, selectLevel, home, user;
    private ChoiceBox<String> selectMode;

    private ButtonState buttonState;
    private BuzzwordPane gamePane;
    private UserPane userPane;
    private LevelPane lp;
    private VBox rightBar;
    private int currentLevel;
    private Region r;
    private String userName;


    public static HomeSceneSingleton getHomeSceneSingleton(){

        return homeSceneSingleton == null? new HomeSceneSingleton() : homeSceneSingleton;
    }

    public HomeSceneSingleton(){
        //userName
        userName = "User";
        layout();
        initializeHandlers();
        initializeStyle();
    }

    public HomeSceneSingleton(String userName){
        //userName
        this.userName = userName;
        layout();
        initializeHandlers();
        initializeStyle();
    }

    public void layout() {
        pane = new BorderPane();
        pane.setPrefSize(800, 600);

        VBox leftBar = new VBox(10);
        leftBar.setId("left-bar");

        Region filler = new Region();
        filler.setPrefSize(leftBar.getPrefWidth(), 120);

        startPlaying = new Button("New Game");
        selectLevel = new Button("Select Level");
        home = new Button("Home");
        user = new Button(userName);
        selectMode = new ChoiceBox<>();
        selectMode.getStylesheets().add("css/ChoiceBoxStyle.css");
        selectMode.setTooltip(new Tooltip("Select Game Mode"));
        selectMode.setValue("Dictionary Words");
        selectMode.getItems().addAll("Dictionary Words", "Famous People", "Places", "Science");


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
   // @Override
    public void initializeHandlers() {
        startPlaying.setOnAction(event -> {
            gamePane = (lp == null) ? new BuzzwordPane(selectMode.getValue(), "1")
                    : new BuzzwordPane(selectMode.getValue(), Integer.toString(lp.getSelectedLevel()));
            rightBar.getChildren().set(1, gamePane.getPrimaryPane());
        });
        selectLevel.setOnAction(event -> {
            lp = new LevelPane(selectMode.getValue());
            rightBar.getChildren().set(1, lp.getPrimaryPane());
        });

        home.setOnAction(event -> {
            rightBar.getChildren().set(1, new VBox(r, buttonGrid));
        });

        user.setOnAction(event -> {
            userPane = new UserPane();
            rightBar.getChildren().set(1, userPane.getPrimaryPane());
        });


    }

    public void initializeStyle() {
        primaryScene.getStylesheets().add("css/SceneStyle.css");
    }

    public void init(JFLAGWorkspaceComponent workspace) {
        this.workspace = (Workspace) workspace;
        homeSceneSingleton = new HomeSceneSingleton(this.workspace.getUserName());
    }

    public void buildGrid() {
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
}
