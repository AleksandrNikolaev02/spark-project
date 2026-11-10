package com.senla.core.api.mapper.spark;

import com.senla.core.api.model.Song;
import org.apache.spark.sql.Row;

public class SongMapper implements TypedMapFunction<Song> {
    @Override
    public Song call(Row s) {
        Song song = new Song();

        song.setArtistName(s.getString(0));
        song.setGeniusArtistName(s.getString(1));
        song.setTitle(s.getString(2));
        song.setLyric(s.getString(3));

        return song;
    }

    @Override
    public Class<Song> getOutputClass() {
        return Song.class;
    }
}
