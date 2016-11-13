package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import state.ButtonState;

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

    public HomeSceneSingleton(){
        layout();
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
        selectMode.getStylesheets().add("ui/ChoiceBoxStyle.css");
        selectMode.setTooltip(new Tooltip("Select Game Mode"));
        selectMode.setValue("Dictionary");
        selectMode.getItems().addAll("Dictionary", "Famous People", "Places", "Science");


        leftBar.getChildren().addAll(filler, startPlaying, selectLevel, selectMode, home);

        VBox rightBar = new VBox();
        rightBar.setId("right-bar");

        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label("!! Buzzword !!");
        topPane.getChildren().add(title);

        rightBar.getChildren().addAll(topPane);

        pane.setCenter(rightBar);
        pane.setLeft(leftBar);
        primaryScene = new Scene(pane);
    }

    @Override
    public Scene getScene() {
        return primaryScene;
    }
}
