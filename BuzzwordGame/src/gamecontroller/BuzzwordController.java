package gamecontroller;

import app.JFLAGApplication;
import controller.AppFileController;
import gui.Workspace;
import ui.ProfileDialogSingleton;

import java.io.IOException;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordController extends AppFileController{

    private Workspace workspace;

    /**
     * Constructor to just store the reference to the application.
     *
     * @param appTemplate The application within which this controller will provide file toolbar responses.
     */
    public BuzzwordController(JFLAGApplication appTemplate){
        super(appTemplate);
        workspace = (Workspace) appTemplate.getWorkspaceComponent();
    }

    public void handleSignInRequest() throws IOException {
        Boolean success = false;

        while(!success){
            try{
                super.handleSignInRequest();
                success = true;
            }
            catch (IOException e){
                e.printStackTrace();
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
}
