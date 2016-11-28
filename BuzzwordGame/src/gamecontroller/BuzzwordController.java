package gamecontroller;

import app.JFLAGApplication;
import controller.AppFileController;
import gamedata.BuzzwordData;
import gamedata.BuzzwordDataFile;
import gui.HomeSceneSingleton;
import gui.Workspace;
import ui.ProfileDialogSingleton;

import java.io.IOException;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordController extends AppFileController{

    private Workspace workspace;
    private JFLAGApplication appTemplate;
    /**
     * Constructor to just store the reference to the application.
     *
     * @param appTemplate The application within which this controller will provide file toolbar responses.
     */
    public BuzzwordController(JFLAGApplication appTemplate){
        super(appTemplate);
        this.appTemplate = appTemplate;
        workspace = (Workspace) appTemplate.getWorkspaceComponent();
    }

    public void handleSignInRequest() throws IOException {
        Boolean success = false;
        BuzzwordDataFile gameFile = (BuzzwordDataFile) appTemplate.getFileComponent();
        BuzzwordData gameData = (BuzzwordData) appTemplate.getDataComponent();
        while(!success){
            try{
                super.handleSignInRequest();
                success = true;
                gameFile.loadGameData(gameData, appTemplate.getCurrentUser().getJsonFile());
            }
            catch (Exception e){
                e.printStackTrace();    //TODO : remove this before submission
            }
        }

        ProfileDialogSingleton profileDialogSingleton = ProfileDialogSingleton.getProfileCreator();
        if(profileDialogSingleton.getSelection().equals("login")) {
            workspace.startBuzzword();
        }
    }

    @Override
    public void handleExitRequest() {
        super.handleExitRequest();
    }

    public void handleStartRequest(){
        HomeSceneSingleton homeSceneSingleton = HomeSceneSingleton.getHomeSceneSingleton();
        BuzzwordData gameData = (BuzzwordData) appTemplate.getDataComponent();
        //gameData.setCurrentLevel(homeSceneSingleton.getSelectedLevel());
        //gameData.setCurrentMode(homeSceneSingleton.getSelectedMode());
        homeSceneSingleton.startGame();
    }
}
