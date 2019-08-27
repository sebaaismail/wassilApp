package com.sebaainf.ismPoiLib;

import com.sebaainf.main.ClasseRoomSheet;
import com.sebaainf.main.MyFileParser;
import com.sebaainf.main.StudentResults;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Ismail on 24/08/2019.
 */
public class MyFileWriter {
    private File fileResult;
    private Workbook workbook;
    public MyFileWriter(MyFileParser parser){
        this.workbook = parser.getWorkbook();
        this.fileResult = parser.getFileResult();
    }

    public File write(Map<IPoiObject, IPoiResults> results, Map<Row, IPoiObject> placemMap){


        try {
            FileOutputStream fos = new FileOutputStream(fileResult); // todo maybe this will be inside sheetParser



        IPoiObject obj;
            String tak, irch;
        for(Row row:placemMap.keySet()){
            obj = placemMap.get(row);
            tak = ((StudentResults)results.get(obj)).getTakdirate();
            irch = ((StudentResults)results.get(obj)).getIrchadate();

            row.getCell(ConfigSheet.RESULT_ONE_COL).setCellValue(tak);
            row.getCell(ConfigSheet.RESULT_TWO_COL).setCellValue(irch);

            System.out.println(tak + " : " + irch);
        }
            placemMap.keySet().iterator().next().getSheet().getWorkbook().write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileResult;

    }

    public File getFileResult() {
        return fileResult;
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
