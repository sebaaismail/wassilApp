package com.sebaainf.main;

import com.sebaainf.ismPoiLib.IPoiObject;
import com.sebaainf.ismPoiLib.IPoiParams;
import com.sebaainf.ismPoiLib.IPoiResults;
import com.sebaainf.ismPoiLib.ObjCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ismail on 26/08/2019.
 */
public class StudentCalculator extends ObjCalculator {
    private double moy;
    private String takdir;
    private String irchad;

    @Override
    public Map<IPoiObject, IPoiResults> calculate(IPoiObject obj) {

        moy = calculateMoy(obj);

        takdir = ConfigFile.listTak[(int) moy];
        irchad = ConfigFile.listIrch[(int) moy];

        IPoiResults res = (IPoiResults) new StudentResults(moy, takdir, irchad);
        HashMap<IPoiObject, IPoiResults> out = new HashMap<>();
        out.put(obj, res);
        return out;
    }

    private double calculateMoy(IPoiObject obj) {
        IPoiParams notes = ((Student)obj).getNotes();
        double moy = (double) notes.calculate();
        return moy;
    }

    protected static String irchadateByCompare(double moyTri1, double moyTri2) {
        String s = "";
        double bonus = moyTri2 - moyTri1;

        if (bonus >= ConfigFile.bonusUp && moyTri2 > 12) {
            s = ConfigFile.irchUp;
            System.out.println(s);
        } else if (bonus <= - ConfigFile.bonusDown && moyTri2 < 13 && moyTri2 >= 8) {
            s = ConfigFile.irchDown;
            System.out.println(s);
        } else {
            s = ConfigFile.listIrch[(int) moyTri2];
        }
        System.out.println("irchadateByCompare ( " + moyTri1 + "," + moyTri2 + " ) : " + s);
        return s;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }

    public void setTakdir(String takdir) {
        this.takdir = takdir;
    }

    public void setIrchad(String irchad) {
        this.irchad = irchad;
    }
}