package components;

import controller.FileController;

/**
 * This interface provides the structure required for a builder
 * object used for initializing all components for this application.
 * This is one means of employing a component hierarchy.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface JFLAGComponentsBuilder {
    JFLAGDataComponent buildDataComponent() throws Exception;

    JFLAGFileComponent buildFileComponent() throws Exception;

    FileController buildGameController() throws Exception;

    JFLAGSettingsComponent buildSettingComponent() throws Exception;

    JFLAGWorkspaceComponent buildWorkspaceComponent() throws Exception;
}
