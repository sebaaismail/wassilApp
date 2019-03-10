package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import main.BasicScene;
import main.MyApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ismail on 26/12/2018.
 */
public class OneFileController {
    @FXML
    GridPane gp = null;
    @FXML
    Pane pane = null;
    @FXML
    Button buttonOne = null;
    @FXML
    Button buttonCalc = new Button();
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

    public void buttonCalcClick() {

        if (MyApp.selectedFile != null) {
            if (!MyApp.configLists()) return;
            File res = MyApp.process(MyApp.selectedFile).getExcelResult();
            File resAnalyseFile = MyApp.process(MyApp.selectedFile).getAnalyseFile();
            //TODO for twoFiles
            //String s = MyApp.process_with_compare(null, MyApp.selectedFile).getName();
            messageLabel.setText("تم إنشاء الملف المعالج بنجاح \"" + res.getName() + "\"");
            try {
                //wait(3000);
                Desktop.getDesktop().open(res);
                Desktop.getDesktop().open(resAnalyseFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else System.out.println("selectedFile is null");
    }

    public void configClick() {
        MyApp.openConfigFile();
    }


    public void buttonOneClick() {
        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le Fichier excel...");
        MyApp.selectedFile = fileChooser.showOpenDialog(null); //theStage
        label.setText(MyApp.selectedFile.getPath());
    }
}
