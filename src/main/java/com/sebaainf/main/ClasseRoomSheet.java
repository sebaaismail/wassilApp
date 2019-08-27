package com.sebaainf.main;

import com.sebaainf.ismPoiLib.IPoiObject;
import com.sebaainf.ismPoiLib.IPoiSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ismail on 25/08/2019.
 */
public class ClasseRoomSheet extends IPoiSheet {


    private Sheet sheet;


    public ClasseRoomSheet(Sheet sheet){
        this.sheet = sheet;
        objectsCollection = new HashMap<Row, IPoiObject>();
    }

    public void addObject(Row row, IPoiObject obj) {
        this.objectsCollection.put(row, obj);
    }

    public Sheet getSheet() {
        return sheet;
    }

    public Map<Row, IPoiObject> getObjectsCollection() {
        return objectsCollection;
    }
}
