package de.richtercloud.labfolder.code.challenge.wordsupply;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

/**
 *
 * @author richter
 */
@Service
public class DefaultWordSupplier implements WordSupplier {
    /**
     * The provided words for each user.
     *
     * Initially an empty list in order to avoid
     * introduction of an exception when calling {@link #supply() } before
     * {@link #provideWords(java.util.List) }.
     *
     * Don't worry too much about the List implementation: linked list provides
     * more flexibility because the JVM doesn't need to move the array in memory
     * whereas the array list is fast in adding a large list of elements. If any
     * of those problems ever arise, one should switch to a Javolution or
     * similar implementation.
     */
    private final Map<String, List<String>> providedWords = new HashMap<>();

    @Override
    public Stream<String> supply(String username) {
        List<String> userWords = providedWords.get(username);
        if(userWords == null) {
            userWords = new LinkedList<>();
            providedWords.put(username, userWords);
        }
        return userWords.stream();
    }

    /**
     * Provide words for the next calls of {@link #supply() } until this method
     * is called with a different argument.
     *
     * @param username the name of the user for whom to provide the words
     * @param words the provided words (mustn't be null or contain the empty
     *     string)
     * @throws IllegalArgumentException if {@code words} is {@code null} or
     *     contains the empty string
     */
    @Override
    public void provideWords(String username,
            List<String> words) throws IllegalArgumentException {
        Preconditions.checkArgument(words != null, "words mustn't be null");
        Preconditions.checkArgument(!words.contains(""), "words mustn't contain the empty string (provision of empty is conter-intuitive)");
        List<String> userWords = providedWords.get(username);
        if(userWords == null) {
            userWords = new LinkedList<>(words);
            providedWords.put(username, userWords);
        }else {
            userWords.clear();
            userWords.addAll(words);
        }
    }
}
