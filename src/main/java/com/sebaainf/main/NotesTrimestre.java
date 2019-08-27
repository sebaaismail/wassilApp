package com.sebaainf.main;

import com.sebaainf.ismPoiLib.IPoiParams;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ismail on 24/08/2019.
 */
public abstract class NotesTrimestre implements IPoiParams {

    protected double noteCont;
    protected double noteDev;
    protected double noteEx;

    protected double moy;

    public double getNoteCont()  {
        return noteCont;
    }
    public void setNoteCont(double noteCont)  {
        this.noteCont = noteCont;
    }

    public double getNoteDev()  {
        return noteDev;
    }
    public void setNoteDev(double noteDev)  {
        this.noteDev = noteDev;
    }

    public double getNoteEx() {
        return noteEx;
    }

    public void setNoteEx(double noteEx) {
        this.noteEx = noteEx;
    }

    public double calculateMoy(){

        calculate();
        return moy;

    }





}
