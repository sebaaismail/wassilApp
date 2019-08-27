package com.sebaainf.ismPoiLib;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ismail on 24/08/2019.
 */
public abstract class IPoiSheet {
    protected Map<Row, IPoiObject> objectsCollection = null;

    protected abstract void addObject(Row row, IPoiObject obj);

    public Map<Row, IPoiObject> getObjectsCollection() {
        return objectsCollection;
    }
}
