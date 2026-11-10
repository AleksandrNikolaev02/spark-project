package com.senla.core.filter;

import com.senla.core.model.Data;

import java.io.Serializable;

public class AnomaliesFilter implements Serializable {

    public static boolean isAnomalies(Data data) {
        return data.getNetto().equalsIgnoreCase("0") ||
                data.getQuantity().equalsIgnoreCase("0");
    }
}
