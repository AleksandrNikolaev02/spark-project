package com.senla.core.jobs;

import com.senla.core.api.LyricApi;
import com.senla.core.api.dto.ArtistCountDto;
import com.senla.core.api.mapper.common.MappingFunction;
import com.senla.core.api.mapper.common.RowToArtistCountDtoMapper;
import com.senla.core.api.mapper.spark.SongMapper;
import com.senla.core.api.mapper.spark.TypedMapFunction;
import com.senla.core.api.model.Song;
import com.senla.core.constants.ApiStringConstants;
import com.senla.core.enums.ProcessingFunction;
import org.apache.spark.sql.SparkSession;

import java.util.List;

public class CountSongsOfArtistsRddJob implements SparkJob {
    @Override
    public void execute(SparkSession sparkSession) {
        TypedMapFunction<Song> mapper = new SongMapper();

        LyricApi api = new LyricApi(sparkSession);

        List<ArtistCountDto> result =
                api.getCountSongsOfArtistsRDD(mapper, ApiStringConstants.PATH_TO_SONGS);

        result.forEach(System.out::println);
    }

    @Override
    public ProcessingFunction getFunction() {
        return ProcessingFunction.COUNT_SONGS_OF_ARTISTS_RDD;
    }
}
