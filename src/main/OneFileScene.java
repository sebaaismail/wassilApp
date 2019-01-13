package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import view.OneFileController;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Ismail on 23/12/2018.
 */
class OneFileScene extends BasicScene {
    public OneFileScene(Parent root) {
        super(root);
        FXMLLoader loader = new FXMLLoader();

        GridPane gp = null;
        try {
            OneFileController cont = new OneFileController();
            loader.setController(cont);

            gp = loader.load(getClass().getResource("/view/OneFile.fxml").openStream());
            this.getStylesheets().add(MyApp.class.getResource("myStyle.css").toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        border.setCenter(gp);
    }
}
