package main;

import java.io.File;
import java.util.ArrayList;

public class GlobalResult {

    private File excelResult, analyseFile, prepareGroupsFile;
    private ArrayList<UneClasse> listClassesRes;

    public GlobalResult() {
        listClassesRes = new ArrayList<UneClasse>();
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

    public ArrayList<UneClasse> getListClassesRes() {
        return listClassesRes;
    }

    public void setListClassesRes(ArrayList<UneClasse> listClassesRes) {
        this.listClassesRes = listClassesRes;
    }

    public void add(UneClasse cr) {
        this.getListClassesRes().add(cr);
    }

    public File getPrepareGroupsFile() {
        return prepareGroupsFile;
    }

    public void setPrepareGroupsFile(File prepareGroupsFile) {
        this.prepareGroupsFile = prepareGroupsFile;
    }
}
