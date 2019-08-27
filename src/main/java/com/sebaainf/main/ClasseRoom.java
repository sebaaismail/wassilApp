package com.sebaainf.main;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.sebaainf.seatsPlan.ComputerMembers;

import java.util.ArrayList;

public class ClasseRoom implements Comparable{

    private String nameClasseRoom;

    private ArrayList<Student> listStudents = new ArrayList<Student>();
    private ArrayList<Student> groupeA = new ArrayList<Student>();
    private ArrayList<Student> groupeB = new ArrayList<Student>();
    protected ArrayList<ComputerMembers> pcMembers = new ArrayList<ComputerMembers>();

    private boolean hasNoteTP;

    private AnalyseResult analyseResult;

    private ArrayList<ComputerMembers> listCMA;
    private ArrayList<ComputerMembers> listCMB;


    private int down = -1, up = -1;

    private static double note1b;
    private static double note2b;
    private static double note3b;
    private static double note4b;
    private static double moyb = 0.0d;


    public ClasseRoom() {


    }


/*

    public void process(HSSFSheet sheet, HSSFSheet analyseSheet, HSSFSheet analyseExSheet, int r, int i) {
        double moy, noteEx;

        this.setNameClasseRoom(sheet.getSheetName());
        for (Student st : this.getListStudents()
                ) {
            moy = st.getMoy();
            noteEx = st.getNoteEx();

            if (moy <= WORST_MOYS_MAX) {
                this.analyseClassRoomMoys[WORST_MOYS]++;
            } else if (moy > WORST_MOYS_MAX && moy <= BAD_MOYS_MAX) {
                this.analyseClassRoomMoys[BAD_MOYS]++;
            } else if (moy > BAD_MOYS_MAX && moy <= ACCEPTABLE_MOYS_MAX) {
                this.analyseClassRoomMoys[ACCEPTABLE_MOYS]++;
            } else if (moy > ACCEPTABLE_MOYS_MAX && moy <= PERFECT_MOYS) {
                this.analyseClassRoomMoys[GOOD_MOYS]++;
            }

            moyClasseRoom = moyClasseRoom + moy;
            if (maxMoy < moy) {
                maxMoy = moy;
            }
            if (minMoy > moy) {
                minMoy = moy;
            }

            if (noteEx < 7.99) {
                this.analyseClassRoomExam[0]++;
            } else if (noteEx >= 8 && noteEx < 9.99) {
                this.analyseClassRoomExam[1]++;
            } else if (noteEx >= 10 && noteEx < 14.99) {
                this.analyseClassRoomExam[2]++;
            } else if (noteEx >= 15 && noteEx <= 20) {
                this.analyseClassRoomExam[3]++;
            }

        }

        writeTab(sheet, analyseSheet, r, i);
        writeTab(sheet, analyseExSheet, r, i);

    }

*/


    public void process_with_compare(HSSFSheet sheetCible, HSSFSheet sheetTri1, HSSFSheet analyseSheet,
                                     HSSFSheet analyseExSheet, int r, int i, ArrayList<Student> listB) {

        double moy, noteEx;

        this.setNameClasseRoom(sheetCible.getSheetName());
        for (Student st : this.getListStudents()
                ) {
            //moy = st.getMoy();
            //noteEx = st.getNoteEx();

            //moyb = listB.get(this.getListStudents().indexOf(st)).getMoy();

            //sheetCible.getRow(st.getNumRow()).getCell(8).setCellValue(AppOld.listTak[((int) moy)]); //old .getCell(7)

            //st.addIrchadateByCompare(sheetCible.getRow(st.getNumRow()), moyb);


           // System.out.println("123456789 : " + AppOld.irchadateByCompare(moyb, moy));
            /*
            if (moy - moyb >= 2) {
                this.setUp(this.getUp() + 1);
            } else if (moyb - moy >= 2) {
                this.setDown(this.getDown() + 1);
            }
            */
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

    public String getNameClasseRoom() {
        return nameClasseRoom;
    }

    public void setNameClasseRoom(String nameClasseRoom) {
        this.nameClasseRoom = nameClasseRoom;
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

    public ArrayList<ComputerMembers> getListCMA() {
        return listCMA;
    }

    public void setListCMA(ArrayList<ComputerMembers> listCMA) {
        this.listCMA = listCMA;
    }

    public ArrayList<ComputerMembers> getListCMB() {
        return listCMB;
    }

    public void setListCMB(ArrayList<ComputerMembers> listCMB) {
        this.listCMB = listCMB;
    }

    public AnalyseResult getAnalyseResult() {
        return analyseResult;
    }

    public void setAnalyseResult(AnalyseResult analyseResult) {
        this.analyseResult = analyseResult;
    }


    public void setMaps(ArrayList<ComputerMembers> listCMA, ArrayList<ComputerMembers> listCMB) {
        this.listCMA = listCMA;
        this.listCMB = listCMB;
    }

    @Override
    public int compareTo(Object obj) {
        int i = 1;
        if (this.getAnalyseResult().getMoyClasseRoom() <((ClasseRoom)obj).getAnalyseResult().getMoyClasseRoom()){
            i = -1;
        }
        return i;
    }

    public void calculateStats() {
        this.analyseResult = new AnalyseResult(this);
        analyseResult.calculate();
    }
}
