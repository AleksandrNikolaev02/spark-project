package com.senla.core.api.dto;

public class ArtistReport {
    private String artist;
    private long totalTokens;
    private long badTokens;
    private double badRatio;

    public ArtistReport() {
    }

    public ArtistReport(String artist, long totalTokens, long badTokens, double badRatio) {
        this.artist = artist;
        this.totalTokens = totalTokens;
        this.badTokens = badTokens;
        this.badRatio = badRatio;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(long totalTokens) {
        this.totalTokens = totalTokens;
    }

    public long getBadTokens() {
        return badTokens;
    }

    public void setBadTokens(long badTokens) {
        this.badTokens = badTokens;
    }

    public double getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(double badRatio) {
        this.badRatio = badRatio;
    }

    @Override
    public String toString() {
        return "ArtistReport{" +
                "artist='" + artist + '\'' +
                ", totalTokens=" + totalTokens +
                ", badTokens=" + badTokens +
                ", badRatio=" + badRatio +
                '}';
    }
}
