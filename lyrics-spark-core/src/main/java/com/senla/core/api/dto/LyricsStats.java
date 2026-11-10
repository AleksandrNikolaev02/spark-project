package com.senla.core.api.dto;

import java.util.Map;

public record LyricsStats(
        long totalTokens,
        long badTokens,
        long uniqueBadLemmas,
        double badRatio,
        Map<String, Integer> hits
) {
}
