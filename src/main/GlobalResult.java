package main;

import java.io.File;
import java.util.ArrayList;

public class GlobalResult {

    private File excelResult, analyseFile;
    private ArrayList<ClasseResult> listClassesRes;

    public GlobalResult() {
        listClassesRes = new ArrayList<ClasseResult>();
    }

    public File getExcelResult() {
        return excelResult;
    }

    public void setExcelResult(File excelResult) {
        this.excelResult = excelResult;
    }

    public File getAnalyseFile() {
        return analyseFile;
    }

    public void setAnalyseFile(File analyseFile) {
        this.analyseFile = analyseFile;
    }

    public ArrayList<ClasseResult> getListClassesRes() {
        return listClassesRes;
    }

    public void setListClassesRes(ArrayList<ClasseResult> listClassesRes) {
        this.listClassesRes = listClassesRes;
    }

    public void add(ClasseResult cr) {
        this.getListClassesRes().add(cr);
    }
}
