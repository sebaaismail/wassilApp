package com.sebaainf.main;

/**
 * Created by Ismail on 24/08/2019.
 */
public class WithTpNotesTrim extends NotesTrimestre {
    private double noteTp;


    @Override
    public Object calculate() {

        this.moy = (noteCont + noteTp + noteDev + (2 * noteEx))/5;
        return moy;
    }

    public double getNoteTp() {
        return noteTp;
    }

    public void setNoteTp(double noteTp) {
        this.noteTp = noteTp;
    }


}
