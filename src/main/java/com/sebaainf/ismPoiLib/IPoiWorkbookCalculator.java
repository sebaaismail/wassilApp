package com.sebaainf.ismPoiLib;

import com.sebaainf.main.ClasseRoomSheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ismail on 24/08/2019.
 */
public class IPoiWorkbookCalculator implements IPoiCalculator {

    private Map<String,IPoiSheet> sheetsCollection;

    private Map<IPoiObject, IPoiResults> results = new HashMap<>();
    private Map<Row, IPoiObject> placeObj = new HashMap<>();

    public IPoiWorkbookCalculator(Map<String,IPoiSheet> sheetsCollection){
        this.setSheetsCollection(sheetsCollection);
    }

    @Override
    public void calculate() {
        IPoiSheetCalculator sheetCalc = new IPoiSheetCalculator();
        for(IPoiSheet sheet: sheetsCollection.values()){
            sheetCalc.calculate(sheet);
            results.putAll(sheetCalc.getResults());
            placeObj.putAll(((ClasseRoomSheet) sheet).getObjectsCollection());

        }


    }

    public void setSheetsCollection(Map<String, IPoiSheet> sheetsCollection) {
        this.sheetsCollection = sheetsCollection;
    }

    public Map<IPoiObject, IPoiResults> getResults() {
        return results;
    }

    public Map<Row,IPoiObject> getPlaceObjs() {
        return placeObj;
    }
}
