package com.sebaainf.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import com.sebaainf.view.BasicWinController;
import com.sebaainf.view.InfoController;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Ismail on 23/12/2018.
 */
public class BasicScene extends Scene {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    BorderPane border = (BorderPane) this.getRoot();
    Label label = new Label();
    Label messageLabel = new Label();
    javafx.scene.image.Image logo, info;
    javafx.scene.control.Button config = new Button("Config");

    @SuppressWarnings("AccessStaticViaInstance")
    public BasicScene(Parent root) {


        super(root);

        FXMLLoader loader = new FXMLLoader();
        FXMLLoader loader2 = new FXMLLoader();

        VBox vBox = null;
        TabPane tp = null;

        try {
            InfoController cont = new InfoController();
            BasicWinController cont2 = new BasicWinController();
            loader.setController(cont);
            loader2.setController(cont2);


            vBox = loader.load(Thread.currentThread().getContextClassLoader().getResource("Info.fxml").openStream());

            tp = loader2.load(Thread.currentThread().getContextClassLoader().getResource("BasicWin.fxml"));
            this.getStylesheets().add(
                    Thread.currentThread().getContextClassLoader().getResource("myStyle.css").toExternalForm());
            //AquaFx.style();
            //AeroFX.style();
            //FlatterFX.style();




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        border.setCenter(tp);
        border.setRight(vBox);

    }


}
