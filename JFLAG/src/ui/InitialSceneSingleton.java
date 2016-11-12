package ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by ishmam on 10/30/2016.
 */
public class InitialSceneSingleton extends JFLAGScene{

    private static InitialSceneSingleton singleton = null;
    private Scene scene;
    private Pane pane;

    private InitialSceneSingleton(){
        scene = new Scene(pane);
    }

    public InitialSceneSingleton getProfileSceneSingleton(){
        return singleton == null ? new InitialSceneSingleton() : singleton;
    }

    @Override
    public void layout() {
        pane = new BorderPane();

        VBox leftBar = new VBox();
    }

    @Override
    public void initializeHandlers() {

    }


}
