package com.senla.core;

import com.senla.core.enums.ProcessingFunction;
import com.senla.core.processor.FunctionProcessor;
import org.apache.spark.sql.SparkSession;

public class ProfanityByArtistNameJob {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder()
                .appName("Artist Analyzer")
                .master("local[*]")
                .getOrCreate();

        try {
            FunctionProcessor processor = new FunctionProcessor();
            processor.execute(sparkSession, ProcessingFunction.PROFANITY_BY_ARTIST_NAME);
        } finally {
            sparkSession.stop();
        }
    }
}
