package com.senla.core.api.mapper.spark;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public interface TypedMapFunction<Out> extends MapFunction<Row, Out> {
    Class<?> getOutputClass();
}
