package com.senla.core.api.profanity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Определяет, является ли (уже нормализованное) слово нецензурным.
 * <p>
 * Работает в три этапа:
 * <ol>
 *   <li>встроенные «семейства» корней (хуй, пизд, еб, бляд, сук, ...) с учётом приставок
 *   и окончаний — покрывают «нахуй», «заебал», «суку», «охуенно» и т.д.;</li>
 *   <li>отдельные слова-исключения (стоп-лист), которые похожи на мат, но им не являются;</li>
 *   <li>пользовательский словарь из {@code dictionary-ru.properties}: слова короче 4 букв
 *   сравниваются точно, остальные — по префиксу. Фразы (с пробелами) игнорируются,
 *   т.к. текст разбивается на отдельные слова.</li>
 * </ol>
 * Слова с маскировкой («бл*дь», «х*й») восстанавливаются подбором пропущенной буквы.
 */
public class ProfanityMatcher implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String CYR_ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String LAT_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private static final String P_EB = "(?:на|по|за|вы|до|от|раз|рас|с|у|при|пере|под|об|про|из|ис|не|ни|недо"
            + "|съ|разъ|разь|объ|подъ|отъ|въ|взъ|долбо|мудо|хуе|пизде)?";
    private static final String P_ALL = "(?:на|по|за|вы|до|от|раз|рас|с|у|при|пере|под|об|про|из|ис|не|ни|недо"
            + "|съ|разъ|разь|объ|подъ|отъ|въ|взъ|а|в|о|во|долбо|мудо|хуе|пизде)?";
    private static final String P_NONE = "";

    private static final List<Family> CYRILLIC_FAMILIES = List.of(
            family("хуй", P_ALL, "ху[йеяю]"),
            family("пизд", P_ALL, "пиз[дж]"),
            family("еб", P_EB, "еб(?:а|у|и|ло|ла|ыр|ок|ош|ет|еш|ут|ют|ну)|еб$"),
            family("хер", P_ALL, "хер(?:$|а|ом|ня|ов|овин|овый|ово)"),
            family("бляд", P_NONE, "бля(?:$|д|т|х)|блэ[дт]|блеа[дт]"),
            family("сук", P_NONE, "сук(?:а|и|е|у|ой|ою|ам|ами|ах)$|сукин|суч(?:к[аиоуе]|ек|ар|ий|онок)"),
            family("муд", P_NONE, "муд(?:ак|ил|о)"),
            family("залуп", P_NONE, "залуп"),
            family("манд", P_NONE, "манд(?:а|ы|е|у|ой|ою|ам|ах|ами)$"),
            family("пидор", P_NONE, "пид(?:о|а)р|пидр|пед(?:ик|рил)(?:а|у|ом|ы|и|ов|ам|ах|ами|е)?$"),
            family("гандон", P_NONE, "г[ао]ндон"),
            family("шлюх", P_NONE, "шлю[хш]"),
            family("жоп", P_NONE, "жоп(?:а|у|е|ы|ой|ою|ам|ами|ах|ка|ку|ке|ок|ень)$|жопо|жопн"),
            family("говн", P_NONE, "г[ао]вн"),
            family("дерьм", P_NONE, "дерьм"),
            family("срат", P_NONE, "ср(?:ат|ал|ан)"),
            family("мраз", P_NONE, "мраз(?:ь|и|ей|ью|ота)"),
            family("тварь", P_NONE, "твар(?:ь|и|ей|ью|ям|ями)$"),
            family("сволоч", P_NONE, "сволоч|сволат"),
            family("ублюд", P_NONE, "ублюд"),
            family("дурак", P_NONE, "дур(?:а|ы|е|у|ой|ою|ам|ами|ах)$|дурак|дурочк"),
            family("гнида", P_NONE, "гнид(?:а|ы|е|у|ой)$")
    );

    private static final List<Family> LATIN_FAMILIES = List.of(
            family("fuck", P_NONE, "(?:mother|motha)?(?:fu?ck|fuk)"),
            family("shit", P_NONE, "(?:bull|horse|dip)?shit"),
            family("bitch", P_NONE, "bitch"),
            family("nigga", P_NONE, "nigg(?:a|er)"),
            family("cunt", P_NONE, "cunt"),
            family("whore", P_NONE, "whor(?:e|es)$"),
            family("slut", P_NONE, "slut"),
            family("asshole", P_NONE, "asshole"),
            family("bastard", P_NONE, "bastard"),
            family("pussy", P_NONE, "puss(?:y|ies)$"),
            family("dick", P_NONE, "dick(?:s|head)?$"),
            family("cock", P_NONE, "cock(?:s|sucker)?$"),
            // транслит русского мата
            family("blyat", P_NONE, "bly(?:a|at|ad)|cyka|suka$"),
            family("huy", P_NONE, "(?:na|po|do)?(?:hu|xu)y|nahui"),
            family("pizd", P_NONE, "pizd|pidor|pidar|ebat|ebal|ebanut")
    );

    /** Точные слова, которые стоят отдельно и не подходят под корни. */
    private static final Set<String> EXACT = Set.of("нах", "пох", "чмо");

    /** Слова, похожие на мат, но им не являющиеся. */
    private static final Set<String> STOP_LIST = Set.of("ебионит", "ебионитов");

    private final Set<String> dictionaryExact = new HashSet<>();
    private final Set<String> dictionaryRoots = new HashSet<>();
    private int minRootLength = Integer.MAX_VALUE;
    private int maxRootLength = 0;

    public ProfanityMatcher(List<String> dictionary) {
        for (String raw : dictionary) {
            if (raw == null) continue;
            String word = TextNormalizer.normalize(raw.trim().toLowerCase(Locale.ROOT));
            if (word.isEmpty() || word.indexOf(' ') >= 0) continue;
            if (word.length() < 4) {
                dictionaryExact.add(word);
            } else {
                dictionaryRoots.add(word);
                minRootLength = Math.min(minRootLength, word.length());
                maxRootLength = Math.max(maxRootLength, word.length());
            }
        }
    }

    /**
     * @param token слово после {@link TextNormalizer} и {@link Tokenizer}
     * @return ключ найденного корня/семейства либо {@code null}, если слово не матерное
     */
    public String match(String token) {
        if (token == null || token.isEmpty()) return null;
        if (token.indexOf('*') >= 0) return matchMasked(token);
        return matchPlain(token);
    }

    private String matchPlain(String token) {
        if (STOP_LIST.contains(token)) return null;
        if (EXACT.contains(token)) return token;

        char first = token.charAt(0);
        List<Family> families = TextNormalizer.isCyrillic(first) ? CYRILLIC_FAMILIES
                : (Character.isLetter(first) ? LATIN_FAMILIES : List.of());
        for (Family f : families) {
            if (f.pattern.matcher(token).find()) return f.name;
        }

        return matchDictionary(token);
    }

    private String matchDictionary(String token) {
        if (dictionaryExact.contains(token)) return token;
        int max = Math.min(token.length(), maxRootLength);
        for (int len = minRootLength; len <= max; len++) {
            String prefix = token.substring(0, len);
            if (dictionaryRoots.contains(prefix)) return prefix;
        }
        return null;
    }

    private String matchMasked(String token) {
        String stripped = token.replace("*", "");
        if (stripped.length() < 2) return null;

        String direct = matchPlain(stripped);
        if (direct != null) return direct;

        // одна замаскированная буква: подбираем её
        int star = token.indexOf('*');
        if (star != token.lastIndexOf('*')) return null;
        String alphabet = TextNormalizer.isCyrillic(stripped.charAt(0)) ? CYR_ALPHABET : LAT_ALPHABET;
        for (int i = 0; i < alphabet.length(); i++) {
            String candidate = token.substring(0, star) + alphabet.charAt(i) + token.substring(star + 1);
            String found = matchPlain(candidate);
            if (found != null) return found;
        }
        return null;
    }

    private static Family family(String name, String prefix, String body) {
        return new Family(name, Pattern.compile("^" + prefix + "(?:" + body + ")"));
    }

    private record Family(String name, Pattern pattern) {
    }
}
