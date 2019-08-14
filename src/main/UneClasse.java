package main;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;

import java.util.ArrayList;

public class UneClasse {

    private String nameClasse;
    private double moyClasse = 0.0d;
    private double maxMoy = 0.0d;
    private double minMoy = 20;
    private int[] analyseTab;
    private int[] analyseExTab;
    private ArrayList<Student> listStudents = new ArrayList<Student>();
    private ArrayList<Student> groupeA = new ArrayList<Student>();
    private ArrayList<Student> groupeB = new ArrayList<Student>();
    protected ArrayList<ComputerMembers> pcMembers = new ArrayList<ComputerMembers>();
    private Student bestStudent, worstStudent;
    private boolean hasNoteTP;


    private int down = -1, up = -1;

    private static double note1b;
    private static double note2b;
    private static double note3b;
    private static double note4b;
    private static double moyb = 0.0d;

    public UneClasse() {
        this.analyseTab = new int[4];
        this.getAnalyseTab()[0] = 0;
        this.getAnalyseTab()[1] = 0;
        this.getAnalyseTab()[2] = 0;
        this.getAnalyseTab()[3] = 0;

        this.analyseExTab = new int[4];
        this.getAnalyseExTab()[0] = 0;
        this.getAnalyseExTab()[1] = 0;
        this.getAnalyseExTab()[2] = 0;
        this.getAnalyseExTab()[3] = 0;
    }


    public double getMoyClasse() {
        return moyClasse;
    }

    public void setMoyClasse(double moyClasse) {
        this.moyClasse = moyClasse;
    }

    public double getMaxMoy() {
        return maxMoy;
    }

    public void setMaxMoy(double maxMoy) {
        this.maxMoy = maxMoy;
    }

    public double getMinMoy() {
        return minMoy;
    }

    public void setMinMoy(double minMoy) {
        this.minMoy = minMoy;
    }

    public int[] getAnalyseTab() {
        return analyseTab;
    }

    public void setAnalyseTab(int[] analyseTab) {
        this.analyseTab = analyseTab;
    }

    public int[] getAnalyseExTab() {
        return analyseExTab;
    }

    public void setAnalyseExTab(int[] analyseExTab) {
        this.analyseExTab = analyseExTab;
    }

    public void process(HSSFSheet sheet, HSSFSheet analyseSheet, HSSFSheet analyseExSheet, int r, int i) {
        double moy, noteEx;

        this.setNameClasse(sheet.getSheetName());
        for (Student st : this.getListStudents()
                ) {
            moy = st.getMoy();
            noteEx = st.getNoteEx();

            if (moy < 7.99) {
                this.analyseTab[0]++;
            } else if (moy >= 8 && moy < 9.99) {
                this.analyseTab[1]++;
            } else if (moy >= 10 && moy < 14.99) {
                this.analyseTab[2]++;
            } else if (moy >= 15 && moy <= 20) {
                this.analyseTab[3]++;
            }

            moyClasse = moyClasse + moy;
            if (maxMoy < moy) {
                maxMoy = moy;
            }
            if (minMoy > moy) {
                minMoy = moy;
            }

            if (noteEx < 7.99) {
                this.analyseExTab[0]++;
            } else if (noteEx >= 8 && noteEx < 9.99) {
                this.analyseExTab[1]++;
            } else if (noteEx >= 10 && noteEx < 14.99) {
                this.analyseExTab[2]++;
            } else if (noteEx >= 15 && noteEx <= 20) {
                this.analyseExTab[3]++;
            }

        }

        writeTab(sheet, analyseSheet, r, i);
        writeTab(sheet, analyseExSheet, r, i);

    }



    public void process_with_compare(HSSFSheet sheetCible, HSSFSheet sheetTri1, HSSFSheet analyseSheet,
                                     HSSFSheet analyseExSheet, int r, int i, ArrayList<Student> listB) {

        double moy, noteEx;

        this.setNameClasse(sheetCible.getSheetName());
        for (Student st : this.getListStudents()
                ) {
            moy = st.getMoy();
            noteEx = st.getNoteEx();

            moyb = listB.get(this.getListStudents().indexOf(st)).getMoy();

            sheetCible.getRow(st.getNumRow()).getCell(8).setCellValue(MyApp.listTak[((int) moy)]); //old .getCell(7)

            st.addIrchadateByCompare(sheetCible.getRow(st.getNumRow()), moyb);


            System.out.println("123456789 : " + MyApp.irchadateByCompare(moyb, moy));
            if (moy - moyb >= 2) {
                this.setUp(this.getUp() + 1);
            } else if (moyb - moy >= 2) {
                this.setDown(this.getDown() + 1);
            }
            //}


        }
    }


    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Student> listStudents) {
        this.listStudents = listStudents;
    }

    public void addStudent(Student student) {
        this.getListStudents().add(student);
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public boolean isHasNoteTP() {
        return hasNoteTP;
    }

    public void setHasNoteTP(boolean hasNoteTP) {
        this.hasNoteTP = hasNoteTP;
    }

    public boolean getHasNoteTP() {

        return hasNoteTP;

    }

    public String getNameClasse() {
        return nameClasse;
    }

    public void setNameClasse(String nameClasse) {
        this.nameClasse = nameClasse;
    }

    public ArrayList<Student> getGroupeA() {
        return groupeA;
    }

    public void setGroupeA(ArrayList<Student> groupeA) {
        this.groupeA = groupeA;
    }

    public ArrayList<Student> getGroupeB() {
        return groupeB;
    }

    public void setGroupeB(ArrayList<Student> groupeB) {
        this.groupeB = groupeB;
    }

    private void writeTab(HSSFSheet sheet, HSSFSheet analyseSheet, int r, int i) {


        HSSFCell cellC, cellD, cellE, cellF, cellH, cellJ, cellK, cellL, cellM;
        HSSFCell cellC2, cellD2, cellE2, cellF2, cellH2, cellJ2, cellK2, cellL2, cellM2;

        System.out.println("moyClass = " + this.getMoyClasse() + "nbre etudiant : " + (r - 8));
        this.setMoyClasse(this.getMoyClasse() / (r - 8));
        //DecimalFormat dec = new DecimalFormat("#.00");
        //moyClasse = Double.valueOf(dec.format(moyClasse));

        HSSFRow row4 = analyseSheet.createRow(4 + (i * 7));
        HSSFRow row5 = analyseSheet.createRow(5 + (i * 7));
        HSSFRow row6 = analyseSheet.createRow(6 + (i * 7));

        int[] tab = this.getAnalyseExTab();
        if (analyseSheet.getSheetName().equals("تحليل النتائج العامة")) {
            tab = this.getAnalyseTab();

            if (this.getDown() != -1) {
                cellL = row4.createCell(11);
                cellL.setCellValue("\u200F" + "عدد التلاميذ المتراجعين");

                cellM = row4.createCell(12);
                cellM.setCellValue("\u200F" + "عدد التلاميذ الذين تحسنت نتائجهم");

                cellL2 = row5.createCell(11);
                cellL2.setCellValue("\u200F" + this.getDown());

                cellM2 = row5.createCell(12);
                cellM2.setCellValue("\u200F" + this.getUp());

            }


        }

        if (sheet.getRow(4) != null) {


            cellC = row4.createCell(2);
            cellC.setCellValue("\u200F" + "00 - 7,99");

            cellD = row4.createCell(3);
            cellD.setCellValue("\u200F" + "08 - 9,99");

            cellE = row4.createCell(4);
            cellE.setCellValue("\u200F" + "10 - 14,99");

            cellF = row4.createCell(5);
            cellF.setCellValue("\u200F" + "15 - 20");

            cellH = row4.createCell(7);
            cellH.setCellValue("\u200F" + "معدل القسم");

            cellJ = row4.createCell(9);
            cellJ.setCellValue("\u200F" + "أعلى معدل");

            cellK = row4.createCell(10);
            cellK.setCellValue("\u200F" + "أدنى معدل");

            cellC2 = row5.createCell(2);
            cellC2.setCellValue("\u200F" + tab[0]);

            cellD2 = row5.createCell(3);
            cellD2.setCellValue("\u200F" + tab[1]);

            cellE2 = row5.createCell(4);
            cellE2.setCellValue("\u200F" + tab[2]);

            cellF2 = row5.createCell(5);
            cellF2.setCellValue("\u200F" + tab[3]);

            cellH2 = row5.createCell(7);
            cellH2.setCellValue("\u200F" + this.getMoyClasse());

            cellJ2 = row5.createCell(9);
            cellJ2.setCellValue("\u200F" + this.getMaxMoy());

            cellK2 = row5.createCell(10);
            cellK2.setCellValue("\u200F" + this.getMinMoy());

            double perc = (double) (this.getAnalyseTab()[2] + this.getAnalyseTab()[3]) * 100
                    / (this.getAnalyseTab()[0] + this.getAnalyseTab()[1]
                    + this.getAnalyseTab()[2] + this.getAnalyseTab()[3]);
            cellH2 = row6.createCell(7);
            //cellH2.setCellValue("\u200F" + perc + " %");


            String str = sheet.getRow(4).getCell(0).getStringCellValue();
            int word1 = str.indexOf("الفوج التربوي :");
            String name = str.substring(word1, str.indexOf("مادة : "));
            System.out.println(name);

            HSSFRow row2 = analyseSheet.createRow(2 + (i * 7));
            HSSFCell cc = row2.createCell(0);
            cc.setCellValue(name);
        }

    }


}
