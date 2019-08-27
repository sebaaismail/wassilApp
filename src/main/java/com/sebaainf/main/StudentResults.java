package com.sebaainf.main;

import com.sebaainf.ismPoiLib.IPoiResults;

/**
 * Created by Ismail on 24/08/2019.
 */
public class StudentResults implements IPoiResults {
    private double moy;
    private String takdirate;
    private String irchadate;

    StudentResults(double moy, String takdirate, String irchadate){
        this.moy = moy;
        this.takdirate = takdirate;
        this.irchadate = irchadate;
    }

    public double getMoy() {
        return moy;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }

    public String getTakdirate() {
        return takdirate;
    }

    public void setTakdirate(String takdirate) {
        this.takdirate = takdirate;
    }

    public String getIrchadate() {
        return irchadate;
    }

    public void setIrchadate(String irchadate) {
        this.irchadate = irchadate;
    }
}
