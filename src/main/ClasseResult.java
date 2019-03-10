package main;

import java.util.ArrayList;

public class ClasseResult {

    private double moyClasse = 0.0d;
    private double maxMoy = 0.0d;
    private double minMoy = 20;
    private int[] analyseTab;
    private int[] analyseExTab;
    private ArrayList<Student> listStudents = new ArrayList<Student>();

    private int down = -1 , up = -1;

    public ClasseResult() {
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

    public void process() {
        double moy, noteEx;
        for (Student st: this.getListStudents()
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
}
