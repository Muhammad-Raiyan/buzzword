package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

/**
 * Created by ishmam on 12/10/2016.
 *
 * @author ishmam
 */
public class GridSolutionSingleton extends Stage {

    private static GridSolutionSingleton singleton = null;

    public Button closeButton;
    private ListView solutionTable;
    private TableColumn wordsColumn;
    private ObservableList<String> solutionList;
    private GridSolutionSingleton() {

    }

    public static GridSolutionSingleton getSingleton(){
        if(singleton == null)
            singleton = new GridSolutionSingleton();
        return singleton;
    }

    public void init(Stage owner){
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);

        closeButton = new Button("Close");
        closeButton.setOnAction(event -> this.close());

        solutionTable = new ListView();
        solutionTable.setEditable(false);


        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(solutionTable, closeButton);

        Scene solutionScene = new Scene(vBox);
        this.setScene(solutionScene);
        solutionScene.getStylesheets().addAll("css/DialogStyle.css","css/GameScene.css");
        this.initStyle(StageStyle.UNDECORATED);

    }

    public void setSolutionList(ArrayList<String> solutionList) {
        this.solutionList = FXCollections.observableArrayList(solutionList);
        solutionTable.setItems(this.solutionList);
    }

    public void show(String title){
        setTitle(title);
        showAndWait();
    }
}
