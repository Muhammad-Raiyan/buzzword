package settings;

import app.JFLAGApplication;
import components.JFLAGSettingsComponent;
import propertymanager.PropertyManager;
import ui.AppMessageDialogSingleton;
import xmlutils.InvalidXMLFileFormatException;

import static settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static settings.InitializationParameters.*;

/**
 * Created by ishmam on 10/29/2016.
 */
public abstract class JFLAGSettings implements JFLAGSettingsComponent{
    private PropertyManager propertyManager = PropertyManager.getManager();
    private boolean isConfigured = false;
    JFLAGApplication app;

    public JFLAGSettings(JFLAGApplication app){
        this.app = app;
        loadSettings(APP_PROPERTIES_XML);
        isConfigured = propertyManager.isInitialized();

    }

    public void loadSettings(InitializationParameters loadThis){
        try {
            propertyManager.loadProperties(JFLAGApplication.class, loadThis.getParameter(), PROPERTIES_SCHEMA_XSD.getParameter());
        } catch (InvalidXMLFileFormatException e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(propertyManager.getPropertyValue(PROPERTIES_LOAD_ERROR_TITLE.toString()),
                    propertyManager.getPropertyValue(PROPERTIES_LOAD_ERROR_MESSAGE.toString()));
        }
    }

    public void changeSettings(InitializationParameters loadThis){
        try {
            propertyManager.loadProperties(JFLAGApplication.class, loadThis.getParameter(), PROPERTIES_SCHEMA_XSD.getParameter());
        } catch (InvalidXMLFileFormatException e) {
            e.printStackTrace();
        }
    }

    public boolean isConfigured() {
        return isConfigured;
    }
}
