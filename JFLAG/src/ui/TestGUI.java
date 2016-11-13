package ui;

import app.JFLAGApplication;
import components.JFLAGStyleArbiter;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by ishmam on 11/11/2016.
 */
public class TestGUI implements JFLAGStyleArbiter{

    JFLAGApplication app;
    private Stage primaryStage;
    private Scene primaryScene;
    private SceneTracker currentScene;
    private String appTitle;
    ProfileCreatorSingleton p;
    private HashMap<SceneTracker, Scene> sceneMap;
    public SceneTracker observerID;


    public TestGUI(JFLAGApplication app, Stage primaryStage, String appTitle){
        this.app = app;
        this.primaryStage = primaryStage;
        this.appTitle = appTitle;
        sceneMap = new HashMap<>();
        primaryScene = InitialSceneSingleton.getInitialSceneSingleton();
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

    public Scene getPrimaryScene(){
        return primaryScene;
    }

    /*public Pane getAppPane(){
        return pane;
    }*/

    public void addScene(SceneTracker sceneID, Scene scene){
        sceneMap.put(sceneID, scene);
    }

    public void changeScene(SceneTracker id){
        primaryScene = sceneMap.get(id);
    }

    @Override
    public void initStyle() {

    }
}
