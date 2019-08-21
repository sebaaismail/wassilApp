package com.sebaainf.main;

import java.io.File;
import java.util.ArrayList;

public class GlobalResult {

    private File excelResult, analyseFile, prepareGroupsFile;
    private ArrayList<ClasseRoom> listClassesRes;

    public GlobalResult() {
        listClassesRes = new ArrayList<ClasseRoom>();
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

    public ArrayList<ClasseRoom> getListClassesRes() {
        return listClassesRes;
    }

    public void setListClassesRes(ArrayList<ClasseRoom> listClassesRes) {
        this.listClassesRes = listClassesRes;
    }

    public void add(ClasseRoom cr) {
        this.getListClassesRes().add(cr);
    }

    public File getPrepareGroupsFile() {
        return prepareGroupsFile;
    }

    public void setPrepareGroupsFile(File prepareGroupsFile) {
        this.prepareGroupsFile = prepareGroupsFile;
    }
}
