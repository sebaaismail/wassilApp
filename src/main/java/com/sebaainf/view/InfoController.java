package com.sebaainf.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.sebaainf.main.BasicScene;

/**
 * Created by Ismail on 25/12/2018.
 */
public class InfoController {
    @FXML
    private VBox vBox = new VBox();
    @FXML
    private Pane blankPane = new Pane();
    @FXML
    private TextField textField = new TextField();

    @FXML
    public void setMinHeightPane() {
        this.blankPane.setMinHeight(0.15 * BasicScene.screenSize.getHeight());
        this.textField.setText("222222222222222");
    }

    @FXML
    private void initialize() {
        //this.textField.setText("wiwwwwwwwwwwwwww");
        this.blankPane.setMinHeight(0.15 * BasicScene.screenSize.getHeight());
        this.vBox.setMaxWidth(0.25 * BasicScene.screenSize.getWidth());
    }
}
