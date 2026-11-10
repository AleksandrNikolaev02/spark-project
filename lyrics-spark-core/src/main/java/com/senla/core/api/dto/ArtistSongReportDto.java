package com.senla.core.api.dto;

public record ArtistSongReportDto(
        String songName,
        long totalTokens,
        long badTokens,
        double badRatio
) {
}
