package main;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellType;



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
    private int numRow;
    private boolean isMale = false;


    public Student() {

    }

    public Student(HSSFRow row) {
        this.loadBase(row);
    }


    public Student(HSSFRow row, boolean forGrouping) {
        if(forGrouping){
            this.loadForGrouping(row);
        }
    }

    //constructor that map notes from row in Excel file to student object
    public Student(HSSFRow row, boolean hasNoteTP, boolean noCompare){

        //chaining instructor : call the first constructor
        this(row);


        int takdirateColumn = 8;

        //TODO i have to calculate takdrate and irchadate here

        noteCont = row.getCell(4).getNumericCellValue(); //old .getCell(3)


        //if file has not TP column
        if (!hasNoteTP) {
            takdirateColumn = 7;
            //if (!sheet.getRow(7).getCell(9).getStringCellValue().equals("«·«—‘«œ« ")) {

            noteDev = row.getCell(5).getNumericCellValue(); //old .getCell(5)
            noteEx = row.getCell(6).getNumericCellValue(); //old .getCell(6)
            moy = (noteCont + noteDev + (noteEx * 2)) / 4;


        } else {


            noteDev = row.getCell(6).getNumericCellValue(); //old .getCell(5)
            noteEx = row.getCell(7).getNumericCellValue(); //old .getCell(6)


            if (row.getCell(5).getCellTypeEnum() == CellType.NUMERIC) { //old .getCell(4)
                noteTp = row.getCell(5).getNumericCellValue(); //old .getCell(4)
                this.setNoteTp(noteTp);
                moy = (noteCont + noteTp + noteDev + (noteEx * 2)) / 5;
            } else {
                moy = (noteCont + noteDev + (noteEx * 2)) / 4;
            }
        }

        this.setId(id);
        this.setFullName(fullName);
        this.setNoteCont(noteCont);
        this.setNoteDev(noteDev);
        this.setNoteEx(noteEx);
        this.setMoy(moy);
        System.out.println(fullName + " , moy = " + moy);

        // Now adding takdirate and irchadate for the student

        row.getCell(takdirateColumn).setCellValue(MyApp.listTak[(int) moy]); //old .getCell(7)

        if (!noCompare) {
            row.getCell(takdirateColumn + 1).setCellValue(MyApp.listIrch[(int) moy]); //old .getCell(8)

        }


    }


    private void loadBase(HSSFRow row) {

        HSSFCell cell = row.getCell(4); //old .getCell(3)


        this.id = row.getCell(0).getStringCellValue();
        this.fullName = row.getCell(1).getStringCellValue() + " " +
                row.getCell(2).getStringCellValue();
    }

    private void loadForGrouping(HSSFRow row) {

        this.fullName = row.getCell(6).getStringCellValue();

        if(row.getCell(7).getStringCellValue().equals("H")) {
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

    public void addIrchadateByCompare(HSSFRow row, double moyb) {
        row.getCell(9).setCellValue(MyApp.irchadateByCompare(moyb, moy)); //old .getCell(8)
    }
}
