package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.BasicWinController;
import view.InfoController;

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


            vBox = loader.load(getClass().getResource("/view/Info.fxml").openStream());


            tp = loader2.load(getClass().getResource("/view/BasicWin.fxml"));
            this.getStylesheets().add(MyApp.class.getResource("myStyle.css").toExternalForm());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //*/
        /*
        VBox vBox = new VBox();
        Pane blankPane = new Pane();
        blankPane.setMinHeight(0.15 * screenSize.getHeight());
        try {
            this.getStylesheets().add(MyApp.class.getResource("myStyle.css").toExternalForm());
            logo = new Image(new FileInputStream("files/logo.png"));
            info = new Image(new FileInputStream("files/info.png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView iv1 = new ImageView(logo);
        ImageView iv2 = new ImageView(info);



        vBox.setMaxWidth(0.25 * screenSize.getWidth());
        //iv2.setFitWidth(vBox.getWidth());
        //iv2.setFitWidth(0.25 * screenSize.getWidth());
        iv2.setPreserveRatio(true);
        //iv2.setSmooth(true);
        //iv2.setCache(true);

        vBox.setAlignment(Pos.TOP_CENTER);
        //vBox.setSpacing(0.2 * screenSize.getHeight());

        TextField textField = new TextField("by: sebaa.ismail@gmail.com           ©2018");
        textField.setEditable(false);
        textField.setFocusTraversable(false);

        vBox.getChildren().add(iv1);
        vBox.getChildren().add(blankPane);
        vBox.getChildren().add(iv2);
        vBox.getChildren().add(textField);
        //*/
        border.setCenter(tp);
        border.setRight(vBox);

    }


}
