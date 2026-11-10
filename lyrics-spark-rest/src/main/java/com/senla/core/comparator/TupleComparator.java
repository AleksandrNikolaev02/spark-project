package com.senla.core.comparator;

import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;

public class TupleComparator implements Comparator<Tuple2<String, Float>>, Serializable {
    @Override
    public int compare(Tuple2<String, Float> a, Tuple2<String, Float> b) {
        if (a._2 - b._2 > 0f) return 1;
        if (a._2 - b._2 < 0f) return -1;
        return 0;
    }
}
