package com.senla.core.dto;

import java.util.List;

public record GetProfanityByArtistDto(
        List<String> artistNames
) {
}
