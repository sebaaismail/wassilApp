package com.sebaainf.main;

/**
 * Created by Ismail on 24/08/2019.
 */
public class NoTpNotesTrim extends NotesTrimestre {
    @Override
    public Object calculate() {
        this.moy = (noteCont + noteDev + (2 * noteEx))/4;
        return moy;
    }

}
