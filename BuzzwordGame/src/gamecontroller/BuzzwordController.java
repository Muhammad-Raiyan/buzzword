package gamecontroller;

import app.JFLAGApplication;
import controller.AppFileController;
import controller.FileController;

import java.io.IOException;

/**
 * Created by ishmam on 10/30/2016.
 */
public class BuzzwordController extends AppFileController{


    /**
     * Constructor to just store the reference to the application.
     *
     * @param appTemplate The application within which this controller will provide file toolbar responses.
     */
    public BuzzwordController(JFLAGApplication appTemplate){
        super(appTemplate);

    }


    public void handleCreateRequest() {
        System.out.println("Hello");
    }

}
