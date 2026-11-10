package com.senla.core.api.profanity;


import com.senla.core.api.dto.ArtistReport;
import com.senla.core.api.dto.LyricsStats;
import com.senla.core.api.dto.SongStats;
import com.senla.core.api.function.ArtistAnalyzeFunction;
import com.senla.core.api.model.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistAnalyzer implements Serializable, ArtistAnalyzeFunction {

    private final List<String> roots;
    private final Set<String> exactForms;

    public ArtistAnalyzer(List<String> words) {
        this.roots = words.stream()
                .filter(w -> w.length() >= 4)
                .toList();
        this.exactForms = words.stream()
                .filter(w -> w.length() < 4)
                .collect(Collectors.toSet());
    }

    public ArtistReport analyze(String artist, List<Song> songs) {
        List<SongStats> perSong = new ArrayList<>();
        long totalTokens = 0, totalBad = 0;

        for (Song song : songs) {
            LyricsStats s = analyze(song.getLyric());
            perSong.add(new SongStats(song.getTitle(), s.totalTokens(), s.badTokens(), s.badRatio()));
            totalTokens += s.totalTokens();
            totalBad += s.badTokens();
        }

        double artistRatio = totalTokens == 0 ? 0.0 : (double) totalBad / totalTokens;
        return new ArtistReport(artist, totalTokens, totalBad, artistRatio);
    }

    public SongStats analyzeSong(Song song) {
        var result = analyze(song.getLyric());

        return new SongStats(
                song.getTitle(),
                result.totalTokens(),
                result.badTokens(),
                result.badRatio()
        );
    }

    private LyricsStats analyze(String lyrics) {
        String normalized = TextNormalizer.normalize(lyrics);
        List<String> tokens = Tokenizer.tokenize(normalized);

        long total = tokens.size();
        long bad = 0;
        Map<String, Integer> hits = new HashMap<>();

        for (String token : tokens) {
            String root = match(token);
            if (root != null) {
                bad++;
                hits.merge(root, 1, Integer::sum);
            }
        }

        double ratio = total == 0 ? 0.0 : (double) bad / total;
        return new LyricsStats(total, bad, hits.size(), ratio, hits);
    }

    private String match(String token) {
        if (exactForms.contains(token)) return token;
        for (String root : roots) {
            if (token.startsWith(root)) return root;
        }
        return null;
    }
}
