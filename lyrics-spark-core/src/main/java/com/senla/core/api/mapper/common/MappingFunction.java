package com.senla.core.api.mapper.common;

import org.apache.spark.sql.Row;

public interface MappingFunction<T> {
    T map(Row data);
    Class<T> getOutputClass();
}
