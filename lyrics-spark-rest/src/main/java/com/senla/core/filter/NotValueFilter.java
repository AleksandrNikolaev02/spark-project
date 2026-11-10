package com.senla.core.filter;

import java.io.Serializable;

public class NotValueFilter implements Serializable {
    public static boolean isValueNumber(String row) {
        String[] elements = row.split(";");
        try {
            Float.parseFloat(elements[3]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
