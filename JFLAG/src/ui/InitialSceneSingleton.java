package ui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by ishmam on 10/30/2016.
 */
public class InitialSceneSingleton extends JFLAGScene{

    private static InitialSceneSingleton singleton = null;
    private Scene profileScene;
    private Pane profilePane;
    private InitialSceneSingleton(){

    }

    public InitialSceneSingleton getProfileSceneSingleton(){
        return singleton;
    }


    @Override
    public void initializeHandlers() {

    }
}
