package ui;

import app.JFLAGApplication;
import components.JFLAGStyleArbiter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import propertymanager.PropertyManager;
import state.ButtonTracker;
import state.GUITracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;

import static settings.AppPropertyType.*;
import static settings.InitializationParameters.APP_IMAGEDIR_PATH;

/**
 * This class provides the basic user interface for this application, including all the file controls, but it does not
 * include the workspace, which should be customizable and application dependent.
 *
 * @author Richard McKenna, Ritwik Banerjee
 */
public class AppGUI extends Observable implements JFLAGStyleArbiter {

    protected JFLAGApplication app;
    protected Stage          primaryStage;     // the application window
    protected Scene          primaryScene;     // the scene graph
    protected BorderPane     appPane;          // the root node in the scene graph, to organize the containers
    protected FlowPane       toolbarPane;      // the top toolbar
    protected Button         newButton;        // button to create a new instance of the application
    protected Button         saveButton;       // button to save progress on application
    protected Button         loadButton;       // button to load a saved game from (json) file
    protected Button         exitButton;       // button to exit application
    protected String         applicationTitle; // the application title
    public ButtonTracker observerID;
    public GUITracker sceneID;

    private int appWindowWidth;  // optional parameter for window width that can be set by the application
    private int appWindowHeight; // optional parameter for window height that can be set by the application
    
    /**
     * This constructor initializes the file toolbar for use.
     *
     * @param initPrimaryStage The window for this application.
     * @param initAppTitle     The title of this application, which
     *                         will appear in the window bar.
     * @param app              The app within this gui is used.
     */
    public AppGUI(Stage initPrimaryStage, String initAppTitle, JFLAGApplication app) throws IOException, InstantiationException {
        this(initPrimaryStage, initAppTitle, app, -1, -1);
    }

    public AppGUI(Stage primaryStage, String applicationTitle, JFLAGApplication appTemplate, int appWindowWidth, int appWindowHeight) throws IOException, InstantiationException {
        this.appWindowWidth = appWindowWidth;
        this.appWindowHeight = appWindowHeight;
        this.primaryStage = primaryStage;
        this.applicationTitle = applicationTitle;
        app = appTemplate;
        initializeToolbar();                    // initialize the top toolbar
        initializeToolbarHandlers(appTemplate); // set the toolbar button handlers
        initializeWindow();                     // start the app window (without the application-specific workspace)

    }

    public FlowPane getToolbarPane() { return toolbarPane; }

    public BorderPane getAppPane() { return appPane; }
    
    /**
     * Accessor method for getting this application's primary stage's,
     * scene.
     *
     * @return This application's window's scene.
     */
    public Scene getPrimaryScene() { return primaryScene; }
    
    /**
     * Accessor method for getting this application's window,
     * which is the primary stage within which the full GUI will be placed.
     *
     * @return This application's primary stage (i.e. window).
     */
    public Stage getWindow() { return primaryStage; }
    
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initializeToolbar() throws IOException {
        toolbarPane = new FlowPane();
        newButton = initializeChildButton(toolbarPane, NEW_ICON.toString(), NEW_TOOLTIP.toString(), false);
        loadButton = initializeChildButton(toolbarPane, LOAD_ICON.toString(), LOAD_TOOLTIP.toString(), false);
        saveButton = initializeChildButton(toolbarPane, SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), false);
        exitButton = initializeChildButton(toolbarPane, EXIT_ICON.toString(), EXIT_TOOLTIP.toString(), false);
    }

    private void initializeToolbarHandlers(JFLAGApplication app) throws InstantiationException {

        newButton.setOnAction(e -> {
            observerID = ButtonTracker.NEW;
            setChanged();
            notifyObservers();
        });
        saveButton.setOnAction(e -> {
                observerID = ButtonTracker.SAVE;
                setChanged();
                notifyObservers();

        });
        loadButton.setOnAction(e -> {

                observerID = ButtonTracker.LOAD;
                setChanged();
                notifyObservers();

        });
        exitButton.setOnAction(e -> {
            observerID = ButtonTracker.EXIT;
            setChanged();
            notifyObservers();
        });
    }

    public void updateWorkspaceToolbar(boolean savable) {
        saveButton.setDisable(!savable);
        newButton.setDisable(false);
        exitButton.setDisable(false);
    }

    private void initializeWindow() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        // SET THE WINDOW TITLE
        primaryStage.setTitle(applicationTitle);

        // add the toolbar to the constructed workspace
        appPane = new BorderPane();
        appPane.setTop(toolbarPane);
        primaryScene = appWindowWidth < 1 || appWindowHeight < 1 ? new Scene(appPane)
                                                                 : new Scene(appPane,
                                                                             appWindowWidth,
                                                                             appWindowHeight);

        URL imgDirURL = JFLAGApplication.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resrouces folder does not exist.");
        try (InputStream appLogoStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(APP_LOGO)))) {
            primaryStage.getIcons().add(new Image(appLogoStream));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    /**
     * This is a public helper method for initializing a simple button with
     * an icon and tooltip and placing it into a toolbar.
     *
     * @param toolbarPane Toolbar pane into which to place this button.
     * @param icon        Icon image file name for the button.
     * @param tooltip     Tooltip to appear when the user mouses over the button.
     * @param disabled    true if the button is to start off disabled, false otherwise.
     * @return A constructed, fully initialized button placed into its appropriate
     * pane container.
     */
    public Button initializeChildButton(Pane toolbarPane, String icon, String tooltip, boolean disabled) throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        URL imgDirURL = JFLAGApplication.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resources folder does not exist.");

        Button button = new Button();
        try (InputStream imgInputStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(icon)))) {
            Image buttonImage = new Image(imgInputStream);
            button.setDisable(disabled);
            button.setGraphic(new ImageView(buttonImage));
            Tooltip buttonTooltip = new Tooltip(propertyManager.getPropertyValue(tooltip));
            button.setTooltip(buttonTooltip);
            toolbarPane.getChildren().add(button);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return button;
    }
    /**
     * This function specifies the CSS style classes for the controls managed
     * by this framework.
     */
    @Override
    public void initStyle() {
        primaryStage.setResizable(false);
        PropertyManager propertyManager = PropertyManager.getManager();

        appPane.setId(propertyManager.getPropertyValue(ROOT_BORDERPANE_ID));
        toolbarPane.getStyleClass().setAll(propertyManager.getPropertyValue(SEGMENTED_BUTTON_BAR));
        toolbarPane.setId(propertyManager.getPropertyValue(TOP_TOOLBAR_ID));
    }
}
