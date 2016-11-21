package controller;

import app.JFLAGApplication;
import data.ProfileManager;
import data.UserData;
import javafx.beans.property.SimpleBooleanProperty;
import propertymanager.PropertyManager;
import ui.ProfileDialogSingleton;
import ui.YesNoCancelDialogSingleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;

/**
 * This class provides the event programmed responses for the file controls
 * that are provided by this framework.
 *
 * @author Richard McKenna, Ritwik Banerjee
 */
public class AppFileController implements FileController {

    public JFLAGApplication appTemplate;     // reference to the application
    public SimpleBooleanProperty saved;           // whether or not changes have been saved
    public File                  currentWorkFile; // the file on which currently work is being done

    /**
     * Constructor to just store the reference to the application.
     *
     * @param appTemplate The application within which this controller will provide file toolbar responses.
     */
    public AppFileController(JFLAGApplication appTemplate) {
        this.saved = new SimpleBooleanProperty(true);
        this.appTemplate = appTemplate;
    }

    private void ensureActivatedWorkspace() {
        //appTemplate.getWorkspaceComponent().activateWorkspace(appTemplate.getGUI().getAppPane());
    }

    /**
     * This method will save the current course to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     */
    public void handleSaveRequest() {
        System.out.println("Hello from save");
        //appTemplate.startPlay();
    }

    public void handleLoadRequest() {
        System.out.println("Hello From LOAD");
    }
    
    /**
     * A helper method to save work. It saves the work, marks the current work file as saved, notifies the user, and
     * updates the appropriate controls in the user interface
     *
     * @param selectedFile The file to which the work will be saved.
     * @throws IOException
     */
    private void saveWork(File selectedFile) throws IOException {
       /* appTemplate.getFileComponent()
                   .saveData(appTemplate.getDataComponent(), Paths.get(selectedFile.getAbsolutePath()));

        currentWorkFile = selectedFile;
        saved.set(true);

        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        PropertyManager props  = PropertyManager.getManager();
        dialog.show(props.getPropertyValue(SAVE_COMPLETED_TITLE), props.getPropertyValue(SAVE_COMPLETED_MESSAGE));*/
    }
    
    /** This method will exit the application. If work is unsaved, it will first prompt the user. */
    public void handleExitRequest() {
        YesNoCancelDialogSingleton yesNoCancelDialog = YesNoCancelDialogSingleton.getSingleton();
        yesNoCancelDialog.show("EXIT CONFIRMATION", "DO YOU WANT TO EXIT?");
        try {
            if(yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.YES)){
                System.exit(0);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        /*try {
            boolean continueToExit = true;
            if (!saved.getValue())
                continueToExit = promptToSave();
            if (continueToExit)
                System.exit(0);
        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            PropertyManager props  = PropertyManager.getManager();
            dialog.show(props.getPropertyValue(SAVE_ERROR_TITLE), props.getPropertyValue(SAVE_ERROR_MESSAGE));
        }*/
    }

    @Override
    public void handleCreateRequest() throws IOException {
        ProfileDialogSingleton profileDialogSingleton = ProfileDialogSingleton.getProfileCreator();
        profileDialogSingleton.changeToSignup();
        profileDialogSingleton.show();
    }

    @Override
    public void handleSignInRequest() throws IOException {
        ProfileDialogSingleton profileDialogSingleton = ProfileDialogSingleton.getProfileCreator();
        profileDialogSingleton.changeToLogin();
        profileDialogSingleton.show();
        ProfileManager profileManager = (ProfileManager) appTemplate.getFileComponent();
        if(profileDialogSingleton.getSelection().equals("cancel")) return;
        if(!profileManager.validate(profileDialogSingleton.getUserName(), profileDialogSingleton.getPassword())){
            throw new IOException("Incorrect");
        }
    }

    public void handleSignUpRequest() throws IOException {
        ProfileDialogSingleton profileDialogSingleton = ProfileDialogSingleton.getProfileCreator();
        System.out.println("User: " + profileDialogSingleton.getUserName());
        System.out.println("Password: " + profileDialogSingleton.getPassword());

        UserData userProfile = new UserData();
        userProfile.createUser(profileDialogSingleton.getUserName(), profileDialogSingleton.getPassword());
        appTemplate.setCurrentUser(userProfile);

        Path target = Paths.get("JFLAG\\resources\\profiledata\\users.json");
        ProfileManager profileManager = (ProfileManager) appTemplate.getFileComponent();
        profileManager.addUser(userProfile);
        profileManager.saveData(target);

        profileDialogSingleton.close();

    }

    @Override
    public void handleHelpRequest() throws IOException {
        System.out.println("Help");
    }

    /**
     * This helper method verifies that the user really wants to save their unsaved work, which they might not want to
     * do. Note that it could be used in multiple, like creating new work, or opening another one. The user will be
     * presented with three options:
     * <ol>
     * <li>{@code yes}, to indicate that the user wants to save their work and continue with the action,</li>
     * <li>{@code no}, to indicate that the user wants to continue with the action without saving their work, and</li>
     * <li>{@code cancel}, to indicate that the user does not want to continue with the action, but also does not want
     * to save their work at this point.</li>
     * </ol>
     *
     * @return {@code false} if the user presses the <i>cancel</i>, and {@code true} otherwise.
     */
    private boolean promptToSave() throws IOException {
        PropertyManager propertyManager   = PropertyManager.getManager();
        YesNoCancelDialogSingleton yesNoCancelDialog = YesNoCancelDialogSingleton.getSingleton();

        yesNoCancelDialog.show(propertyManager.getPropertyValue(SAVE_UNSAVED_WORK_TITLE),
                               propertyManager.getPropertyValue(SAVE_UNSAVED_WORK_MESSAGE));

        if (yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.YES)) {
            /*if (currentWorkFile != null)
                saveWork(currentWorkFile);
            else {
                FileChooser filechooser = new FileChooser();
                URL         workDirURL  = JFLAGApplication.class.getClassLoader().getResource(APP_WORKDIR_PATH.getParameter());
                if (workDirURL == null)
                    throw new FileNotFoundException("Work folder not found under resources.");

                File initialDir = new File(workDirURL.getFile());
                filechooser.setInitialDirectory(initialDir);
                filechooser.setTitle(propertyManager.getPropertyValue(SAVE_WORK_TITLE));

                String description = propertyManager.getPropertyValue(WORK_FILE_EXT_DESC);
                String extension   = propertyManager.getPropertyValue(WORK_FILE_EXT);
                ExtensionFilter extFilter = new ExtensionFilter(String.format("%s (*.%s)", description, extension),
                                                                String.format("*.%s", extension));
                filechooser.getExtensionFilters().add(extFilter);
                File selectedFile = filechooser.showSaveDialog(appTemplate.getGUI().getWindow());
                if (selectedFile != null)
                    saveWork(selectedFile);
            }*/
        }

        return !yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.CANCEL);
    }

}
