package com.teoco.mongo.rop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roysha on 2/24/2016.
 */
public class RopPeriodCollection {
    public Map<RopPeriod, List<File>> mapRop = new HashMap<RopPeriod, List<File>>();
    public List<RopPeriod> orderRop = new ArrayList<RopPeriod>();

    public int size() {
        return orderRop.size();
    }
}
