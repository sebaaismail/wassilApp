package com.sebaainf.ismPoiLib;

import com.sebaainf.main.StudentCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ismail on 24/08/2019.
 */
public class IPoiSheetCalculator implements IPoiCalculator {

    private Map<IPoiObject, IPoiResults> results = new HashMap<>();

    @Override
    public void calculate() {

    }

    public void calculate(IPoiSheet sheet) {
        ObjCalculator calculator = new StudentCalculator();
        for(IPoiObject obj:sheet.getObjectsCollection().values()){
            results.putAll(calculator.calculate(obj));
        }
    }

    public Map<IPoiObject, IPoiResults> getResults() {
        return results;
    }
}
