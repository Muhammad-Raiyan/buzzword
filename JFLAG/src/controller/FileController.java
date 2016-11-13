package controller;

import java.io.IOException;

/**
 * @author Ritwik Banerjee
 */
public interface FileController {

    void handleExitRequest();

    void handleCreateRequest() throws IOException;

    void handleLoginRequest() throws IOException;

    void handleHelpRequest() throws IOException;
}
