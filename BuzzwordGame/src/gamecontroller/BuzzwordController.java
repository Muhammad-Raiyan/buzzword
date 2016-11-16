package gamecontroller;

import app.JFLAGApplication;
import controller.AppFileController;
import controller.FileController;
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

    public void handleLoginRequest() throws IOException {
        try{
            super.handleLoginRequest();
        }
        catch (IOException e){

        }
        workspace.startBuzzword();

    }

}
