package de.richtercloud.labfolder.code.challenge.rest;

import java.util.List;

/**
 * Allows to works with {@code @RequestBody} in
 * {@link WordCountRestController#provideWords(java.util.List) }.
 *
 * @author richter
 */
public class ProvideWordsRequest {
    private String username;
    private List<String> words;

    public ProvideWordsRequest() {
    }

    public ProvideWordsRequest(String username, List<String> words) {
        this.username = username;
        this.words = words;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
