package com.sebaainf.main;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ismail on 18/08/2019.
 */
public class AnalyseResultFileWriter {

    private HSSFSheet analyseMoysSheet, analyseExamsSheet;

    // the position of worstMoysTitle in the sheet
    // utilise comme repere pour positionner les autres cellules
    private int worstMoysTitleRowNum = 4;
    private int worstMoysTitleCellNum = 2;

    private int moyClassRoomTitleCellNum = -1;
    private int moyClassRoomTitleFirstRowNum = -1;
    private int maxMoyTitleCellNum = 9;

    private ArrayList<ClasseRoom> listClassRooms;

    public AnalyseResultFileWriter(ArrayList<ClasseRoom> listClassRooms){
        this.listClassRooms = listClassRooms;
    }

    public File write() {

        File fileAnalyse = new File(MyApp.selectedFile.getParentFile() + "\\" + "تحليل النتائج.xls");


        FileOutputStream fosAnalyse = null;


        try {

            fosAnalyse = new FileOutputStream(fileAnalyse);




        HSSFWorkbook workbookAnalyse = new HSSFWorkbook();


        HSSFCellStyle style = workbookAnalyse.createCellStyle();
        style.setDataFormat(workbookAnalyse.createDataFormat().getFormat("0.00"));

        analyseMoysSheet = workbookAnalyse.createSheet("تحليل النتائج العامة");
        analyseMoysSheet.setRightToLeft(true);

        analyseExamsSheet = workbookAnalyse.createSheet("تحليل نتائج الإختبار");
        analyseExamsSheet.setRightToLeft(true);

        HSSFCell cellC, cellD, cellE, cellF, cellH, cellJ, cellK, cellL, cellM;
        HSSFCell cellC2, cellD2, cellE2, cellF2, cellH2, cellJ2, cellK2, cellL2, cellM2;


        AnalyseResult ar;
        int index = 0;

        for(ClasseRoom cr : listClassRooms){

            ar = cr.getAnalyseResult();
            index = listClassRooms.indexOf(cr);
            fill(analyseMoysSheet, ar.getStatsExam(), ar.getMoyClasseRoom(), ar.getMaxMoy(), ar.getMinMoy(), index, false);
            fill(analyseExamsSheet, ar.getStatsExam(), ar.getMoyClasseRoom(), ar.getMaxMoy(), ar.getMinMoy(), index, true);
        }


        workbookAnalyse.write(fosAnalyse); //for each classRoom
            fosAnalyse.close();

            return fileAnalyse;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    } //  rend write (int , int)

    public File write(int worstMoysTitleRowNum, int worstMoysTitleCellNum) {
        this.worstMoysTitleRowNum = worstMoysTitleRowNum;
        this.worstMoysTitleCellNum = worstMoysTitleCellNum;
        this.moyClassRoomTitleFirstRowNum = worstMoysTitleRowNum + 0; // here you can change row for all

               return write();



} // end write function

    private void fill(HSSFSheet sheet, int[] statsNotes, double moyClasseRoom, double maxMoy, double minMoy,
                      int indexClasseRoom, boolean isStatsExamSheet) {

        String word = "";

        if(isStatsExamSheet){
            word = "نقطة";
        } else {
            word = "معدل";
        }

        if (moyClassRoomTitleCellNum < 0) {
            moyClassRoomTitleCellNum = worstMoysTitleCellNum + 5;
        }

        maxMoyTitleCellNum = moyClassRoomTitleCellNum + 2;

        if (moyClassRoomTitleFirstRowNum < 0) {
            moyClassRoomTitleFirstRowNum = worstMoysTitleRowNum;
        }


        HSSFRow rowNameClasseRoom = sheet.createRow(worstMoysTitleRowNum - 2 + (indexClasseRoom * 7));
        HSSFRow rowHeadings = sheet.createRow(worstMoysTitleRowNum + (indexClasseRoom * 7));
        HSSFRow rowNumbers = sheet.createRow(worstMoysTitleRowNum + 1 + (indexClasseRoom * 7));

        HSSFRow rowMoyClassHeads = sheet.getRow(moyClassRoomTitleFirstRowNum + (indexClasseRoom * 7));
        HSSFRow rowMoyClassNumbs = sheet.getRow(moyClassRoomTitleFirstRowNum + 1 + (indexClasseRoom * 7));

        if(rowMoyClassHeads == null) {
            rowMoyClassHeads = sheet.createRow(moyClassRoomTitleFirstRowNum + (indexClasseRoom * 7));
        }

        if(rowMoyClassHeads == null) {
            rowMoyClassNumbs = sheet.createRow(moyClassRoomTitleFirstRowNum + 1 + (indexClasseRoom * 7));
        }



        rowNameClasseRoom.createCell(0).setCellValue("الفوج التربوي : "
                + listClassRooms.get(indexClasseRoom).getNameClasseRoom());

        rowHeadings.createCell(worstMoysTitleCellNum).setCellValue("00 - " + AnalyseResult.WORST_MOYS_MAX);

        rowHeadings.createCell(worstMoysTitleCellNum+1).setCellValue(AnalyseResult.WORST_MOYS_MAX + 0.01 +
                " - " + AnalyseResult.BAD_MOYS_MAX);

        rowHeadings.createCell(worstMoysTitleCellNum+2).setCellValue( AnalyseResult.BAD_MOYS_MAX + 0.01 +
                " - " + AnalyseResult.ACCEPTABLE_MOYS_MAX);

        rowHeadings.createCell(worstMoysTitleCellNum+3).setCellValue(AnalyseResult.ACCEPTABLE_MOYS_MAX + 0.01 + " - 20");


        rowNumbers.createCell(worstMoysTitleCellNum).setCellValue(statsNotes[AnalyseResult.WORST_MOYS]);
        rowNumbers.createCell(worstMoysTitleCellNum+1).setCellValue(statsNotes[AnalyseResult.BAD_MOYS]);
        rowNumbers.createCell(worstMoysTitleCellNum+2).setCellValue(statsNotes[AnalyseResult.ACCEPTABLE_MOYS]);
        rowNumbers.createCell(worstMoysTitleCellNum+3).setCellValue(statsNotes[AnalyseResult.GOOD_MOYS]);

        rowMoyClassHeads.createCell(moyClassRoomTitleCellNum).setCellValue("\u200Fمعدل القسم");
        rowMoyClassHeads.createCell(maxMoyTitleCellNum).setCellValue("أعلى " + word);
        rowMoyClassHeads.createCell(maxMoyTitleCellNum + 1).setCellValue("أدنى " + word);

        rowMoyClassNumbs.createCell(moyClassRoomTitleCellNum).setCellValue(moyClasseRoom);
        rowMoyClassNumbs.createCell(maxMoyTitleCellNum).setCellValue(maxMoy);
        rowMoyClassNumbs.createCell(maxMoyTitleCellNum+1).setCellValue(minMoy);


    }
}
