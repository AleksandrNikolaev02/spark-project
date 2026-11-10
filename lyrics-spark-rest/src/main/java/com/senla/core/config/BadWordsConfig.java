package com.senla.core.config;

import com.senla.core.api.function.ArtistAnalyzeFunction;
import com.senla.core.api.profanity.ArtistAnalyzer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@Getter
@PropertySource(value = "classpath:/dictionary-ru.properties", encoding = "UTF8")
public class BadWordsConfig {
    @Value("${bad-words}")
    private List<String> badWords;

    @Bean
    public ArtistAnalyzeFunction analyzeFunction() {
        return new ArtistAnalyzer(badWords);
    }
}
