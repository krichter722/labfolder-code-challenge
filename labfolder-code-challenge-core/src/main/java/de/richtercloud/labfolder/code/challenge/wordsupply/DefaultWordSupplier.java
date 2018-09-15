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
@SuppressWarnings("JavadocMethod")
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

    // CHECKSTYLE:OFF
    /**
     * Provide words for the next calls of {@link #supply() } until this method
     * is called with a different argument.
     *
     * @param username the name of the user for whom to provide the words
     * @param words the provided words (mustn't be null or contain the empty
     *     string)
     * @throws IllegalArgumentException if {@code words} is {@code null}
     * @throws EmptyStringSuppliedException if {@code words} contains the empty
     *     string
     */
    @Override
    @SuppressWarnings({"PMD.JavadocMethod", "JavadocMethod"})
        //the very annoying and long outstanding bug ?? in PMD and
        //https://github.com/checkstyle/checkstyle/issues/5088 in checkstyle
        //-> checkstyle seems suffer from this issue before scanning the
        //comments (method including Javadoc surrounded by off-on comment) and
        //and the annotations both on method and class level -> turn off static
        //analysis because it's not worth figuring this out right now
    public void provideWords(String username,
            List<String> words) throws EmptyStringSuppliedException {
        Preconditions.checkArgument(words != null, "words mustn't be null");
        if(words.contains("")) {
            throw new EmptyStringSuppliedException("words mustn't contain the empty string (provision of empty string as word is conter-intuitive)");
        }
        List<String> userWords = providedWords.get(username);
        if(userWords == null) {
            userWords = new LinkedList<>(words);
            providedWords.put(username, userWords);
        }else {
            userWords.clear();
            userWords.addAll(words);
        }
    }
    // CHECKSTYLE:ON
}
