package com.sebaainf.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class AppOld extends Application {

    public static File selectedFile;
    public static File selectedOldFile;
    public static File selectedPreparedFile;

    private ArrayList<ClasseRoom> listClassRooms = new ArrayList<ClasseRoom>();

    private static File out1 = new File("data01.bin");
    private static File out2 = new File("data02.bin");
    private Scene scene;

    public static void main(String args[]) throws Exception {

        //to create files data01_01.data and data02_02.data for crypted images just one time
        //IsmCommonUtils.encryptDES("resources/ldata01.bin", "resources/data01_01.data", "keya8585"); //just one time size key must be 8
        //IsmCommonUtils.encryptDES("resources/data02.bin", "resources/data02_02.data", "keya8585"); //just one time size key must be 8
        //TODO in dev when we went change pictures
        //IsmCommonUtils.encryptDES("resourcesOriginal/info2.png", "resources/data02.data", "keya8585"); //just one time size key must be 8

        //to restore images from crypted files then creates it, they will be deleted after close mainStage of app

        String pathOne = Thread.currentThread().getContextClassLoader().getResource("data01.data").getPath();
        String pathTwo = Thread.currentThread().getContextClassLoader().getResource("data02.data").getPath();

        BufferedImage bi1 = IsmCommonUtils.decryptDES_picture(pathOne, "keya8585");
        BufferedImage bi2 = IsmCommonUtils.decryptDES_picture(pathTwo, "keya8585");

        ImageIO.write(bi1, "png", out1);
        ImageIO.write(bi2, "png", out2);


        Application.launch(args);
    }

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

                ClasseRoom classeRes = new ClasseRoom();

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

                        //student = new Student(sheet.getRow(r));
                        student = new Student();

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
        //scene2.getStylesheets().add(AppOld.class.getResource("myStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}