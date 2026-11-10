package com.senla.core.api.mapper;

import com.senla.core.api.model.Song;

import java.io.Serializable;

public class SimpleSongMapper implements Serializable {
    private final String SPLITERATOR = ",";

    public Song mapStringToSong(String row) {
        System.out.println(row);
        String[] tokens = row.split(SPLITERATOR);

        Song song = new Song();
        song.setArtistName(tokens[0]);
        song.setGeniusArtistName(tokens[1]);
        song.setTitle(tokens[2]);
        song.setLyric(tokens[3]);

        return song;
    }
}
