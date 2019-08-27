package com.sebaainf.ismPoiLib;

import com.sebaainf.main.ClasseRoomSheet;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.Map;

/**
 * Created by Ismail on 24/08/2019.
 */
public abstract class FileParser {
    protected File fileIn = null;
    String resultQuote = " + results";

    public FileParser(File fileIn){
        this.fileIn = fileIn;

    }

    abstract public Map<String,IPoiSheet> parse();

    public File prepareFileResult() {
        File fileResult = null;
        String fileName = fileIn.getName();
        /*String parentFile = "";

        if(fileIn.getParentFile() != null){
            parentFile = fileIn.getParent();
        }*/

        int lastDot = fileName.lastIndexOf('.');
        fileName = fileIn.getParent() + "\\" + fileName.substring(0, lastDot) +
                resultQuote + fileName.substring(lastDot);
        fileResult = new File(fileName);
        return fileResult;

    }

}
