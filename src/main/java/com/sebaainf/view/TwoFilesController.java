package com.sebaainf.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import main.BasicScene;
import main.GlobalResult;
import main.MyApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ismail on 29/12/2018.
 */
public class TwoFilesController {

    @FXML
    GridPane gp = null;
    @FXML
    Pane pane = null;
    @FXML
    Button buttonNewTri = null;
    @FXML
    Button buttonOldTri = null;
    @FXML
    Button buttonCalc = new Button();
    @FXML
    Label label1 = new Label();
    @FXML
    Label label2 = new Label();
    @FXML
    Label messageLabel = new Label();

    @FXML
    private void initialize() {
        this.gp.setMinWidth(0.25 * BasicScene.screenSize.getWidth());
        this.pane.setMinHeight(0.15 * BasicScene.screenSize.getHeight());
    }

    public void buttonOneClick() {
        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le Fichier excel...");
        MyApp.selectedFile = fileChooser.showOpenDialog(null); //theStage
        label1.setText(MyApp.selectedFile.getPath());
    }

    public void buttonTwoTriClick() {
        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le 2eme Fichier excel(L'ancien)...");
        MyApp.selectedOldFile = fileChooser.showOpenDialog(null); //theStage
        label2.setText(MyApp.selectedOldFile.getPath());
    }

    public void buttonCalcClick() {

        if (MyApp.selectedFile != null) {
            if (!MyApp.configLists()) return;
            GlobalResult gr = MyApp.process_with_compare(MyApp.selectedOldFile, MyApp.selectedFile);

            File resFile = gr.getExcelResult();
            File analyseFile = gr.getAnalyseFile();
            //TODO for twoFiles
            //String s = MyApp.process_with_compare(null, MyApp.selectedFile).getName();
            messageLabel.setText("تم إنشاء الملف المعالج بنجاح \"" + resFile.getName() + "\"");
            try {
                //wait(3000);
                Desktop.getDesktop().open(resFile);
                Desktop.getDesktop().open(analyseFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else System.out.println("selectedFile is null");
    }

    public void configClick() {
        MyApp.openConfigFile();
    }
}