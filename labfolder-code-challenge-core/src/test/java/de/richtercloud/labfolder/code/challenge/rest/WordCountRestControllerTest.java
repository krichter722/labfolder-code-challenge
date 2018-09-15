package de.richtercloud.labfolder.code.challenge.rest;

import de.richtercloud.labfolder.code.challenge.wordsupply.WordSupplier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author richter
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class WordCountRestControllerTest {

    @Test
    public void testWordCountEmpty() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        String keyword = "";
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        WordCountResponse expResult = new WordCountResponse("", 0, new LinkedList<>());
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }

    @Test
    public void testWordCountNoMatch() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        String keyword = "a";
        List<String> provision = new LinkedList<>(Arrays.asList("bb", "cc"));
        when(wordSupplier.supply("username")).thenReturn(provision.stream());
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        WordCountResponse expResult = new WordCountResponse(keyword, 0, new LinkedList<>());
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }

    @Test
    public void testWordCountNoMatchSimilar() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        String keyword = "a";
        List<String> provision = new LinkedList<>(Arrays.asList("ab", "c"));
        when(wordSupplier.supply("username")).thenReturn(provision.stream());
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        WordCountResponse expResult = new WordCountResponse(keyword, 0, new LinkedList<>(Arrays.asList("ab", "c" //substitution counts as 1
                )));
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }

    @Test
    public void testWordCountMatchNoSimilar() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        String keyword = "a";
        List<String> provision = new LinkedList<>(Arrays.asList("a", "cc"));
        when(wordSupplier.supply("username")).thenReturn(provision.stream());
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        WordCountResponse expResult = new WordCountResponse(keyword, 1, new LinkedList<>());
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }

    @Test
    public void testWordCountMatchSimilar() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        String keyword = "a";
        List<String> provision = new LinkedList<>(Arrays.asList("a", "ab", "ac", "c", "cc"));
        when(wordSupplier.supply("username")).thenReturn(provision.stream());
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        WordCountResponse expResult = new WordCountResponse(keyword, 1, new LinkedList<>(Arrays.asList("ab", "ac", "c")));
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }

    @Test
    public void testProvideWordsEmpty() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        List<String> words = new LinkedList<>();
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        instance.provideWords("username",
                words);
        String keyword = "a";
        WordCountResponse expResult = new WordCountResponse(keyword, 0, new LinkedList<>());
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }

    @Test
    public void testProvideWordsNonEmpty() {
        WordSupplier wordSupplier = mock(WordSupplier.class);
        List<String> words = new LinkedList<>(Arrays.asList("x", "y"));
        WordCountRestController instance = new WordCountRestController(wordSupplier);
        instance.provideWords("username",
                words);
        String keyword = "a";
        WordCountResponse expResult = new WordCountResponse(keyword, 0, new LinkedList<>());
        WordCountResponse result = instance.wordCount("username",
                keyword);
        assertEquals(expResult, result);
    }
}
