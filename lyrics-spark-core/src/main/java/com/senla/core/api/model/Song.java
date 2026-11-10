package com.senla.core.api.model;

import java.io.Serializable;

public class Song implements Serializable {
    private String artistName;
    private String geniusArtistName;
    private String title;
    private String lyric;

    public Song() {
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGeniusArtistName() {
        return geniusArtistName;
    }

    public void setGeniusArtistName(String geniusArtistName) {
        this.geniusArtistName = geniusArtistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public enum SongColumn {
        ARTIST("Artist"),
        GENIUS_ARTIST_NAME("Genius Artist Name"),
        TITLE("Title"),
        LYRIC("Lyric");

        SongColumn(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }

        private final String column;
    }
}
