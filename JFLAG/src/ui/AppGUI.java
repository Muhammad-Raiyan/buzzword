package ui;

import app.JFLAGApplication;
import components.JFLAGStyleArbiter;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import observer.ButtonObserver;
import state.ButtonState;
import javafx.scene.layout.*;

import java.util.HashMap;

/**
 * Created by ishmam on 11/11/2016.
 */
public class AppGUI implements JFLAGStyleArbiter{

    JFLAGApplication app;
    private Stage primaryStage;
    private Scene primaryScene;
    private String appTitle;
    private HashMap<ButtonState, Scene> sceneMap;
    public static ButtonState buttonState;
    public ButtonObserver buttonObserver;

    public AppGUI(JFLAGApplication app, Stage primaryStage, String appTitle){
        this.app = app;
        this.primaryStage = primaryStage;
        this.appTitle = appTitle;
        sceneMap = new HashMap<>();
        buttonObserver = new ButtonObserver(this.app);
        buttonState = ButtonState.INITIAL;

        ProfileDialogSingleton profileDialogSingleton = ProfileDialogSingleton.getProfileCreator();
        profileDialogSingleton.addObserver(buttonObserver);
        InitialSceneSingleton initialSceneSingleton = InitialSceneSingleton.getInitialSceneSingleton();
        initialSceneSingleton.addObserver(buttonObserver);
        AppMessageDialogSingleton  messageDialog = AppMessageDialogSingleton.getSingleton();
        YesNoCancelDialogSingleton yesNoDialog   = YesNoCancelDialogSingleton.getSingleton();

        initialSceneSingleton.init(buttonState);
        profileDialogSingleton.init(primaryStage);
        messageDialog.init(primaryStage);
        yesNoDialog.init(primaryStage);

        primaryScene = initialSceneSingleton.getScene();
        primaryStage.setScene(primaryScene);
        //primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();

    }

    public Scene getPrimaryScene(){
        return primaryScene;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    /*public Pane getAppPane(){
        return pane;
    }*/

    /*public void addScene(SceneTracker sceneID, Scene scene){
        sceneMap.put(sceneID, scene);
    }

    public void changeScene(SceneTracker id){
        primaryScene = sceneMap.get(id);
    }
*/
    @Override
    public void initStyle() {

    }

}
