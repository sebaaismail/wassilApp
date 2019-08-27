package com.sebaainf.main;

import com.sebaainf.ismPoiLib.IPoiObject;
import com.sebaainf.ismPoiLib.IPoiParams;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.Date;

public class Student implements IPoiObject {
    private String id;
    private String nom;
    private String prenom;

    private Date dateNaiss;

    private IPoiParams notes;
    private int numRow;

    private boolean isMale = false;

    public Student() {

    }


    public Student(HSSFRow row, boolean forGrouping) {
        if (forGrouping) {
            this.loadForGrouping(row);
        }
    }


    private void loadForGrouping(HSSFRow row) {

        // this.setFullName(row.getCell(6).getStringCellValue());

        if (row.getCell(7).getStringCellValue().equals("H")) {
            this.isMale = true;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getFullName() {
        return this.nom + " " + this.prenom;
    }


    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public IPoiParams getNotes() {
        return notes;
    }

    public void setNotes(IPoiParams notes) {
        this.notes = notes;
    }
}
