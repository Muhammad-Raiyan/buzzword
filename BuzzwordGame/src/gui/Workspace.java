package gui;

import app.JFLAGApplication;
import components.JFLAGWorkspaceComponent;
import gamecontroller.BuzzwordObserver;
import gamecontroller.BuzzwordState;
import gamedata.BuzzwordData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.AppGUI;
import ui.InitialSceneSingleton;

import java.util.ArrayList;

/**
 * Created by ishmam on 10/31/2016.
 */
public class Workspace extends JFLAGWorkspaceComponent{

    private AppGUI gui;
    private JFLAGApplication application;
    private Stage primaryStage;
    private Scene primaryScene;
    private HomeSceneSingleton homeSceneSingleton;
    /*private BuzzwordPane gamePane;
    private UserPane userPane;
    private LevelPane levelPane;
    */
    public static BuzzwordState gameState;
    private BuzzwordObserver buzzwordObserver;

    public Workspace(JFLAGApplication app){
        this.application = app;
        gui = app.getGUI();
        primaryStage = gui.getPrimaryStage();
        gameState = BuzzwordState.HOME;
        buzzwordObserver = new BuzzwordObserver(app);
        homeSceneSingleton = HomeSceneSingleton.getHomeSceneSingleton();
        homeSceneSingleton.addObserver(buzzwordObserver);
        GridSolutionSingleton gridSolutionSingleton = GridSolutionSingleton.getSingleton();
        gridSolutionSingleton.init(gui.getPrimaryStage());
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
        //homeSceneSingleton.addObserver(buzzwordObserver);
        primaryScene = homeSceneSingleton.getScene();
        primaryStage.setScene(primaryScene);
    }

    public String getUserName() {
        return this.application.getCurrentUser().getUserName();
    }

    public BuzzwordObserver getBuzzwordObserver() {
        return buzzwordObserver;
    }

    public ArrayList<Integer> getUnlockedLevels(String mode) {
        BuzzwordData data =  (BuzzwordData) application.getDataComponent();
        return data.getUnlockedLevels(mode);

    }

    public BuzzwordData getBuzzwordData(){
        return (BuzzwordData) application.getDataComponent();
    }
}
