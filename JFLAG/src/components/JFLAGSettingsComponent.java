package components;

import settings.InitializationParameters;

/**
 * Created by ishmam on 10/29/2016.
 */
public interface JFLAGSettingsComponent {

    void loadSettings(InitializationParameters loadTHis);
    void changeSettings(InitializationParameters loadThis);
}
