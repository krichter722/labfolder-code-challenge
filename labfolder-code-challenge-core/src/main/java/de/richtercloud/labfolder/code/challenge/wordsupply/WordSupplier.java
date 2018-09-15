package de.richtercloud.labfolder.code.challenge.wordsupply;

import java.util.List;
import java.util.stream.Stream;

/**
 * Retrieves the content of notebooks and splits it into words on whitespace
 * (tokenization). The result is provided as stream in order to avoid building a
 * huge collection and allow lazy evaluation.
 *
 * @author richter
 */
public interface WordSupplier {

    /**
     * Supplies notebook content as described in the class documentation.
     * @param username the name of the user whose data is returned
     * @return a stream of words
     */
    Stream<String> supply(String username);

    /**
     * Since there's no requirement for and information about the notebook data
     * format, you can provide the data through this method.
     *
     * Calling this method causes {@link supply} to return the provided words
     * overwriting data from any previous calls. The list is copied in order to
     * avoid modification after calling this method.
     *
     * @param username the name of the user for whom to provide the words
     * @param words the provided words
     * @throws EmptyStringSuppliedException if {@code words} contains the empty
     *     string which is considered counter-intuitive to be expected in the
     *     list of provided words
     */
    void provideWords(String username,
            List<String> words) throws EmptyStringSuppliedException;
}
