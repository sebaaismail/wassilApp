package com.sebaainf.seatsPlan;



import com.sebaainf.main.Student;

import java.util.ArrayList;

/**
 * Created by Ismail on 13/08/2019.
 */
public class ComputerMembers implements Comparable {

    private ArrayList<Student> members = new ArrayList();
    private int idComputer = -1;
    private String group;

    public ArrayList<Student> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Student> members) {
        this.members = members;
    }

    public int getIdComputer() {
        return idComputer;
    }

    public void setIdComputer(int idComputer) {
        this.idComputer = idComputer;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void add(Student st) {
        this.members.add(st);
    }

    public void remove(Student st) {
        this.members.remove(st);
    }

    @Override
    public int compareTo(Object o) {
        return this.getIdComputer() - ((ComputerMembers)o).getIdComputer();
    }
}
