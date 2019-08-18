package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seatsPlan.view.MakingGroupsController;


/**
 * Created by Ismail on 29/12/2018.
 */
public class BasicWinController {

    @FXML
    Button buttonOne = null;
    @FXML
    Label label = new Label();
    @FXML
    private TabPane tp = new TabPane();
    @FXML
    private Tab tab1 = new Tab();
    @FXML
    private Tab tab2 = new Tab();
    @FXML
    private Tab tab3 = new Tab();

    @FXML
    private OneFileController oneFileController;
    @FXML
    private TwoFilesController twoFilesController;
    @FXML
    private MakingGroupsController makingGroupsController;

    private void initialize() {
        //this.textField.setText("wiwwwwwwwwwwwwww");
        //this.blankPane.setMinHeight(0.15 * BasicScene.screenSize.getHeight());
        //this.vBox.setMaxWidth(0.25 * BasicScene.screenSize.getWidth());
    }

    @FXML
    private void buttonOneClick() {
        oneFileController.buttonOneClick();
    }

    @FXML
    private void buttonCalcClick() {
        oneFileController.buttonCalcClick();
    }
}
