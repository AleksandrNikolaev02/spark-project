package com.senla.core.jobs;

import com.senla.core.api.LyricApi;
import com.senla.core.api.dto.SongStats;
import com.senla.core.api.function.ArtistAnalyzeFunction;
import com.senla.core.api.mapper.spark.SongMapper;
import com.senla.core.api.mapper.spark.TypedMapFunction;
import com.senla.core.api.model.Song;
import com.senla.core.api.profanity.ArtistAnalyzer;
import com.senla.core.constants.ApiStringConstants;
import com.senla.core.enums.ProcessingFunction;
import org.apache.spark.sql.SparkSession;

import java.util.List;

import static com.senla.core.loader.FileLoader.loadBadWords;

public class MostProfanitySongOfArtistJob implements SparkJob {
    @Override
    public void execute(SparkSession sparkSession) {
        TypedMapFunction<Song> mapper = new SongMapper();

        var badWords = loadBadWords();

        ArtistAnalyzeFunction analyzeFunction = new ArtistAnalyzer(badWords);

        LyricApi api = new LyricApi(sparkSession);

        List<SongStats> result = api.getMostProfanitySongOfArtist(mapper, analyzeFunction,
                ApiStringConstants.PATH_TO_SONGS, ApiStringConstants.DEFAULT_ARTIST_NAME, ApiStringConstants.TOP_PROFANITY_ARTIST);

        result.forEach(System.out::println);
    }

    @Override
    public ProcessingFunction getFunction() {
        return ProcessingFunction.MOST_PROFANITY_SONG_OF_ARTIST;
    }
}
