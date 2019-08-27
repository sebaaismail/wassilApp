package com.sebaainf.ismPoiLib;


import com.sebaainf.main.ClasseRoomSheet;

import com.sebaainf.main.RowParser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;

/**
 * Created by Ismail on 25/08/2019.
 */
public class SheetParser {

    private Sheet sheet;



    public SheetParser(Sheet sheet) {
        this.sheet = sheet;
    }

    public ClasseRoomSheet parse() {

        ClasseRoomSheet classeRoomSheet = new ClasseRoomSheet(sheet);

        Iterator<Row> rowIterator = sheet.rowIterator();
        RowParser rowParser = new RowParser();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(row.getRowNum() >= ConfigSheet.FIRST_ROW_TO_PROCESS){
                classeRoomSheet.addObject(row, rowParser.generate(row));
            }


        }

        if(classeRoomSheet.getObjectsCollection().size() == 0){
            classeRoomSheet = null;
        }

        return classeRoomSheet;

    }

}
