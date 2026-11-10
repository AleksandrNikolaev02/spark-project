package com.senla.core.api.profanity;

import java.util.Locale;
import java.util.Map;

public final class TextNormalizer {
    private static final Map<Character, Character> HOMOGLYPHS = Map.ofEntries(
            Map.entry('a', 'а'), Map.entry('c', 'с'), Map.entry('e', 'е'),
            Map.entry('o', 'о'), Map.entry('p', 'р'), Map.entry('x', 'х'),
            Map.entry('y', 'у'), Map.entry('k', 'к'), Map.entry('m', 'м'),
            Map.entry('t', 'т'), Map.entry('h', 'н'), Map.entry('b', 'в'),
            Map.entry('0', 'о'), Map.entry('3', 'з'), Map.entry('6', 'б')
    );

    public static String normalize(String text) {
        if (text == null) return "";
        String lower = text.toLowerCase(Locale.ROOT);

        StringBuilder sb = new StringBuilder(lower.length());
        for (char c : lower.toCharArray()) {
            char mapped = HOMOGLYPHS.getOrDefault(c, c);
            if (mapped == 'ё') mapped = 'е';
            sb.append(mapped);
        }

        return sb.toString().replaceAll("(\\p{L})\\1{2,}", "$1$1");
    }
}
