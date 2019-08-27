package com.sebaainf.ismPoiLib;

import java.util.Map;

/**
 * Created by Ismail on 26/08/2019.
 */
public abstract class ObjCalculator {
    abstract public Map<IPoiObject, IPoiResults>  calculate(IPoiObject obj);
}
