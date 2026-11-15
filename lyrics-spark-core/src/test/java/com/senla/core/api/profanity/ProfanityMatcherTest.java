package com.senla.core.api.profanity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProfanityMatcherTest {

    private final ProfanityMatcher matcher = new ProfanityMatcher(List.of("козел", "дебил", "дурить"));

    /** Прогоняет слово через тот же конвейер, что и ArtistAnalyzer. */
    private String match(String word) {
        List<String> tokens = Tokenizer.tokenize(TextNormalizer.normalize(word));
        return tokens.isEmpty() ? null : matcher.match(tokens.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "хуй", "нахуй", "похуй", "хуйня", "дохуя", "нихуя", "охуенно", "ахуеть",
            "пизда", "пиздец", "спиздил", "ебал", "заебал", "заебись", "выебал", "въебал", "долбоеб", "уебок",
            "блядь", "блять", "бля", "бляди", "сука", "суку", "сукой", "сучка", "мудак", "жопу", "говна", "дерьма",
            "пидор", "педик", "шлюха", "залупа", "нахер", "дурак", "дебилы", "козел",
            "fuck", "motherfucker", "shit", "bitch", "nigga"
    })
    void detectsProfanity(String word) {
        assertNotNull(match(word), word);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "небо", "себя", "себестоимость", "веб", "вебинар", "требует", "учебу", "хлеб", "хлебушек",
            "сукно", "сукачев", "мандарин", "педикюр", "херсон", "херувим", "хуан", "бляшка", "блин",
            "срамота", "срабатывать", "класс", "творец", "дурацкий", "ебионит",
            "assist", "niger", "shiitake", "hello", "2024"
    })
    void ignoresInnocentWords(String word) {
        assertNull(match(word), word);
    }

    @ParameterizedTest
    @ValueSource(strings = {"хуууй", "бляяять", "fuuuck", "xуй", "бл*дь", "х*й", "п*зда", "бл@дь"})
    void detectsObfuscatedProfanity(String word) {
        assertNotNull(match(word), word);
    }

    @Test
    void groupsHitsByRoot() {
        assertEquals(match("нахуй"), match("хуйня"));
        assertEquals("хуй", match("нахуй"));
    }

    @Test
    void doesNotTouchEnglishWordsAndNumbers() {
        assertEquals("the 100 hat", TextNormalizer.normalize("The 100 HAT"));
    }

    @Test
    void ignoresPhrasesInDictionary() {
        ProfanityMatcher m = new ProfanityMatcher(List.of("иди на работу"));
        assertNull(m.match("иди"));
        assertNull(m.match("работу"));
    }
}
