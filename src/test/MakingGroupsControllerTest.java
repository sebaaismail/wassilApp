package test;

import junit.framework.TestCase;
import main.GroupsMaker;
import main.MyApp;
import view.MakingGroupsController;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Ismail on 14/08/2019.
 */
public class MakingGroupsControllerTest extends TestCase {


    public static void main(String[] args) {

        MyApp.selectedPreparedFile = new File("C:\\Users\\Ismail\\Desktop\\sbaa + تحضير الأفواج.xls");

        if (MyApp.selectedPreparedFile != null) {
            GroupsMaker gm = new GroupsMaker(MyApp.selectedPreparedFile);
            File pre = gm.run();

            try {
                //wait(3000);
                Desktop.getDesktop().open(pre);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void testButtonGroupingClick() throws Exception {
        MyApp.selectedPreparedFile = new File("C:\\Users\\Ismail\\Desktop\\testGrouping.xls");

        MakingGroupsController mgc = new MakingGroupsController();
        File f = mgc.buttonGroupingClick();
        assertTrue(!f.equals(null));
    }
}