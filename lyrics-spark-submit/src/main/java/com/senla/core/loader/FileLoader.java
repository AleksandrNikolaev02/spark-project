package com.senla.core.loader;

import com.senla.core.ArtistJob;
import com.senla.core.constants.ApiStringConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FileLoader {
    public static List<String> loadBadWords() {
        var inputStream = ArtistJob.class.getResourceAsStream(ApiStringConstants.PATH_TO_BAD_WORDS);
        if (inputStream == null) {
            throw new IllegalArgumentException("Файл не найден: " + ApiStringConstants.PATH_TO_BAD_WORDS);
        }

        String res = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                res = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of(res.split(","));
    }
}
