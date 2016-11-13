package app;

import components.*;
import controller.FileController;
import data.ProfileManager;
import javafx.application.Application;
import javafx.stage.Stage;
import propertymanager.PropertyManager;
import ui.*;
import observer.ButtonObserver;

import java.net.URL;

import static settings.AppPropertyType.*;

/**
 * @author Ritwik Banerjee
 */
public abstract class JFLAGApplication extends Application{

    private PropertyManager propertyManager;
    private JFLAGDataComponent dataComponent; // to manage the app's data
    private JFLAGFileComponent fileComponent; // to manage the app's file I/O
    private JFLAGWorkspaceComponent workspaceComponent; // to manage the app's GUI workspace
    private JFLAGSettingsComponent settingsComponent;
    private ProfileManager profileManager;
    private FileController gameController;
    private TestGUI                gui;
    private ButtonObserver guiObserver;

    public String getFileControllerClass() {
        return "AppFileController";
    }

    public abstract JFLAGComponentsBuilder makeAppBuilderHook();
    public FileController getGameController(){
        return gameController;
    }

    public JFLAGDataComponent getDataComponent() {
        return dataComponent;
    }

    public JFLAGFileComponent getFileComponent() {
        return fileComponent;
    }

    public JFLAGWorkspaceComponent getWorkspaceComponent() {
        return workspaceComponent;
    }

    public TestGUI getGUI() {
        return gui;
    }

    @Override
    public void start(Stage primaryStage) {
        AppMessageDialogSingleton  messageDialog = AppMessageDialogSingleton.getSingleton();
        YesNoCancelDialogSingleton yesNoDialog   = YesNoCancelDialogSingleton.getSingleton();
        ProfileDialogSingleton profileCreator = ProfileDialogSingleton.getProfileCreator();
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        messageDialog.init(primaryStage);
        yesNoDialog.init(primaryStage);
        profileCreator.init(primaryStage);
        JFLAGComponentsBuilder builder = makeAppBuilderHook();

        try {
            settingsComponent = builder.buildSettingComponent();
            propertyManager = PropertyManager.getManager();

            gui = new TestGUI(this, primaryStage, "Buzzword");
            workspaceComponent = builder.buildWorkspaceComponent();
            gameController = builder.buildGameController();
            //dataComponent = builder.buildDataComponent();
            //fileComponent = builder.buildFileComponent();

            /*gui = new AppGUI(primaryStage, propertyManager.getPropertyValue(APP_TITLE.toString()), this,
                    Integer.parseInt(propertyManager.getPropertyValue(APP_WINDOW_WIDTH)),
                    Integer.parseInt(propertyManager.getPropertyValue(APP_WINDOW_HEIGHT)));*/

            guiObserver = new ButtonObserver(this);
            //gui.addObserver(guiObserver);

            profileManager = new ProfileManager(this);
            initStylesheet();
            gui.initStyle();
            //workspaceComponent.initStyle();
        } catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(propertyManager.getPropertyValue(PROPERTIES_LOAD_ERROR_TITLE.toString()),
                    propertyManager.getPropertyValue(PROPERTIES_LOAD_ERROR_MESSAGE.toString()));
        }
        /*try {
            if (loadProperties(APP_PROPERTIES_XML) && loadProperties(WORKSPACE_PROPERTIES_XML)) {
                JFLAGComponentsBuilder builder = makeAppBuilderHook();
                //fileComponent = builder.buildFileComponent();
               // dataComponent = builder.buildDataComponent();
                gui = (propertyManager.hasProperty(APP_WINDOW_WIDTH) && propertyManager.hasProperty(APP_WINDOW_HEIGHT))
                      ? new AppGUI(primaryStage, propertyManager.getPropertyValue(APP_TITLE.toString()), this,
                                   Integer.parseInt(propertyManager.getPropertyValue(APP_WINDOW_WIDTH)),
                                   Integer.parseInt(propertyManager.getPropertyValue(APP_WINDOW_HEIGHT)))
                      : new AppGUI(primaryStage, propertyManager.getPropertyValue(APP_TITLE.toString()), this);
                //workspaceComponent = builder.buildWorkspaceComponent();
                initStylesheet();
                gui.initStyle();
                //workspaceComponent.initStyle();
            }
        } catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(propertyManager.getPropertyValue(PROPERTIES_LOAD_ERROR_TITLE.toString()),
                        propertyManager.getPropertyValue(PROPERTIES_LOAD_ERROR_MESSAGE.toString()));
        }*/
    }

    public void initStylesheet() {
        /*URL cssResource = getClass().getClassLoader().getResource(propertyManager.getPropertyValue(APP_PATH_CSS) +
                                                                  File.separator +
                                                                  propertyManager.getPropertyValue(APP_CSS));*/

        URL cssResource = getClass().getClassLoader().getResource("css/framework_style.css");
        assert cssResource != null;

        gui.getPrimaryScene().getStylesheets().add(cssResource.toExternalForm());
    }

    public void initStyle() {
        PropertyManager propertyManager = PropertyManager.getManager();

        /*gui.getAppPane().setId(propertyManager.getPropertyValue(ROOT_BORDERPANE_ID));
        gui.getToolbarPane().getStyleClass().setAll(propertyManager.getPropertyValue(SEGMENTED_BUTTON_BAR));
        gui.getToolbarPane().setId(propertyManager.getPropertyValue(TOP_TOOLBAR_ID));*/


    }

}
