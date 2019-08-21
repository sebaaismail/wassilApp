package com.sebaainf.seatsPlan.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import main.BasicScene;
import main.GlobalResult;
import com.sebaainf.seatsPlan.GroupsMaker;
import main.MyApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ismail on 26/12/2018.
 */
public class MakingGroupsController {
    @FXML
    GridPane gp = null;
    @FXML
    Pane pane = null;
    @FXML
    Button buttonFichier = null;
    @FXML
    Button buttonFichier2 = null;
    @FXML
    Button buttonPrepareGroups = null;
    @FXML
    Button buttonGrouping = new Button();
    @FXML
    Label label = new Label();
    @FXML
    Label messageLabel = new Label();

    @FXML
    private void initialize() {
        //Stage theStage = (Stage) this.getWindow();
        this.gp.setMinWidth(0.25 * BasicScene.screenSize.getWidth());
        this.pane.setMinHeight(0.2 * BasicScene.screenSize.getHeight());
    }

    public void buttonFichierClick() {

        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le Fichier excel...");
        MyApp.selectedFile = fileChooser.showOpenDialog(null); //theStage
        label.setText(MyApp.selectedFile.getPath());
    }
    public void buttonFichier2Click() {

        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le Fichier excel...");
        MyApp.selectedPreparedFile = fileChooser.showOpenDialog(null); //theStage
        label.setText(MyApp.selectedPreparedFile.getPath());
    }


    public void buttonPrepareGroupsClick() {
        if (MyApp.selectedFile != null) {
            if (!MyApp.configLists()) return;
            GlobalResult gr = MyApp.processForGroups(MyApp.selectedFile);
            File pre = gr.getPrepareGroupsFile();

            //TODO for twoFiles
            //String s = MyApp.process_with_compare(null, MyApp.selectedFile).getName();
            messageLabel.setText("تم إنشاء ملف تحضير الأفواج \"" + pre.getName() + "\"");
            try {
                //wait(3000);
                Desktop.getDesktop().open(pre);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else System.out.println("selectedFile is null");
    }

    public File buttonGroupingClick() {
        if (MyApp.selectedPreparedFile != null) {
            GroupsMaker gm = new GroupsMaker(MyApp.selectedPreparedFile);
            File pre = gm.run();
            ArrayList<File> mapPdfs = gm.createPdfs();

         messageLabel.setText("تم إنشاء ملف تحضير الأفواج \"" + pre.getName() + "\"");
        try {
            //wait(3000);
            //Desktop.getDesktop().open(pre);
            Desktop.getDesktop().open(mapPdfs.get(0));//test

            /*
            for (File f:mapPdfs) {
                if(f != null){
                    Desktop.getDesktop().open(f);
                }
            }
            //*/
            return pre;
        } catch (IOException e) {
            e.printStackTrace();
        }

    } else System.out.println("selectedPreparedFile is null");
        return null;
    }
}
