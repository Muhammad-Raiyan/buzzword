package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import state.ButtonState;

/**
 * Created by ishmam on 10/30/2016.
 */
public class InitialSceneSingleton extends JFLAGScene{

    public static InitialSceneSingleton initialScene = null;
    private static Scene scene;
    private BorderPane pane;
    private Button createProfile, login, help;
    private ButtonState buttonState;

    private InitialSceneSingleton(){
        layout();
        initializeHandlers();
        initializeStyle();
    }



    public static InitialSceneSingleton getInitialSceneSingleton(){
        if(initialScene == null)
            initialScene = new InitialSceneSingleton();
        return initialScene;
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

        login = new Button("Login");

        help = new Button("Help");

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
        scene = new Scene(pane);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public Pane getPane(){
        return pane;
    }

    @Override
    public void initializeHandlers() {
        ProfileDialogSingleton s = ProfileDialogSingleton.getProfileCreator();
        createProfile.setOnAction(event -> {
            TestGUI.buttonState = ButtonState.CREATE;
            setChanged();
            notifyObservers();
        });
        login.setOnAction(event -> {
            TestGUI.buttonState = ButtonState.LOGIN;
            setChanged();
            notifyObservers();
        });
        help.setOnAction(event -> System.out.println("Help"));
    }

    public void initializeStyle() {
        scene.getStylesheets().add("ui/SceneStyle.css");
    }

    @Override
    public void init(ButtonState buttonState) {
        this.buttonState = buttonState;
    }


}
