package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.examples.Borders;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MyApp extends Application {

    public static File selectedFile;
    public static File selectedOldFile;
    public static File selectedPreparedFile;

    public static HSSFSheet analyseSheet, analyseExSheet;

    protected static String[] listTak;
    protected static String[] listIrch;
    private static double bonusUp;
    private static double bonusDown;
    private static String irchUp;
    private static String irchDown;

    private static double note1;
    private static double note2;
    private static double note3;
    private static double note4;

    private static String id;
    private static String fullName;

    //private static double moy, moyClasse, maxMoy, minMoy = 0.0d;
    private static double moy;

    private static File out1 = new File("resources/data01.bin");
    private static File out2 = new File("resources/data02.bin");
    private Scene scene;

    public static void main(String args[]) throws Exception {

        //to create files data01_01.data and data02_02.data for crypted images just one time
        //IsmCommonUtils.encryptDES("resources/ldata01.bin", "resources/data01_01.data", "keya8585"); //just one time size key must be 8
        //IsmCommonUtils.encryptDES("resources/data02.bin", "resources/data02_02.data", "keya8585"); //just one time size key must be 8
        //TODO in dev when we went change pictures
        //IsmCommonUtils.encryptDES("resourcesOriginal/info2.png", "resources/data02.data", "keya8585"); //just one time size key must be 8

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

    public static GlobalResult process_with_compare(File fileTri1, File fileCible) {

        GlobalResult globalRes = new GlobalResult();
        Student student = new Student();

        String fileName = fileCible.getName();
        System.out.println("absolute path : " + fileCible.getPath());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        fileName = fileCible.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + التقديرات" + fileName.substring(lastDot);

        File fileResult = new File(fileName);
        File fileAnalyse = new File(fileCible.getParentFile() + "\\" + "تحليل النتائج.xls");

        HSSFWorkbook workbookTri1;
        HSSFWorkbook workbookCible = null;
        try {
            FileInputStream fisTri1 = new FileInputStream(fileTri1);
            FileInputStream fisCible = new FileInputStream(fileCible);

            //Get the workbook instance for XLSX file
            workbookTri1 = new HSSFWorkbook(fisTri1);
            workbookCible = new HSSFWorkbook(fisCible);

            HSSFWorkbook workbookAnalyse = new HSSFWorkbook();

            HSSFCellStyle style = workbookAnalyse.createCellStyle();
            style.setDataFormat(workbookAnalyse.createDataFormat().getFormat("0.00"));

            analyseSheet = workbookAnalyse.createSheet("تحليل النتائج العامة");
            analyseSheet.setRightToLeft(true);

            analyseExSheet = workbookAnalyse.createSheet("تحليل نتائج الإختبار");
            analyseExSheet.setRightToLeft(true);

            //Iterate classes
            for (int i = 0; i < workbookCible.getNumberOfSheets(); i++) {// for each i there is classe

                UneClasse classeRes = new UneClasse();
                classeRes.setDown(0);
                classeRes.setUp(0);


                HSSFSheet sheetCible = workbookCible.getSheetAt(i);
                HSSFSheet sheetTri1 = workbookTri1.getSheetAt(i);
                System.out.println("hh =" + sheetCible.getSheetName());

                FileOutputStream fos = new FileOutputStream(fileResult);
                FileOutputStream fosAnalyse = new FileOutputStream(fileAnalyse);

                int r = 8; // old: r = 8;

                int takdirateColumn = 8;

                ArrayList<Student> listB = new ArrayList();
                //iterate students
                Iterator<Row> rowIterator = sheetCible.rowIterator();
                Student stdB;

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                //while (!sheetCible.getRow(r).getCell(1).getStringCellValue().isEmpty()) {
                    if (row.getRowNum() >= 8) {


                        //if file has not TP column
                        if (sheetCible.getRow(7).getCell(9) == null) {
                            classeRes.setHasNoteTP(false);
                            takdirateColumn = 7;
                        } else{
                            classeRes.setHasNoteTP(true);
                        }


                        student = new Student(sheetCible.getRow(r), classeRes.getHasNoteTP(), true);
                        stdB = new Student(sheetTri1.getRow(r), classeRes.getHasNoteTP(), true);

                        student.setNumRow(r);
                        System.out.println(" row num " + r);

                        stdB.setNumRow(r);

                        listB.add(stdB);
                        classeRes.addStudent(student);

                        globalRes.add(classeRes);
                        r++;

                    }

                }

                classeRes.process_with_compare(sheetCible, sheetTri1, analyseSheet, analyseExSheet, r, i, listB);


                //TODO classeRes.process(sheet, analyseSheet, r, i);


                workbookCible.write(fos);
                fos.close();
                workbookAnalyse.write(fosAnalyse);
                fosAnalyse.close();

                //messageLabel.setText("تم إنشاء الملف المعالج بنجاح \""+ fileResult.getName() + "\"");
            }
            globalRes.setExcelResult(fileResult);
            globalRes.setAnalyseFile(fileAnalyse);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Alert alrt = new Alert(Alert.AlertType.WARNING);
            alrt.setHeaderText(null);
            alrt.setContentText("إغلق الملف " + fileResult.getName() + " ثم أعد الضغط على الزر Traiter");
            alrt.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return globalRes;
    }

    protected static String irchadateByCompare(double moyTri1, double moyTri2) {
        String s = "";
        double bonus = moyTri2 - moyTri1;

        if (bonus >= bonusUp && moyTri2 > 12) {
            s = irchUp;
            System.out.println(s);
        } else if (bonus <= -bonusDown && moyTri2 < 13 && moyTri2 >= 8) {
            s = irchDown;
            System.out.println(s);
        } else {
            s = listIrch[(int) moyTri2];
        }
        System.out.println("irchadateByCompare ( " + moyTri1 + "," + moyTri2 + " ) : " + s);
        return s;
    }


    //************************************************************
    public static GlobalResult process(File file) {


        GlobalResult globalRes = new GlobalResult();
        Student student = new Student();

        String fileName = file.getName();
        System.out.println("absolute path : " + file.getParentFile());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        File fileAnalyse = null;


            fileName = file.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + التقديرات" + fileName.substring(lastDot);
            fileAnalyse = new File(file.getParentFile() + "\\" + "تحليل النتائج.xls");


        File fileResult = new File(fileName);
        File filePrepareGr = new File(fileName); //TODO



        FileInputStream fis = null;


        try {
            fis = new FileInputStream(file);


            //Get the workbook instance for XLSX file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFWorkbook workbookAnalyse = new HSSFWorkbook();


                HSSFCellStyle style = workbookAnalyse.createCellStyle();
                style.setDataFormat(workbookAnalyse.createDataFormat().getFormat("0.00"));

                analyseSheet = workbookAnalyse.createSheet("تحليل النتائج العامة");
                analyseSheet.setRightToLeft(true);

                analyseExSheet = workbookAnalyse.createSheet("تحليل نتائج الإختبار");
                analyseExSheet.setRightToLeft(true);



            if (file.isFile() && file.exists()) {
                System.out.println(
                        "openworkbook.xlsx file open successfully.");
            } else {
                System.out.println(
                        "Error to open openworkbook.xlsx file.");
            }

            int nbstudents = 0;
            System.out.println("NumberOfSheets  = " + workbook.getNumberOfSheets());

            //for (int i = 0; i < workbook.getNumberOfSheets() - 3; i++) { // old version files

            //iterate classes
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) { // for each i there is classe

                UneClasse classeRes = new UneClasse();

                System.out.println("i  = " + i);

//                HSSFSheet analyseSheet = workbookAnalyse.createSheet("تحليل النتائج" + i);
//                analyseSheet.setRightToLeft(true);


                HSSFSheet sheet = workbook.getSheetAt(i);

                System.out.println("feuille : " +  sheet.getSheetName());


                FileOutputStream fos = new FileOutputStream(fileResult);
                FileOutputStream fosAnalyse = null;

                    fosAnalyse = new FileOutputStream(fileAnalyse);

                //FileOutputStream fos = new FileOutputStream(file);

                int r = 8; // r = 8


                //Cell ccc = sheet.getRow(34).getCell(1);

                //System.out.println(sheet.getRow(34).getCell(1).getStringCellValue());
                Iterator<Row> rowIterator = sheet.rowIterator();

                int takdirateColumn = 8;
                //iterate students
                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();

                    //if ((row.getRowNum() >= 8 && nbstudents != 26 && nbstudents != 76 & i!=3)|| (row.getRowNum() >= 8 && i==3 && r != 20)){
                    if (row.getRowNum() >= 8 ){
                        //System.out.println("row cell" + row.getCell(0).getStringCellValue());
                        //while (!sheet.getRow(r).getCell(1).getStringCellValue().isEmpty()) {  //old version of files
                        nbstudents++;


                            //if file has not TP column
                            if (sheet.getRow(7).getCell(9) == null) {
                                classeRes.setHasNoteTP(false);
                                takdirateColumn = 7;
                            } else {
                                classeRes.setHasNoteTP(true);
                            }

                            student = new Student(sheet.getRow(r), classeRes.getHasNoteTP(), false);

                        student.setNumRow(r);
                        System.out.println(" row num " + r);

                        classeRes.addStudent(student);

                        System.out.println("number students = " + nbstudents);
                        globalRes.add(classeRes);
                        r++;
                    }

                }


                    classeRes.process(sheet, analyseSheet, analyseExSheet, r, i);

                    workbookAnalyse.write(fosAnalyse);
                    fosAnalyse.close();


                workbook.write(fos);
                fos.close();



                //messageLabel.setText("تم إنشاء الملف المعالج بنجاح \""+ fileResult.getName() + "\"");
            }

                globalRes.setExcelResult(fileResult);
                globalRes.setAnalyseFile(fileAnalyse);

        } catch (FileNotFoundException e) {
            Alert alrt2 = new Alert(Alert.AlertType.WARNING);
            alrt2.setHeaderText(null);
            alrt2.setContentText("إغلق الملف " + fileResult.getName() + " ثم أعد الضغط على الزر Traiter");
            alrt2.showAndWait();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return globalRes;
    }
    //************************************************************
    public static GlobalResult processForGroups(File file) { //TODO


        GlobalResult globalRes = new GlobalResult();
        Student student = new Student();

        String fileName = file.getName();
        System.out.println("absolute path : " + file.getParentFile());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        File fileAnalyse = null;


            fileName = file.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + تحضير الأفواج" + fileName.substring(lastDot);


        File fileResult = new File(fileName);
        File filePrepareGr = new File(fileName); //TODO



        FileInputStream fis = null;


        try {
            fis = new FileInputStream(file);


            //Get the workbook instance for XLSX file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFWorkbook workbookAnalyse = new HSSFWorkbook();


            if (file.isFile() && file.exists()) {
                System.out.println(
                        "openworkbook.xlsx file open successfully.");
            } else {
                System.out.println(
                        "Error to open openworkbook.xlsx file.");
            }

            int nbstudents = 0;
            System.out.println("NumberOfSheets  = " + workbook.getNumberOfSheets());

            //for (int i = 0; i < workbook.getNumberOfSheets() - 3; i++) { // old version files

            FileOutputStream fos = new FileOutputStream(fileResult);
            FileOutputStream fosAnalyse = null;

            int n = workbook.getNumberOfSheets();
            //iterate classes
            for (int i = 0; i < n; i++) { // for each i there is classe

                UneClasse classeRes = new UneClasse();

                System.out.println("i  = " + i);

//                HSSFSheet analyseSheet = workbookAnalyse.createSheet("تحليل النتائج" + i);
//                analyseSheet.setRightToLeft(true);


                HSSFSheet sheet = workbook.getSheetAt(i);


                String nameSheet = sheet.getSheetName();
                workbook.setSheetName(i, "to Delete " + i);
                HSSFSheet newSheet = workbook.createSheet(nameSheet);
                newSheet.setRightToLeft(true);


                System.out.println("feuille : " + sheet.getSheetName());


                //FileOutputStream fos = new FileOutputStream(file);

                int r = 8; // r = 8


                //Cell ccc = sheet.getRow(34).getCell(1);

                //System.out.println(sheet.getRow(34).getCell(1).getStringCellValue());
                Iterator<Row> rowIterator = sheet.rowIterator();


                int takdirateColumn = 8;
                //iterate students
                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();

                    //if ((row.getRowNum() >= 8 && nbstudents != 26 && nbstudents != 76 & i!=3)|| (row.getRowNum() >= 8 && i==3 && r != 20)){
                    if (row.getRowNum() >= 8) {
                        //System.out.println("row cell" + row.getCell(0).getStringCellValue());
                        //while (!sheet.getRow(r).getCell(1).getStringCellValue().isEmpty()) {  //old version of files
                        nbstudents++;

                        System.out.println(" current sheet :  " + nameSheet);

                        student = new Student(sheet.getRow(r));

                        student.setNumRow(r);
                        System.out.println(" row num " + r + " student : " + student.getFullName());

                        classeRes.addStudent(student);

                        System.out.println("number students = " + nbstudents);
                        globalRes.add(classeRes);
                        r++;
                    }

                }


                int size = classeRes.getListStudents().size();
                classeRes.setGroupeA(new ArrayList<>());
                classeRes.setGroupeB(new ArrayList<>());
                int j = 0;

                CellStyle style = workbook.createCellStyle();
                HSSFFont font =workbook.createFont();
                font.setFontHeightInPoints((short)20);

                style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                CellStyle style2 =  workbook.createCellStyle();
                style2.setFillForegroundColor(HSSFColor.RED.index);
                style2.setFont(font);

                newSheet.createRow(2);
                newSheet.getRow(2).createCell(6);
                newSheet.getRow(2).getCell(6).setCellStyle(style2);
                newSheet.getRow(2).getCell(6).setCellValue("ضع حرف H امام التلاميذ ذكور");

                newSheet.createRow(4);
                newSheet.getRow(4).createCell(5);
                newSheet.getRow(4).createCell(6);
                newSheet.getRow(4).createCell(7);

                newSheet.getRow(4).getCell(5).setCellStyle(style);
                newSheet.getRow(4).getCell(6).setCellStyle(style);
                newSheet.getRow(4).getCell(7).setCellStyle(style);


                newSheet.getRow(4).getCell(5).setCellValue("الرقم");
                newSheet.getRow(4).getCell(6).setCellValue("الإسم واللقب");
                newSheet.getRow(4).getCell(7).setCellValue("الجنس");

                newSheet.createRow(((size+1) / 2) + 7);
                newSheet.getRow(((size+1) / 2) + 7).createCell(5);
                newSheet.getRow(((size+1) / 2) + 7).createCell(6);
                newSheet.getRow(((size+1) / 2) + 7).createCell(7);

                newSheet.getRow(((size+1) / 2) + 7).getCell(5).setCellValue("الرقم");
                newSheet.getRow(((size+1) / 2) + 7).getCell(6).setCellValue("الإسم واللقب");
                newSheet.getRow(((size+1) / 2) + 7).getCell(7).setCellValue("الجنس");

                newSheet.getRow(((size+1) / 2) + 7).getCell(5).setCellStyle(style);
                newSheet.getRow(((size+1) / 2) + 7).getCell(6).setCellStyle(style);
                newSheet.getRow(((size+1) / 2) + 7).getCell(7).setCellStyle(style);


                for (Student st : classeRes.getListStudents()) {

                    if (j < (size+1) / 2) {
                        newSheet.createRow(j + 5);
                        newSheet.getRow(j + 5).createCell(5);
                        newSheet.getRow(j + 5).createCell(6);
                        newSheet.getRow(j + 5).createCell(7);

                        classeRes.getGroupeA().add(st);

                        newSheet.getRow(j + 5).getCell(5).setCellValue(j + 1);
                        newSheet.getRow(j + 5).getCell(6).setCellValue(st.getFullName());

                        newSheet.getRow(j + 5).getCell(5).setCellStyle(style);
                        newSheet.getRow(j + 5).getCell(6).setCellStyle(style);
                        newSheet.getRow(j + 5).getCell(7).setCellStyle(style);

                    } else {
                        classeRes.getGroupeB().add(st);
                        newSheet.createRow(j + 8);
                        newSheet.getRow(j + 8).createCell(5);
                        newSheet.getRow(j + 8).createCell(6);
                        newSheet.getRow(j + 8).createCell(7);

                        newSheet.getRow(j + 8).getCell(5).setCellValue(j + 1);
                        newSheet.getRow(j + 8).getCell(6).setCellValue(st.getFullName());

                        newSheet.getRow(j + 8).getCell(5).setCellStyle(style);
                        newSheet.getRow(j + 8).getCell(6).setCellStyle(style);
                        newSheet.getRow(j + 8).getCell(7).setCellStyle(style);

                    }

                    j++;
                }
                //workbook.write(fos);
                newSheet.autoSizeColumn(6);

            }


                //fos.close();



                //messageLabel.setText("تم إنشاء الملف المعالج بنجاح \""+ fileResult.getName() + "\"");
            //}

            ArrayList listIndexes = new ArrayList();
            String nameSheet;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                nameSheet = workbook.getSheetAt(i).getSheetName();
                if (!org.apache.commons.lang3.StringUtils.isNumeric(nameSheet)) {
                    listIndexes.add(nameSheet);
                    //workbook.removeSheetAt(i);
                    //workbook.write(fos);
                    System.out.println(workbook.getSheetAt(i).getSheetName() + " is deleted ........");
                }
            }

            for(Object name :listIndexes){
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    if (workbook.getSheetAt(i).getSheetName().equals(name)) {
                        workbook.removeSheetAt((Integer) i);
                    }
                }
            }
            workbook.write(fos);
            fos.close();

                globalRes.setPrepareGroupsFile(fileResult);

        } catch (FileNotFoundException e) {
            Alert alrt2 = new Alert(Alert.AlertType.WARNING);
            alrt2.setHeaderText(null);
            alrt2.setContentText("إغلق الملف " + fileResult.getName() + " ثم أعد الضغط على الزر Traiter");
            alrt2.showAndWait();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return globalRes;
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
        File fileConfig = new File("C:\\Users\\ismail\\Desktop\\config.Xls"); // TODO for dev
        //File fileConfig = new File("resources/config.Xls"); // for prod
        try {
            Desktop.getDesktop().open(fileConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("'التطبيق واصل' , تطبيق إضافة التقديرات والملاحظات"
                + "                                 WassilApp v2.2");

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