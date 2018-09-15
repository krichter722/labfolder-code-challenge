package de.richtercloud.labfolder.code.challenge.rest;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author richter
 */
public class WordCountResponse {
    /**
     * The keyword submitted in the request.
     */
    private String keyword;
    /**
     * The count of occurance of the keyword.
     */
    /*
    internal implementation notes:
    - This uses a long because 1TB of data containing UTF-8 words with an
    average length of 10 characters already exceeds int's range.
    */
    private long frequency;
    /**
     * A list of words similar to the keyword. A similar word is one that has a
     * Levenshtein distance of 1.
     */
    private List<String> similarWords;

    /**
     * Support creation of instances through Jackson.
     */
    public WordCountResponse() {
    }

    public WordCountResponse(String keyword, long frequency, List<String> similarWords) {
        this.keyword = keyword;
        this.frequency = frequency;
        this.similarWords = similarWords;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public List<String> getSimilarWords() {
        return similarWords;
    }

    public void setSimilarWords(List<String> similarWords) {
        this.similarWords = similarWords;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.keyword);
        hash = 67 * hash + (int) (this.frequency ^ (this.frequency >>> 32));
        hash = 67 * hash + Objects.hashCode(this.similarWords);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WordCountResponse other = (WordCountResponse) obj;
        if (this.frequency != other.frequency) {
            return false;
        }
        if (!Objects.equals(this.keyword, other.keyword)) {
            return false;
        }
        return Objects.equals(this.similarWords, other.similarWords);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
