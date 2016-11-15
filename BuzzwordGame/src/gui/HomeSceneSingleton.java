package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import state.ButtonState;
import ui.JFLAGScene;

/**
 * Created by ishmam on 11/11/2016.
 */
public class HomeSceneSingleton extends JFLAGScene {

    private String sceneID;

    public static HomeSceneSingleton homeSceneSingleton;
    private static Scene primaryScene;
    private Button startPlaying, selectLevel, home, user;
    private ChoiceBox<String> selectMode;
    private BorderPane pane;
    private GridPane buttonGrid;
    private ButtonState buttonState;
    private BuzzwordScene gameScene;
    private LevelPane lp;
    private VBox rightBar;
    private int currentLevel;

    Region r;

    public HomeSceneSingleton(){
        layout();
        initializeHandlers();
        initializeStyle();
    }
    @Override
    public void initializeHandlers() {
        startPlaying.setOnAction(event -> {
            if(lp == null) gameScene = new BuzzwordScene(selectMode.getValue(), "1");
            else gameScene =  new BuzzwordScene(selectMode.getValue(), Integer.toString(lp.getSelectedLevel()));
            rightBar.getChildren().set(1, gameScene.getPrimaryPane());
        });
        selectLevel.setOnAction(event -> {
            lp = new LevelPane(selectMode.getValue());
            rightBar.getChildren().set(1, lp.getPrimaryPane());
        });

        home.setOnAction(event -> {
            rightBar.getChildren().set(1, new VBox(r, buttonGrid));
        });

        user.setOnAction(event -> {
            rightBar.getChildren().set(1, new VBox());
        });


    }

    public static HomeSceneSingleton getHomeSceneSingleton(){
        return homeSceneSingleton == null? new HomeSceneSingleton() : homeSceneSingleton;
    }
    @Override
    public void initializeStyle() {
        primaryScene.getStylesheets().add("css/SceneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {
        rightBar.getChildren().set(1, new VBox(r, buttonGrid));
    }


    @Override
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
        user = new Button("USER");
        selectMode = new ChoiceBox<>();
        selectMode.getStylesheets().add("css/ChoiceBoxStyle.css");
        selectMode.setTooltip(new Tooltip("Select Game Mode"));
        selectMode.setValue("Dictionary Words");
        selectMode.getItems().addAll("Dictionary Words", "Famous People", "Places", "Science");


        leftBar.getChildren().addAll(filler, startPlaying, selectLevel, selectMode, home, user);

        rightBar = new VBox();
        rightBar.setId("right-bar");

        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label("!! Buzzword !!");
        topPane.getChildren().add(title);
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
    @Override
    public Scene getScene() {
        return primaryScene;
    }
}
