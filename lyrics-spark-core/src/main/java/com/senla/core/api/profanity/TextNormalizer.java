package com.senla.core.api.profanity;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Приводит текст песни к виду, удобному для поиска нецензурных слов.
 * <p>
 * Нормализация выполняется отдельно для каждого слова:
 * <ul>
 *   <li>нижний регистр, «ё» → «е»;</li>
 *   <li>символы маскировки ({@code * @ #}) внутри слова заменяются одной «*»
 *   (например, «бл*дь», «х@й»), дальше их разбирает {@link ProfanityMatcher};</li>
 *   <li>латинские буквы и цифры-«двойники» заменяются на кириллицу только в словах,
 *   где уже есть кириллица («xуй», «п3здец»), чтобы не портить английские слова и числа;</li>
 *   <li>растянутые буквы схлопываются («хуууй» → «хуй», «fuuuck» → «fuck»).</li>
 * </ul>
 */
public final class TextNormalizer {

    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{N}]+(?:[*@#]+[\\p{L}\\p{N}]*)*");
    private static final Pattern MASK_RUN = Pattern.compile("[*@#]+");
    private static final Pattern CYRILLIC_REPEATS = Pattern.compile("(\\p{L})\\1+");
    private static final Pattern LATIN_REPEATS = Pattern.compile("(\\p{L})\\1{2,}");

    private static final Map<Character, Character> HOMOGLYPHS = Map.ofEntries(
            Map.entry('a', 'а'), Map.entry('c', 'с'), Map.entry('e', 'е'),
            Map.entry('o', 'о'), Map.entry('p', 'р'), Map.entry('x', 'х'),
            Map.entry('y', 'у'), Map.entry('k', 'к'), Map.entry('m', 'м'),
            Map.entry('t', 'т'), Map.entry('h', 'н'), Map.entry('b', 'в'),
            Map.entry('u', 'и'), Map.entry('n', 'п'), Map.entry('r', 'г'),
            Map.entry('0', 'о'), Map.entry('3', 'з'), Map.entry('6', 'б')
    );

    private TextNormalizer() {
    }

    public static String normalize(String text) {
        if (text == null) return "";
        String lower = text.toLowerCase(Locale.ROOT);
        return WORD.matcher(lower).replaceAll(r -> java.util.regex.Matcher.quoteReplacement(normalizeToken(r.group())));
    }

    static String normalizeToken(String token) {
        String t = token.replace('ё', 'е');
        t = MASK_RUN.matcher(t).replaceAll("*");

        boolean hasCyrillic = false;
        boolean hasLatinOrDigit = false;
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (isCyrillic(c)) {
                hasCyrillic = true;
            } else if (Character.isLetterOrDigit(c)) {
                hasLatinOrDigit = true;
            }
        }

        if (hasCyrillic && hasLatinOrDigit) {
            StringBuilder sb = new StringBuilder(t.length());
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                sb.append(HOMOGLYPHS.getOrDefault(c, c));
            }
            t = sb.toString();
        }

        Pattern repeats = hasCyrillic ? CYRILLIC_REPEATS : LATIN_REPEATS;
        return repeats.matcher(t).replaceAll("$1");
    }

    static boolean isCyrillic(char c) {
        return Character.UnicodeScript.of(c) == Character.UnicodeScript.CYRILLIC;
    }
}
