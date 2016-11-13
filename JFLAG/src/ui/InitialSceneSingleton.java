package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by ishmam on 10/30/2016.
 */
public class InitialSceneSingleton extends JFLAGScene{

    public static InitialSceneSingleton initialScene = null;
    private static Scene scene;
    private BorderPane pane;
    private Button createProfile, login, help;

    private InitialSceneSingleton(){
        layout();
        scene = new Scene(pane);
        initializeStyle();
    }



    public static Scene getInitialSceneSingleton(){
        if(scene == null)
            initialScene = new InitialSceneSingleton();
        return scene;
    }

    @Override
    public void layout() {
        pane = new BorderPane();
        pane.setPrefSize(800, 600);

        VBox leftBar = new VBox(10);

        leftBar.setId("left-bar");
        Region filler = new Region();
        filler.setPrefSize(leftBar.getPrefWidth(), 120);

        createProfile = new Button("Create New Profile");
        createProfile.setOnAction(event -> {
            ProfileCreatorSingleton s = ProfileCreatorSingleton.getProfileCreator();
            s.show();
        });
        login = new Button("Login");
        login.setOnAction(e -> System.out.println("Login"));
        help = new Button("Help");
        help.setOnAction(event -> System.out.println("Help"));


        leftBar.getChildren().addAll(filler, createProfile, login, help);

        VBox rightBar = new VBox();
        rightBar.setId("right-bar");
        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER);
        Label title = new Label("!! Buzzword !!");
        topPane.getChildren().add(title);
        rightBar.getChildren().addAll(topPane);

        pane.setCenter(rightBar);
        pane.setLeft(leftBar);
    }

    @Override
    public Scene getScene() {
        return null;
    }

    public Pane getPane(){
        return pane;
    }

    @Override
    public void initializeHandlers() {

    }

    public void initializeStyle() {
        scene.getStylesheets().add("ui/SceneStyle.css");
    }



}
