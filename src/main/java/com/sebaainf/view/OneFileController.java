package com.sebaainf.view;

import com.sebaainf.ismPoiLib.IPoiObject;
import com.sebaainf.ismPoiLib.IPoiResults;
import com.sebaainf.ismPoiLib.IPoiWorkbookCalculator;
import com.sebaainf.ismPoiLib.MyFileWriter;
import com.sebaainf.main.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

        if (AppOld.selectedFile != null) {
            if (!ConfigFile.configLists()) return;

            MyFileParser parser = new MyFileParser(AppOld.selectedFile);

            IPoiWorkbookCalculator calculator = new IPoiWorkbookCalculator(parser.parse());
            calculator.calculate();
            Map<IPoiObject, IPoiResults> results = calculator.getResults();
            Map<Row, IPoiObject> placeObj = calculator.getPlaceObjs();
            MyFileWriter writer = new MyFileWriter(parser);

            File res = writer.write(results, placeObj);

            //File res = gr.getExcelResult();
            //File resAnalyseFile = gr.getAnalyseFile();
            //TODO for twoFiles
            //String s = AppOld.process_with_compare(null, AppOld.selectedFile).getName();
            messageLabel.setText("تم إنشاء الملف المعالج بنجاح \"" + res.getName() + "\"");
            try {
                //wait(3000);
                Desktop.getDesktop().open(res);
                //Desktop.getDesktop().open(resAnalyseFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else System.out.println("selectedFile is null");
    }

    public void configClick() {
        ConfigFile.openConfigFile();
    }


    public void buttonOneClick() {
        String userDir = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
        fileChooser.setTitle("Selectionner le Fichier excel...");
        AppOld.selectedFile = fileChooser.showOpenDialog(null); //theStage
        label.setText(AppOld.selectedFile.getPath());
    }
}
