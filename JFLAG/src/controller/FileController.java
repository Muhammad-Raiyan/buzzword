package controller;

import java.io.IOException;

/**
 * @author Ritwik Banerjee
 */
public interface FileController {

    void handleExitRequest();

    void handleCreateRequest() throws IOException;

    void handleSignInRequest() throws IOException;

    void handleHelpRequest() throws IOException;

    void handleSignUpRequest() throws IOException;

    void handleLogoutRequest();
}
