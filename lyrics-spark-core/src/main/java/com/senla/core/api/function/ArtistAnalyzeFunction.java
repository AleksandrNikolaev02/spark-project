package com.senla.core.api.function;

import com.senla.core.api.dto.ArtistReport;
import com.senla.core.api.dto.SongStats;
import com.senla.core.api.model.Song;

import java.io.Serializable;
import java.util.List;

public interface ArtistAnalyzeFunction
        extends Serializable {
    ArtistReport analyze(String artist, List<Song> songs);
    SongStats analyzeSong(Song song);
}
