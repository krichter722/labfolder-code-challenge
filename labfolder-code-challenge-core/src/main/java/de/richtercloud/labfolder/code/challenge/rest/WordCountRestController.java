package de.richtercloud.labfolder.code.challenge.rest;

import de.richtercloud.labfolder.code.challenge.wordsupply.WordSupplier;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author richter
 */
@RestController
@RequestMapping(WordCountRestController.PATH)
public class WordCountRestController {
    public static final String PATH = "/wordcount";
    public static final String WORD_COUNT_METHOD = "/wordcount";
    public static final String PROVIDE_WORDS_METHOD = "/providewords";
    public static final String KEYWORD_PARAM = "keyword";
    public static final String USERNAME_PARAM = "username";
    @Autowired
    private WordSupplier wordSupplier;

    public WordCountRestController() {
    }

    /**
     * Unit test constructor to simulate dependency injection with mocks
     * ("write testable code").
     * @param wordSupplier the word supplier instance to use
     */
    protected WordCountRestController(WordSupplier wordSupplier) {
        this.wordSupplier = wordSupplier;
    }

    /**
     * Counts the occurances of {@code keyword} in all notebooks and provides a
     * list with similar words.Words are considered similar if their
     * Levenshtein distance is 1.
     *
     * @param keyword the word to search for (empty string is allowed, but
     *     doesn't make too much sense to specify)
     * @param username the name of the user whose notebook data to use for the
     *     search (researchers in a group might want to share their data with
     *     authorized users)
     * @return a JSON object with a field {@code keyword} repeating the keyword,
     *     a field {@code frequency} containing the number of occurances and a
     *     list of similar words.
     */
    @RequestMapping(WORD_COUNT_METHOD+"/{"+USERNAME_PARAM+"}")
    public WordCountResponse wordCount(@PathVariable(USERNAME_PARAM) String username,
            @RequestParam(KEYWORD_PARAM) String keyword) {
        //here should be check that the request comes from an authenticated and
        //authorized user/client using OAuth2 or a technique that is capable of
        //handling microservices (JSON web tokens) if there was more time

        List<String> similarWords = new LinkedList<>();
        long frequency = wordSupplier.supply(username).filter(word -> {
            int levenshteinDistance = LevenshteinDistance.getDefaultInstance().apply(word, keyword);
            if(levenshteinDistance == 0) {
                return true;
            }else if(levenshteinDistance == 1) {
                similarWords.add(word);
            }
            return false;
        }).count();
            //handle filtering for counting and collection of similar word in
            //one lambda in order to avoid extranous iterations
        return new WordCountResponse(keyword,
                frequency,
                similarWords);
    }

    /**
     * Allows to store words which are then retrievable with
     * {@link #wordCount(java.lang.String) }.This is used because there's no
     * source for real notebook data specified in the task.
     * @param username the name of the user to provide for
     * @param words the words to provide
     */
    @PutMapping(value = PROVIDE_WORDS_METHOD+"/{"+USERNAME_PARAM+"}", consumes = {"application/json"})
    public void provideWords(@PathVariable String username,
            @RequestBody List<String> words) {
        wordSupplier.provideWords(username,
                words);
    }
}