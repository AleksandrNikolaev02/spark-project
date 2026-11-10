package com.senla.core.jobs;

import com.senla.core.enums.ProcessingFunction;
import org.apache.spark.sql.SparkSession;

public interface SparkJob {
    void execute(SparkSession sparkSession);

    ProcessingFunction getFunction();
}
