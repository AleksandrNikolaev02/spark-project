package com.senla.core.api.profanity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Tokenizer {
    private static final Pattern TOKEN = Pattern.compile("[\\p{L}\\p{N}]+");

    public static List<String> tokenize(String normalizedText) {
        List<String> tokens = new ArrayList<>();
        Matcher m = TOKEN.matcher(normalizedText);
        while (m.find()) tokens.add(m.group());
        return tokens;
    }
}
