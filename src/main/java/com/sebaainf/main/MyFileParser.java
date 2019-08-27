package com.sebaainf.main;

import com.sebaainf.ismPoiLib.FileParser;
import com.sebaainf.ismPoiLib.IPoiSheet;
import com.sebaainf.ismPoiLib.SheetParser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ismail on 26/08/2019.
 */
public class MyFileParser extends FileParser {

    private Map<String,ClasseRoomSheet> classeRoomsCollection = new HashMap<String,ClasseRoomSheet>();
    private Workbook workbook;
    private File fileResult;

    public MyFileParser(File fileIn) {
        super(fileIn);
    }

    @Override
    public Map<String,IPoiSheet>  parse() {
        //*
        this.fileResult = prepareFileResult();
        ClasseRoomSheet cl = null;
        Map<String,IPoiSheet> sheetsCollection = new HashMap<String,IPoiSheet>();

        HSSFWorkbook workbook = null;
        SheetParser sheetParser = null;
        //*/
        try {

            FileInputStream fis = new FileInputStream(fileIn);
            workbook = new HSSFWorkbook(fis);
            this.workbook = workbook;
            HSSFSheet sheet;


            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheet = workbook.getSheetAt(i);
                sheetParser = new SheetParser(sheet);
                cl = sheetParser.parse();

                if(cl != null) {
                    this.add(cl);
                    sheetsCollection.put(cl.getSheet().getSheetName(), cl);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheetsCollection;
    }

    public void add(ClasseRoomSheet sheet){
        this.classeRoomsCollection.put(sheet.getSheet().getSheetName(), sheet);
    }

    public Map<String, ClasseRoomSheet> getClasseRoomsCollection() {
        return classeRoomsCollection;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public File getFileResult() {
        return fileResult;
    }
}
