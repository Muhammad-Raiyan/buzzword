package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.*;
import state.ButtonState;
import ui.JFLAGScene;

/**
 * Created by ishmam on 11/11/2016.
 */
public class HomeSceneSingleton extends JFLAGScene {

    private String sceneID;

    public static HomeSceneSingleton homeSceneSingleton;
    private static Scene primaryScene;
    private Button startPlaying, selectLevel, home;
    private ChoiceBox<String> selectMode;
    private BorderPane pane;
    private ButtonState buttonState;
    private VBox rightBar;

    public HomeSceneSingleton(){
        layout();
        initializeHandlers();
        initializeStyle();
    }
    @Override
    public void initializeHandlers() {
        /*selectMode.setOnMouseEntered(event -> {
            if(!selectMode.isShowing()){
                selectMode.show();
            }
        });
        selectMode.setOnMouseExited(event -> {
            if(selectMode.isShowing()){
                selectMode.hide();
            }
        });*/
        //selectMode.setPrefWidth(180);
        startPlaying.setOnAction(event -> {

        });
        selectLevel.setOnAction(event -> {
            LevelPane lp = new LevelPane(selectMode.getValue());
            rightBar.getChildren().set(1, lp.getPrimaryPane());
        });

        home.setOnAction(event -> {
            rightBar.getChildren().set(1, new VBox());
        });

    }

    public static HomeSceneSingleton getHomeSceneSingleton(){
        return homeSceneSingleton == null? new HomeSceneSingleton() : homeSceneSingleton;
    }
    @Override
    public void initializeStyle() {
        primaryScene.getStylesheets().add("ui/SceneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {

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

        selectMode = new ChoiceBox<>();
        selectMode.getStylesheets().add("gui/ChoiceBoxStyle.css");
        selectMode.setTooltip(new Tooltip("Select Game Mode"));
        selectMode.setValue("Dictionary");
        selectMode.getItems().addAll("Dictionary", "Famous People", "Places", "Science");


        leftBar.getChildren().addAll(filler, startPlaying, selectLevel, selectMode, home);

        rightBar = new VBox();
        rightBar.setId("right-bar");

        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label("!! Buzzword !!");
        topPane.getChildren().add(title);
        Pane rightPane = new Pane();
        rightBar.getChildren().addAll(topPane, rightPane);

        pane.setCenter(rightBar);
        pane.setLeft(leftBar);
        primaryScene = new Scene(pane);
    }

    @Override
    public Scene getScene() {
        return primaryScene;
    }
}
