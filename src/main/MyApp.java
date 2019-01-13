package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MyApp extends Application {

    public static File selectedFile;
    public static File selectedOldFile;

    private static String[] listTak;
    private static String[] listIrch;
    private static double bonusUp;
    private static double bonusDown;
    private static String irchUp;
    private static String irchDown;

    private static double note1;
    private static double note2;
    private static double note3;
    private static double note4;
    private static double moy = 0.0d;
    private static double note1b;
    private static double note2b;
    private static double note3b;
    private static double note4b;
    private static double moyb = 0.0d;
    private static File out1 = new File("resources/data01.bin");
    private static File out2 = new File("resources/data02.bin");
    private Scene scene;

    public static void main(String args[]) throws Exception {

        //to create files data01_01.data and data02_02.data for crypted images just one time
        //IsmCommonUtils.encryptDES("resources/ldata01.bin", "resources/data01_01.data", "keya8585"); //just one time size key must be 8
        //IsmCommonUtils.encryptDES("resources/data02.bin", "resources/data02_02.data", "keya8585"); //just one time size key must be 8
        IsmCommonUtils.encryptDES("resourcesOriginal/info2.png", "resources/data02.data", "keya8585"); //just one time size key must be 8

        //to restore images from crypted files then creates it, they will be deleted after close mainStage of app
        BufferedImage bi1 = IsmCommonUtils.decryptDES_picture("resources/data01.data", "keya8585");
        BufferedImage bi2 = IsmCommonUtils.decryptDES_picture("resources/data02.data", "keya8585");
        ImageIO.write(bi1, "png", out1);
        ImageIO.write(bi2, "png", out2);


        Application.launch(args);
        //File file = new File("C:\\Users\\ismail\\Desktop\\test.Xls");
        //process(file);
    }

    public static boolean configLists() {

        //File fileConfig = new File("C:\\Users\\ismail\\Desktop\\config.Xls"); // TODO for dev
        File fileConfig = new File("resources/config.Xls"); // for prod
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

            if (bonusUp < 0) bonusUp = -bonusUp;
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
                System.out.println(" de " + Integer.toString(row - 2) + "  à " + Integer.toString(row - 2) + ",99 " + listTak[row - 2]);
/*                HSSFCell cell0 = sheet.getRow(row).getCell(0);
                HSSFCell cell1 = sheet.getRow(row).getCell(1);
                HSSFCell cell2 = sheet.getRow(row).getCell(2);
                HSSFCell cell3 = sheet.getRow(row).getCell(3);
                HSSFCell cell4 = sheet.getRow(row).getCell(4);
                HSSFCell cell5 = sheet.getRow(row).getCell(5);*/
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

    public static File process_with_compare(File fileTri1, File fileCible) {

        String fileName = fileCible.getName();
        System.out.println("absolute path : " + fileCible.getPath());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        fileName = fileCible.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + التقديرات" + fileName.substring(lastDot);

        File fileResult = new File(fileName);
        HSSFWorkbook workbookTri1;
        HSSFWorkbook workbookCible = null;
        try {
            FileInputStream fisTri1 = new FileInputStream(fileTri1);
            FileInputStream fisCible = new FileInputStream(fileCible);

            //Get the workbook instance for XLSX file
            workbookTri1 = new HSSFWorkbook(fisTri1);
            workbookCible = new HSSFWorkbook(fisCible);

            for (int i = 0; i < workbookCible.getNumberOfSheets(); i++) {


                HSSFSheet sheetCible = workbookCible.getSheetAt(i);
                HSSFSheet sheetTri1 = workbookTri1.getSheetAt(i);
                System.out.println("hh =" + sheetCible.getSheetName());

                if (sheetCible.getRow(7).getCell(0).getStringCellValue().isEmpty()
                        || sheetCible.getSheetName().equals("الواجهة")) {
                    break;
                }

                FileOutputStream fos = new FileOutputStream(fileResult);
                //FileOutputStream fos = new FileOutputStream(file);
                int r = 8;

                while (!sheetCible.getRow(r).getCell(1).getStringCellValue().isEmpty()) {

                    System.out.println(" row num " + r);
                    System.out.println(" sheetCible.getRow(r).getCell(1).getStringCellValue() " + sheetCible.getRow(r).getCell(1).getStringCellValue());
                    HSSFCell cellCible = sheetCible.getRow(r).getCell(3);

                    note1 = sheetCible.getRow(r).getCell(3).getNumericCellValue();

                    note3 = sheetCible.getRow(r).getCell(5).getNumericCellValue();
                    note4 = sheetCible.getRow(r).getCell(6).getNumericCellValue();

                    HSSFCell cellb = sheetTri1.getRow(r).getCell(3);

                    note1b = sheetTri1.getRow(r).getCell(3).getNumericCellValue();

                    note3b = sheetTri1.getRow(r).getCell(5).getNumericCellValue();
                    note4b = sheetTri1.getRow(r).getCell(6).getNumericCellValue();

                    //if (sheetCible.getRow(r).getCell(4).getNumericCellValue() != 0) {//TODO
                    if (sheetCible.getRow(r).getCell(4).getCellTypeEnum() != CellType.NUMERIC) {//TODO
                        //if (sheetCible.getRow(r).getCell(4) == null || sheetCible.getRow(r).getCell(4).getCellTypeEnum() == CellType.BLANK) {
                        moy = (note1 + note3 + (note4 * 2)) / 4;
                        moyb = (note1b + note3b + (note4b * 2)) / 4;

                    } else {
                        note2 = sheetCible.getRow(r).getCell(4).getNumericCellValue();
                        moy = (note1 + note2 + note3 + (note4 * 2)) / 5;
                        note2b = sheetTri1.getRow(r).getCell(4).getNumericCellValue();
                        moyb = (note1b + note2b + note3b + (note4b * 2)) / 5;
                    }

                    sheetCible.getRow(r).getCell(7).setCellValue(listTak[((int) moy)]);
                    sheetCible.getRow(r).getCell(8).setCellValue(irchadateByCompare(moyb, moy));

                    //}


                    r++;


                }

                workbookCible.write(fos);
                fos.close();

                //messageLabel.setText("تم إنشاء الملف المعالج بنجاح \""+ fileResult.getName() + "\"");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Alert alrt = new Alert(Alert.AlertType.WARNING);
            alrt.setHeaderText(null);
            alrt.setContentText("إغلق الملف " + fileResult.getName() + " ثم أعد الضغط على الزر Traiter");
            alrt.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileResult;
    }

    private static String irchadateByCompare(double moyTri1, double moyTri2) {
        String s = "";
        double bonus = moyTri2 - moyTri1;

        if (bonus >= bonusUp && moyTri2 > 12) {
            s = irchUp;
        } else if (bonus <= -bonusDown && moyTri2 < 13 && moyTri2 >= 8) {
            s = irchDown;
        } else {
            s = listIrch[(int) moyTri2];
        }
        return s;
    }

    //************************************************************
    public static File process(File file) {

        String fileName = file.getName();
        System.out.println("absolute path : " + file.getParentFile());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        fileName = file.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + التقديرات" + fileName.substring(lastDot);

        File fileResult = new File(fileName);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);


            //Get the workbook instance for XLSX file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);


            if (file.isFile() && file.exists()) {
                System.out.println(
                        "openworkbook.xlsx file open successfully.");
            } else {
                System.out.println(
                        "Error to open openworkbook.xlsx file.");
            }

            int nbstudents = 0;
            System.out.println("NumberOfSheets  = " + workbook.getNumberOfSheets());

            for (int i = 0; i < workbook.getNumberOfSheets() - 3; i++) {
                System.out.println("i  = " + i);


                HSSFSheet sheet = workbook.getSheetAt(i);
                FileOutputStream fos = new FileOutputStream(fileResult);
                //FileOutputStream fos = new FileOutputStream(file);
                int r = 8;

                while (!sheet.getRow(r).getCell(1).getStringCellValue().isEmpty()) {
                    nbstudents++;
                    System.out.println(" row num " + r);
                    HSSFCell cell = sheet.getRow(r).getCell(3);
                    note1 = sheet.getRow(r).getCell(3).getNumericCellValue();

                    note3 = sheet.getRow(r).getCell(5).getNumericCellValue();
                    note4 = sheet.getRow(r).getCell(6).getNumericCellValue();


                    if (sheet.getRow(r).getCell(4).getCellTypeEnum() == CellType.NUMERIC) {//TODO
                        note2 = sheet.getRow(r).getCell(4).getNumericCellValue();
                        moy = (note1 + note2 + note3 + (note4 * 2)) / 5;
                    } else {
                        moy = (note1 + note3 + (note4 * 2)) / 4;
                    }
                    System.out.println(moy);
                    //if (nbstudents < 96) {

                    sheet.getRow(r).getCell(7).setCellValue(listTak[(int) moy]);
                    sheet.getRow(r).getCell(8).setCellValue(listIrch[(int) moy]);

                    //}

                    System.out.println("number students = " + nbstudents);

                    r++;

                }

                workbook.write(fos);
                fos.close();

                //messageLabel.setText("تم إنشاء الملف المعالج بنجاح \""+ fileResult.getName() + "\"");
            }
        } catch (FileNotFoundException e) {
            Alert alrt2 = new Alert(Alert.AlertType.WARNING);
            alrt2.setHeaderText(null);
            alrt2.setContentText("إغلق الملف " + fileResult.getName() + " ثم أعد الضغط على الزر Traiter");
            alrt2.showAndWait();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileResult;
    }

    private static String getNoteFromFile(double moy, File file) {

        if (file.isFile() && file.exists()) {
            System.out.println(file.getName() +
                    " file open successfully.");
        } else {
            System.out.println(
                    "Error to open the file" + file.getName());
        }
        try {
            //fis = new FileInputStream(fileTak);
            //InputStreamReader isr = new InputStreamReader(new FileInputStream("takdirate.txt"),"UTF-8");//for prod
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file.getPath()), "UTF-8");//for dev
            BufferedReader bufferedReader = new BufferedReader(isr);
            String[] list = new String[25];
            int i = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list[i] = line;
                System.out.println(line);
                i++;
            }
            return list[(int) moy + 1];


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void openConfigFile() {
        //File fileConfig = new File("C:\\Users\\ismail\\Desktop\\config.Xls"); // TODO for dev
        File fileConfig = new File("resources/config.Xls"); // for prod
        try {
            Desktop.getDesktop().open(fileConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("'التطبيق واصل' , تطبيق إضافة التقديرات والملاحظات" + "                                 WassilApp v2.1");

        primaryStage.setWidth(0.6 * BasicScene.screenSize.getWidth());
        primaryStage.setHeight(0.66 * BasicScene.screenSize.getHeight());

        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (out1.exists()) {
                    out1.delete();
                }
                if (out2.exists()) {
                    out2.delete();
                }
            }
        });

        //scene = new OneFileScene(new BorderPane());
        scene = new BasicScene(new BorderPane());

        //scene = new main.OneFileScene(new BorderPane());

        //scene.prepare();
        //scene2.getStylesheets().add(MyApp.class.getResource("myStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}