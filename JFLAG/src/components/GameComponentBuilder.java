package components;

/**
 * Created by ishmam on 10/30/2016.
 */
public interface GameComponentBuilder {
    JFLAGDataComponent buildDataComponent() throws Exception;

    JFLAGFileComponent buildFileComponent() throws Exception;

    //JFLAGSettingsComponent buildSettingComponent() throws Exception;

    JFLAGWorkspaceComponent buildWorkspaceComponent() throws Exception;
}
