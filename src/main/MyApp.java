package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;

public class MyApp extends Application {

    public static File selectedFile;
    public static File selectedOldFile;

    public static HSSFSheet analyseSheet, analyseExSheet;

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

    private static String id;
    private static String fullName;

    //private static double moy, moyClasse, maxMoy, minMoy = 0.0d;
    private static double moy;
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

                ClasseResult classeRes = new ClasseResult();
                classeRes.setDown(0);
                classeRes.setUp(0);


                HSSFSheet sheetCible = workbookCible.getSheetAt(i);
                HSSFSheet sheetTri1 = workbookTri1.getSheetAt(i);
                System.out.println("hh =" + sheetCible.getSheetName());

                /* //TODO
                if (sheetCible.getRow(7).getCell(0).getStringCellValue().isEmpty()
                        || sheetCible.getSheetName().equals("الواجهة")) {
                    break;
                }

                //*/

                FileOutputStream fos = new FileOutputStream(fileResult);
                FileOutputStream fosAnalyse = new FileOutputStream(fileAnalyse);

                int r = 8; // old: r = 8;

                //iterate students
                Iterator<Row> rowIterator = sheetCible.rowIterator();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                //while (!sheetCible.getRow(r).getCell(1).getStringCellValue().isEmpty()) {
                    if (row.getRowNum() >= 8) {

                        student = new Student();
                        System.out.println(" row num " + r);
                        System.out.println(" sheetCible.getRow(r).getCell(1).getStringCellValue() " + sheetCible.getRow(r).getCell(1).getStringCellValue());
                        HSSFCell cellCible = sheetCible.getRow(r).getCell(4); //old: = sheetCible.getRow(r).getCell(3);

                        id = sheetCible.getRow(r).getCell(0).getStringCellValue();
                        fullName = sheetCible.getRow(r).getCell(1).getStringCellValue() + " " +
                                sheetCible.getRow(r).getCell(2).getStringCellValue();
                        note1 = sheetCible.getRow(r).getCell(4).getNumericCellValue();//old = sheetCible.getRow(r).getCell(3)

                        note3 = sheetCible.getRow(r).getCell(6).getNumericCellValue(); // old .getCell(5)
                        note4 = sheetCible.getRow(r).getCell(7).getNumericCellValue(); // old .getCell(6)

                        HSSFCell cellb = sheetTri1.getRow(r).getCell(4); //old .getCell(3)

                        note1b = sheetTri1.getRow(r).getCell(4).getNumericCellValue(); //old .getCell(3)

                        note3b = sheetTri1.getRow(r).getCell(6).getNumericCellValue(); //old .getCell(6)
                        note4b = sheetTri1.getRow(r).getCell(7).getNumericCellValue(); //old .getCell(6)

                        //if (sheetCible.getRow(r).getCell(4).getNumericCellValue() != 0) {//TODO
                        if (sheetCible.getRow(r).getCell(5).getCellTypeEnum() != CellType.NUMERIC) {// old .getCell(4)
                            //if (sheetCible.getRow(r).getCell(4) == null || sheetCible.getRow(r).getCell(4).getCellTypeEnum() == CellType.BLANK) {
                            moy = (note1 + note3 + (note4 * 2)) / 4;
                            moyb = (note1b + note3b + (note4b * 2)) / 4;
                            student.setNoteTp(note2);

                        } else {
                            note2 = sheetCible.getRow(r).getCell(5).getNumericCellValue(); //old .getCell(4)
                            moy = (note1 + note2 + note3 + (note4 * 2)) / 5;
                            note2b = sheetTri1.getRow(r).getCell(5).getNumericCellValue(); //old .getCell(4)
                            moyb = (note1b + note2b + note3b + (note4b * 2)) / 5;
                        }

                        student.setId(id);
                        student.setFullName(fullName);
                        student.setNoteCont(note1);
                        student.setNoteDev(note3);
                        student.setNoteEx(note4);
                        student.setMoy(moy);

                        classeRes.addStudent(student);

                        sheetCible.getRow(r).getCell(8).setCellValue(listTak[((int) moy)]); //old .getCell(7)
                        sheetCible.getRow(r).getCell(9).setCellValue(irchadateByCompare(moyb, moy)); //old .getCell(8)

                        if (moy-moyb >= 2) {
                            classeRes.setUp(classeRes.getUp()+1);
                        } else if (moyb - moy >= 2) {
                            classeRes.setDown(classeRes.getDown()+1);
                        }
                        //}

                        globalRes.add(classeRes);
                        r++;

                    }

                }

                classeRes.process();
                writeTab(sheetCible,analyseSheet, classeRes, r, i);
                writeTab(sheetCible,analyseExSheet, classeRes, r, i);

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
    public static GlobalResult process(File file) {

        GlobalResult globalRes = new GlobalResult();
        Student student = new Student();

        String fileName = file.getName();
        System.out.println("absolute path : " + file.getParentFile());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        fileName = file.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + التقديرات" + fileName.substring(lastDot);

        File fileResult = new File(fileName);

        File fileAnalyse = new File(file.getParentFile() + "\\" + "تحليل النتائج.xls");


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

                ClasseResult classeRes = new ClasseResult();

                System.out.println("i  = " + i);

//                HSSFSheet analyseSheet = workbookAnalyse.createSheet("تحليل النتائج" + i);
//                analyseSheet.setRightToLeft(true);


                HSSFSheet sheet = workbook.getSheetAt(i);




                FileOutputStream fos = new FileOutputStream(fileResult);
                FileOutputStream fosAnalyse = new FileOutputStream(fileAnalyse);
                //FileOutputStream fos = new FileOutputStream(file);
                int r = 8; // r = 8

                //Cell ccc = sheet.getRow(34).getCell(1);

                //System.out.println(sheet.getRow(34));
                //System.out.println(sheet.getRow(34).getCell(1).getStringCellValue());
                Iterator<Row> rowIterator = sheet.rowIterator();

                //iterate students
                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();
                    if (row.getRowNum() >= 8) {
                        //while (!sheet.getRow(r).getCell(1).getStringCellValue().isEmpty()) {  //old version of files
                        nbstudents++;
                        student = new Student();

                        System.out.println(" row num " + r);
                        HSSFCell cell = sheet.getRow(r).getCell(4); //old .getCell(3)

                        id = sheet.getRow(r).getCell(0).getStringCellValue();
                        fullName = sheet.getRow(r).getCell(1).getStringCellValue() + " " +
                                    sheet.getRow(r).getCell(2).getStringCellValue();
                        note1 = sheet.getRow(r).getCell(4).getNumericCellValue(); //old .getCell(3)

                        note3 = sheet.getRow(r).getCell(6).getNumericCellValue(); //old .getCell(5)
                        note4 = sheet.getRow(r).getCell(7).getNumericCellValue(); //old .getCell(6)

                        if (sheet.getRow(r).getCell(5).getCellTypeEnum() == CellType.NUMERIC) { //old .getCell(4)
                            note2 = sheet.getRow(r).getCell(5).getNumericCellValue(); //old .getCell(4)
                            student.setNoteTp(note2);
                            moy = (note1 + note2 + note3 + (note4 * 2)) / 5;
                        } else {
                            moy = (note1 + note3 + (note4 * 2)) / 4;
                        }

                        student.setId(id);
                        student.setFullName(fullName);
                        student.setNoteCont(note1);
                        student.setNoteDev(note3);
                        student.setNoteEx(note4);
                        student.setMoy(moy);

                        classeRes.addStudent(student);

                        //TODO important

                        System.out.println(fullName + " , moy = " + moy);
                        //if (nbstudents < 96) {
                        sheet.getRow(r).getCell(8).setCellValue(listTak[(int) moy]); //old .getCell(7)
                        sheet.getRow(r).getCell(9).setCellValue(listIrch[(int) moy]); //old .getCell(8)
                        //}
                        System.out.println("number students = " + nbstudents);
                        globalRes.add(classeRes);
                        r++;
                    }

                }

                classeRes.process();
                writeTab(sheet,analyseSheet, classeRes, r, i);
                writeTab(sheet,analyseExSheet, classeRes, r, i);

                workbook.write(fos);
                fos.close();

                workbookAnalyse.write(fosAnalyse);
                fosAnalyse.close();

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

    private static void writeTab(HSSFSheet sheet,HSSFSheet analyseSheet, ClasseResult classeRes, int r, int i) {

        HSSFCell cellC, cellD, cellE, cellF, cellH, cellJ, cellK, cellL, cellM;
        HSSFCell cellC2, cellD2, cellE2, cellF2, cellH2, cellJ2, cellK2, cellL2, cellM2;

        System.out.println("moyClass = " + classeRes.getMoyClasse() + "nbre etudiant : " +  (r-8));
        classeRes.setMoyClasse(classeRes.getMoyClasse()/(r-8));
        //DecimalFormat dec = new DecimalFormat("#.00");
        //moyClasse = Double.valueOf(dec.format(moyClasse));

        HSSFRow row4 = analyseSheet.createRow(4 + (i*7));
        HSSFRow row5 = analyseSheet.createRow(5 + (i*7));
        HSSFRow row6 = analyseSheet.createRow(6 + (i*7));

        int[] tab = classeRes.getAnalyseExTab();
        if (analyseSheet.getSheetName().equals("تحليل النتائج العامة")) {
            tab = classeRes.getAnalyseTab();

            if (classeRes.getDown() != -1) {
                cellL = row4.createCell(11);
                cellL.setCellValue("\u200F" + "عدد التلاميذ المتراجعين");

                cellM = row4.createCell(12);
                cellM.setCellValue("\u200F" + "عدد التلاميذ الذين تحسنت نتائجهم");

                cellL2 = row5.createCell(11);
                cellL2.setCellValue("\u200F" + classeRes.getDown());

                cellM2 = row5.createCell(12);
                cellM2.setCellValue("\u200F" + classeRes.getUp());

            }


        }

        if (sheet.getRow(4) != null) {



            cellC = row4.createCell(2);
            cellC.setCellValue("\u200F" + "00 - 7,99");

            cellD = row4.createCell(3);
            cellD.setCellValue("\u200F" + "08 - 9,99");

            cellE = row4.createCell(4);
            cellE.setCellValue("\u200F" + "10 - 14,99");

            cellF = row4.createCell(5);
            cellF.setCellValue("\u200F" + "15 - 20");

            cellH = row4.createCell(7);
            cellH.setCellValue("\u200F" + "معدل القسم");

            cellJ = row4.createCell(9);
            cellJ.setCellValue("\u200F" + "أعلى معدل");

            cellK = row4.createCell(10);
            cellK.setCellValue("\u200F" + "أدنى معدل");

            cellC2 = row5.createCell(2);
            cellC2.setCellValue("\u200F" + tab[0]);

            cellD2 = row5.createCell(3);
            cellD2.setCellValue("\u200F" + tab[1]);

            cellE2 = row5.createCell(4);
            cellE2.setCellValue("\u200F" + tab[2]);

            cellF2 = row5.createCell(5);
            cellF2.setCellValue("\u200F" + tab[3]);

            cellH2 = row5.createCell(7);
            cellH2.setCellValue("\u200F" + classeRes.getMoyClasse());

            cellJ2 = row5.createCell(9);
            cellJ2.setCellValue("\u200F" + classeRes.getMaxMoy());

            cellK2 = row5.createCell(10);
            cellK2.setCellValue("\u200F" + classeRes.getMinMoy());

            double perc = (double)(classeRes.getAnalyseTab()[2] + classeRes.getAnalyseTab()[3])*100
                    / (classeRes.getAnalyseTab()[0] + classeRes.getAnalyseTab()[1]
            + classeRes.getAnalyseTab()[2] + classeRes.getAnalyseTab()[3]);
            cellH2 = row6.createCell(7);
            cellH2.setCellValue("\u200F" + perc + " %");



            String str = sheet.getRow(4).getCell(0).getStringCellValue();
            int word1 = str.indexOf("الفوج التربوي :");
            String name = str.substring(word1, str.indexOf("مادة : "));
            System.out.println(name);

            HSSFRow row2 = analyseSheet.createRow(2 + (i * 7));
            HSSFCell cc = row2.createCell(0);
            cc.setCellValue(name);
        }

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