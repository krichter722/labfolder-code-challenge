package de.richtercloud.labfolder.code.challenge.wordsupply;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author richter
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class DefaultWordSupplierTest {

    @Test
    public void testSupplyNoProvision() {
        DefaultWordSupplier instance = new DefaultWordSupplier();
        Stream<String> result = instance.supply("username");
        assertEquals(new LinkedList<>(), new LinkedList<>(Arrays.asList(result.toArray(String[]::new))));
    }

    @Test
    public void testSupplyProvision() throws EmptyStringSuppliedException {
        DefaultWordSupplier instance = new DefaultWordSupplier();
        List<String> provision = new LinkedList<>(Arrays.asList("a", "abc"));
        instance.provideWords("username",
                provision);
        Stream<String> result = instance.supply("username");
        assertEquals(provision, new LinkedList<>(Arrays.asList(result.toArray(String[]::new))));
    }

    @Test
    public void testSupplyProvisionEmpty() throws EmptyStringSuppliedException {
        DefaultWordSupplier instance = new DefaultWordSupplier();
        List<String> provision = new LinkedList<>();
        instance.provideWords("username",
                provision);
        Stream<String> result = instance.supply("username");
        assertEquals(provision, new LinkedList<>(Arrays.asList(result.toArray(String[]::new))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProvideWordsNull() throws EmptyStringSuppliedException {
        List<String> words = null;
        DefaultWordSupplier instance = new DefaultWordSupplier();
        instance.provideWords("username",
                words);
    }

    /**
     * Tests that provision of the empty string as word causes
     * {@code IllegalArgumentException} since we don't expect a proper word
     * provider implementation to provide it.
     * @throws EmptyStringSuppliedException expected
     */
    @Test(expected = EmptyStringSuppliedException.class)
    public void testProvideWordsEmpty() throws EmptyStringSuppliedException {
        List<String> words = new LinkedList<>(Arrays.asList("", "xyz"));
        DefaultWordSupplier instance = new DefaultWordSupplier();
        instance.provideWords("username",
                words);
    }
}
