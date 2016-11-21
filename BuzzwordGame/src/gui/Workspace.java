package gui;

import app.JFLAGApplication;
import components.JFLAGWorkspaceComponent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.AppGUI;
import ui.InitialSceneSingleton;

/**
 * Created by ishmam on 10/31/2016.
 */
public class Workspace extends JFLAGWorkspaceComponent{
    private AppGUI gui;
    private JFLAGApplication application;
    private Stage primaryStage;
    private Scene primaryScene;
    private HomeSceneSingleton homeSceneSingleton;
    private BuzzwordPane gamePane;
    private UserPane userPane;
    private LevelPane levelPane;

    public Workspace(JFLAGApplication app){
        this.application = app;
        gui = app.getGUI();
        primaryStage = gui.getPrimaryStage();
        homeSceneSingleton = HomeSceneSingleton.getHomeSceneSingleton();
        setBuzzwordGrid();
    }

    private void setBuzzwordGrid() {
        InitialSceneSingleton initialSceneSingleton = InitialSceneSingleton.getInitialSceneSingleton();
        initialSceneSingleton.setRightPane(homeSceneSingleton.getButtonGrid());
    }

    @Override
    public void initStyle() {

    }

    @Override
    public void reloadWorkspace() {

    }

    public void startBuzzword(){
        homeSceneSingleton.init(this);
        primaryScene = homeSceneSingleton.getScene();
        primaryStage.setScene(primaryScene);
    }

    public String getUserName() {
        return this.application.getCurrentUser().getUserName();
    }
}
