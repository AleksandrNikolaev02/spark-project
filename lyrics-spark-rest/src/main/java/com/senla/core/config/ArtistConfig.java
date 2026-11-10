package com.senla.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ArtistConfig {
    @Value("${spark.dataset-dir}")
    private String path;
    @Value("${spark.artists-dir}")
    private String artistsPath;
}
