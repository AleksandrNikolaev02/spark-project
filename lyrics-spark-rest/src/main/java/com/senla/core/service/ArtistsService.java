package com.senla.core.service;

import com.senla.core.api.LyricApi;
import com.senla.core.api.dto.ArtistCountDto;
import com.senla.core.api.dto.ArtistReport;
import com.senla.core.api.dto.ArtistSongReportDto;
import com.senla.core.api.dto.SongStats;
import com.senla.core.api.function.ArtistAnalyzeFunction;
import com.senla.core.api.mapper.common.MapperHelper;
import com.senla.core.api.mapper.spark.MapperSolver;
import com.senla.core.api.model.Song;
import com.senla.core.config.ArtistConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistsService {
    private ArtistConfig config;
    private MapperHelper mapperHelper;
    private MapperSolver mapperSolver;
    private LyricApi lyricApi;
    private ArtistAnalyzeFunction analyzeFunction;

    public List<ArtistCountDto> getCountSongsOfArtistsRDD() {
        var mapper = mapperSolver.getMapperFunction(Song.class);

        return lyricApi.getCountSongsOfArtistsRDD(mapper, config.getArtistsPath());
    }

    public List<ArtistCountDto> getCountSongsOfArtists() {
        var mapper = mapperHelper.getFunction(ArtistCountDto.class);

        return lyricApi.getCountSongsOfArtists(config.getArtistsPath(), mapper);
    }

    public List<ArtistReport> getMostProfanityArtist(int top) {
        var mapper = mapperSolver.getMapperFunction(Song.class);

        return lyricApi.getMostProfanityArtist(mapper, analyzeFunction, config.getArtistsPath(), top);
    }

    public ArtistReport getProfanityByArtistName(String artistName) {
        var mapper = mapperSolver.getMapperFunction(Song.class);

        return lyricApi.getProfanityByArtistName(mapper, analyzeFunction, config.getArtistsPath(), artistName);
    }

    public List<SongStats> getMostProfanitySongOfArtist(String artistName, int top) {
        var mapper = mapperSolver.getMapperFunction(Song.class);

        return lyricApi.getMostProfanitySongOfArtist(mapper, analyzeFunction, config.getArtistsPath(), artistName, top);
    }

    @Autowired
    public void setConfig(ArtistConfig config) {
        this.config = config;
    }

    @Autowired
    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    @Autowired
    public void setMapperSolver(MapperSolver mapperSolver) {
        this.mapperSolver = mapperSolver;
    }

    @Autowired
    public void setLyricApi(LyricApi lyricApi) {
        this.lyricApi = lyricApi;
    }

    @Autowired
    public void setAnalyzeFunction(ArtistAnalyzeFunction analyzeFunction) {
        this.analyzeFunction = analyzeFunction;
    }
}
