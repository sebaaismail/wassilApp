package com.sebaainf.main;

import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Ismail on 26/08/2019.
 */
public class ConfigFile {


    protected static String[] listTak;
    protected static String[] listIrch;
    protected static double bonusUp;
    protected static double bonusDown;
    protected static String irchUp;
    protected static String irchDown;

    public static boolean configLists() {

        File fileConfig = new File("C:\\Users\\ismail\\Desktop\\config.Xls"); // TODO for dev
        //File fileConfig = new File("config.Xls"); // for prod
        listTak = new String[21];
        listIrch = new String[21];


        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileConfig);

            //Get the workbook instance for XLSX file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet("Config");

            bonusUp = sheet.getRow(6).getCell(7).getNumericCellValue();
            bonusDown = sheet.getRow(7).getCell(7).getNumericCellValue();
            irchUp = sheet.getRow(6).getCell(8).getStringCellValue();
            irchDown = sheet.getRow(7).getCell(8).getStringCellValue();

            if (bonusUp < 0) bonusUp = - bonusUp;
            if (bonusDown < 0) bonusDown = -bonusDown;

            if (!(bonusUp >= 1.5 && bonusUp <= 5) || !(bonusDown >= 1.5 && bonusDown <= 5)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطأ في  قيمة الفارق");
                alert.setHeaderText(null);
                alert.setContentText("قم بتعديل الفارق في الملف Config، حيث انه يجب ان يكون بين 1.5  و 5");
                alert.showAndWait();
                //System.exit(0);
                return false;
            }

            int row = 2;

            for (row = 2; row <= 22; row++) {
                listTak[row - 2] = sheet.getRow(row).getCell(3).getStringCellValue();
                listIrch[row - 2] = sheet.getRow(row).getCell(4).getStringCellValue();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void openConfigFile() {
        File fileConfig = new File("C:\\Users\\ismail\\Desktop\\config.Xls"); // TODO for dev
        //File fileConfig = new File("resources/config.Xls"); // for prod
        try {
            Desktop.getDesktop().open(fileConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
