package gui;

import app.JFLAGApplication;
import components.JFLAGWorkspaceComponent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.TestGUI;

/**
 * Created by ishmam on 10/31/2016.
 */
public class Workspace extends JFLAGWorkspaceComponent{
    private TestGUI gui;
    private Stage primaryStage;
    private Scene primaryScene;
    HomeSceneSingleton homeSceneSingleton;
    public Workspace(JFLAGApplication app){
        gui = app.getGUI();
        primaryStage = gui.getPrimaryStage();
        homeSceneSingleton = HomeSceneSingleton.getHomeSceneSingleton();
    }
    @Override
    public void initStyle() {

    }

    @Override
    public void reloadWorkspace() {

    }

    public void startBuzzword(){
        primaryScene = homeSceneSingleton.getScene();
        primaryStage.setScene(primaryScene);
    }
}
