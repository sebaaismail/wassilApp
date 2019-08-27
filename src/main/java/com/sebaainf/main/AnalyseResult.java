package com.sebaainf.main;

/**
 * Created by Ismail on 18/08/2019.
 */
public class AnalyseResult {

    private ClasseRoom classeRoom;

    private int[] statsMoys = {0, 0, 0, 0};
    private int[] statsExam = {0, 0, 0, 0};

    final static int WORST_MOYS = 0;
    final static int BAD_MOYS = 1;
    final static int ACCEPTABLE_MOYS = 2;
    final static int GOOD_MOYS = 3;

    final static double WORST_MOYS_MAX = 7.99;
    final static double BAD_MOYS_MAX = 9.99;
    final static double ACCEPTABLE_MOYS_MAX = 14.99;
    final static double PERFECT_MOYS = 20;

    private double moyClasseRoom = 0.0d;
    private double moyExamClasseRoom = 0.0d;

    private double maxMoy;
    private double minMoy;

    private Student bestStudent, worstStudent;

    public AnalyseResult(ClasseRoom classeRoom){

        this.classeRoom = classeRoom;


    }




    public void calculate() {

        double moyStudent, noteExStudent, sumMoys = 0.0, sumMoysExams =  0.0;

        maxMoy = 0.0;
        minMoy = 20;
        for (Student st : this.classeRoom.getListStudents()) {
//todo comments for refactoruing
            //moyStudent = st.getMoy();
            moyStudent = 0;noteExStudent = 0;// todo to delete
            //noteExStudent = st.getNoteEx();
            sumMoys = sumMoys + moyStudent;
            sumMoysExams = sumMoysExams + noteExStudent;

            if (moyStudent <= WORST_MOYS_MAX) {
                this.statsMoys[WORST_MOYS]++;
            } else if (moyStudent > WORST_MOYS_MAX && moyStudent <= BAD_MOYS_MAX) {
                this.statsMoys[BAD_MOYS]++;
            } else if (moyStudent > BAD_MOYS_MAX && moyStudent <= ACCEPTABLE_MOYS_MAX) {
                this.statsMoys[ACCEPTABLE_MOYS]++;
            } else if (moyStudent > ACCEPTABLE_MOYS_MAX && moyStudent <= PERFECT_MOYS) {
                this.statsMoys[GOOD_MOYS]++;
            }



            if (maxMoy < moyStudent) {
                maxMoy = moyStudent;
                bestStudent = st;
            }
            if (minMoy > moyStudent) {
                minMoy = moyStudent;
                worstStudent = st;
            }

            if (noteExStudent <= WORST_MOYS_MAX) {
                this.statsExam[WORST_MOYS]++;
            } else if (noteExStudent > WORST_MOYS_MAX && noteExStudent <= BAD_MOYS_MAX) {
                this.statsExam[BAD_MOYS]++;
            } else if (noteExStudent > BAD_MOYS_MAX && noteExStudent < ACCEPTABLE_MOYS_MAX) {
                this.statsExam[ACCEPTABLE_MOYS]++;
            } else if (noteExStudent > ACCEPTABLE_MOYS_MAX && noteExStudent <= PERFECT_MOYS) {
                this.statsExam[GOOD_MOYS]++;
            }

        }
        moyClasseRoom = sumMoys / this.classeRoom.getListStudents().size();
        moyExamClasseRoom = sumMoysExams / this.classeRoom.getListStudents().size();

        //this.classeRoom.setAnalyseResult(this);

    }

    public ClasseRoom getClasseRoom() {
        return classeRoom;
    }

    public void setClasseRoom(ClasseRoom classeRoom) {
        this.classeRoom = classeRoom;
    }

    public int[] getStatsMoys() {
        return statsMoys;
    }

    public void setStatsMoys(int[] statsMoys) {
        this.statsMoys = statsMoys;
    }

    public int[] getStatsExam() {
        return statsExam;
    }

    public void setStatsExam(int[] statsExam) {
        this.statsExam = statsExam;
    }

    public double getMoyClasseRoom() {
        return moyClasseRoom;
    }

    public void setMoyClasseRoom(double moyClasseRoom) {
        this.moyClasseRoom = moyClasseRoom;
    }

    public double getMoyExamClasseRoom() {
        return moyExamClasseRoom;
    }

    public void setMoyExamClasseRoom(double moyExamClasseRoom) {
        this.moyExamClasseRoom = moyExamClasseRoom;
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

    public Student getBestStudent() {
        return bestStudent;
    }

    public void setBestStudent(Student bestStudent) {
        this.bestStudent = bestStudent;
    }

    public Student getWorstStudent() {
        return worstStudent;
    }

    public void setWorstStudent(Student worstStudent) {
        this.worstStudent = worstStudent;
    }
}
