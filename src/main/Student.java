package main;

import java.util.Date;

public class Student {
    private String id;
    private String nom;
    private String prenom;
    private String fullName;
    private Date dateNaiss;
    private double noteCont;
    private double noteTp;
    private double noteDev;
    private double noteEx;
    private double moy;
    private String takdir;
    private String irched;

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

    public double getNoteCont() {
        return noteCont;
    }

    public void setNoteCont(double noteCont) {
        this.noteCont = noteCont;
    }

    public double getNoteTp() {
        return noteTp;
    }

    public void setNoteTp(double noteTp) {
        this.noteTp = noteTp;
    }

    public double getNoteDev() {
        return noteDev;
    }

    public void setNoteDev(double noteDev) {
        this.noteDev = noteDev;
    }

    public double getNoteEx() {
        return noteEx;
    }

    public void setNoteEx(double noteEx) {
        this.noteEx = noteEx;
    }

    public double getMoy() {
        return moy;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }

    public String getTakdir() {
        return takdir;
    }

    public void setTakdir(String takdir) {
        this.takdir = takdir;
    }

    public String getIrched() {
        return irched;
    }

    public void setIrched(String irched) {
        this.irched = irched;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
