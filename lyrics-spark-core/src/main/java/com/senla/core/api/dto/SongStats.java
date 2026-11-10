package com.senla.core.api.dto;

public class SongStats {
    private String title;
    private long totalTokens;
    private long badTokens;
    private double badRatio;

    public SongStats() {
    }

    public SongStats(String title, long totalTokens, long badTokens, double badRatio) {
        this.title = title;
        this.totalTokens = totalTokens;
        this.badTokens = badTokens;
        this.badRatio = badRatio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "SongStats{" +
                "title='" + title + '\'' +
                ", totalTokens=" + totalTokens +
                ", badTokens=" + badTokens +
                ", badRatio=" + badRatio +
                '}';
    }
}
