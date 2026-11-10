package com.senla.core.controller.impl;

import com.senla.core.api.dto.ArtistCountDto;
import com.senla.core.api.dto.ArtistReport;
import com.senla.core.api.dto.SongStats;
import com.senla.core.service.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistsControllerImpl {
    private ArtistsService artistsService;

    @GetMapping("/getSongs")
    public List<ArtistCountDto> getCountSongsOfArtists() {
        return artistsService.getCountSongsOfArtists();
    }

    @GetMapping("/getSongsRdd")
    public List<ArtistCountDto> getCountSongsOfArtistsRdd() {
        return artistsService.getCountSongsOfArtistsRDD();
    }

    @GetMapping("/getArtistReport")
    public ArtistReport getArtistReport(@RequestParam String artistName) {
        return artistsService.getProfanityByArtistName(artistName);
    }

    @GetMapping("/getArtistsReport")
    public List<ArtistReport> getArtistsReport(@RequestParam int top) {
        return artistsService.getMostProfanityArtist(top);
    }

    @GetMapping("/getArtistSongReport")
    public List<SongStats> getArtistSongReport(@RequestParam String name, @RequestParam int top) {
        return artistsService.getMostProfanitySongOfArtist(name, top);
    }

    @Autowired
    public void setArtistsService(ArtistsService artistsService) {
        this.artistsService = artistsService;
    }
}
