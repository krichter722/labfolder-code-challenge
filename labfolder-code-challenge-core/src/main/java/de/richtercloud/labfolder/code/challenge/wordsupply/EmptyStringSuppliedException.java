package de.richtercloud.labfolder.code.challenge.wordsupply;

/**
 * Indicates that the empty string has been provided in
 * {@link DefaultWordSupplier#provideWords(java.lang.String, java.util.List) }.
 *
 * @author richter
 */
public class EmptyStringSuppliedException extends Exception {

    public EmptyStringSuppliedException(String message) {
        super(message);
    }

    public EmptyStringSuppliedException(String message,
            Throwable cause) {
        super(message,
                cause);
    }

    public EmptyStringSuppliedException(Throwable cause) {
        super(cause);
    }
}
