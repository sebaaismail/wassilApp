package com.sebaainf.seatsPlan.view;

import com.sebaainf.main.AppOld;
import com.sebaainf.main.ConfigFile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import com.sebaainf.main.BasicScene;
import com.sebaainf.main.GlobalResult;
import com.sebaainf.seatsPlan.GroupsMaker;

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
        AppOld.selectedFile = fileChooser.showOpenDialog(null); //theStage
        label.setText(AppOld.selectedFile.getPath());
    }
    public void buttonFichier2Click() {

        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le Fichier excel...");
        AppOld.selectedPreparedFile = fileChooser.showOpenDialog(null); //theStage
        label.setText(AppOld.selectedPreparedFile.getPath());
    }


    public void buttonPrepareGroupsClick() {
        if (AppOld.selectedFile != null) {
            if (!ConfigFile.configLists()) return;
            GlobalResult gr = AppOld.processForGroups(AppOld.selectedFile);
            File pre = gr.getPrepareGroupsFile();

            //TODO for twoFiles
            //String s = AppOld.process_with_compare(null, AppOld.selectedFile).getName();
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
        if (AppOld.selectedPreparedFile != null) {
            GroupsMaker gm = new GroupsMaker(AppOld.selectedPreparedFile);
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
