package com.sebaainf.main;

import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.Iterator;

/**
 * Created by Ismail on 19/08/2019.
 */

public class ExcelFileParser {

    private File selectedFile;
    public ExcelFileParser(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public GlobalResult process() {


        //TODO to factorate
        GlobalResult globalRes = new GlobalResult();
        Student student = new Student();

        String fileName = selectedFile.getName();
        System.out.println("absolute path : " + selectedFile.getParentFile());
        //String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');



        fileName = selectedFile.getParentFile() + "\\" + fileName.substring(0, lastDot) + " + التقديرات" + fileName.substring(lastDot);



        File fileResult = new File(fileName);
        File filePrepareGr = new File(fileName); //TODO



        FileInputStream fis = null;


        try {
            fis = new FileInputStream(selectedFile);


            //Get the workbook instance for XLSX file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);




            if (selectedFile.isFile() && selectedFile.exists()) {
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


                System.out.println("i  = " + i);

//                HSSFSheet analyseSheet = workbookAnalyse.createSheet("تحليل النتائج" + i);
//                analyseSheet.setRightToLeft(true);


                HSSFSheet sheet = workbook.getSheetAt(i);

                System.out.println("feuille : " + sheet.getSheetName());

                if(sheet.getRow(5) != null){

                ClasseRoom classeRes = new ClasseRoom();
                classeRes.setNameClasseRoom(sheet.getSheetName());


                FileOutputStream fos = new FileOutputStream(fileResult);


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

                        r++;
                    }

                }

                classeRes.calculateStats();

                globalRes.add(classeRes);


                workbook.write(fos);
                fos.close();


            }// end if row(5)
                //messageLabel.setText("تم إنشاء الملف المعالج بنجاح \""+ fileResult.getName() + "\"");
            }

            globalRes.setExcelResult(fileResult);


            AnalyseResultFileWriter afw = new AnalyseResultFileWriter(globalRes.getListClassesRes());
            globalRes.setAnalyseFile(afw.write());

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
}
