package de.richtercloud.labfolder.code.challenge.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import de.richtercloud.labfolder.code.challenge.Application;
import de.richtercloud.labfolder.code.challenge.rest.WordCountResponse;
import de.richtercloud.labfolder.code.challenge.rest.WordCountRestController;
import de.richtercloud.labfolder.code.challenge.wordsupply.EmptyStringSuppliedException;
import de.richtercloud.labfolder.code.challenge.wordsupply.WordSupplier;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.when;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import org.apache.http.HttpStatus;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert",
    "PMD.AvoidDuplicateLiterals"
})
public class LabfolderCodeChallengeIT {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Value("${local.server.port}")
    private int serverPort;
    @Autowired
    private WordSupplier wordSupplier;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testWordCountMissingKeyword() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>());

        when()
                .get(String.format("%s%s/username",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD
                ))
                .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testWordCountMissingUsername() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>());

        when()
                .get(String.format("%s%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD
                ))
                .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testWordCountEmptyKeyword() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>());

        String keyword = "";
        when()
                .get(String.format("%s%s/username?%s=%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD,
                        WordCountRestController.KEYWORD_PARAM,
                        keyword //keyword
                ))
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .and().contentType("application/json")
                        .and().body(new WordCountResponseDeserializationMatcher(new WordCountResponse(keyword, 0, new LinkedList<>())));
    }

    @Test
    public void testWordCountNonEmptyKeyword() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>());

        String keyword = "x";
        when()
                .get(String.format("%s%s/username?%s=%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD,
                        WordCountRestController.KEYWORD_PARAM,
                        keyword //keyword
                ))
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .and().contentType("application/json")
                        .and().body(new WordCountResponseDeserializationMatcher(new WordCountResponse(keyword, 0, new LinkedList<>())));
    }

    @Test
    public void testWordCountNoMatch() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>(Arrays.asList("123")));

        String keyword = "x";
        when()
                .get(String.format("%s%s/username?%s=%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD,
                        WordCountRestController.KEYWORD_PARAM,
                        keyword //keyword
                ))
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .and().contentType("application/json")
                        .and().body(new WordCountResponseDeserializationMatcher(new WordCountResponse(keyword, 0, new LinkedList<>())));
    }

    @Test
    public void testWordCountNoMatchSimilar() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>(Arrays.asList("y", "123")));

        String keyword = "x";
        when()
                .get(String.format("%s%s/username?%s=%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD,
                        WordCountRestController.KEYWORD_PARAM,
                        keyword //keyword
                ))
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .and().contentType("application/json")
                        .and().body(new WordCountResponseDeserializationMatcher(new WordCountResponse(keyword, 0, new LinkedList<>(Arrays.asList("y")))));
    }

    @Test
    public void testWordCountMatchSimilar() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username",
                new LinkedList<>(Arrays.asList("x", "y", "123")));

        String keyword = "x";
        when()
                .get(String.format("%s%s/username?%s=%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD,
                        WordCountRestController.KEYWORD_PARAM,
                        keyword //keyword
                ))
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .and().contentType("application/json")
                        .and().body(new WordCountResponseDeserializationMatcher(new WordCountResponse(keyword, 1, new LinkedList<>(Arrays.asList("y")))));
    }

    @Test
    public void testWordCountDifferentUser() throws IOException,
            EmptyStringSuppliedException {
        wordSupplier.provideWords("username1",
                new LinkedList<>(Arrays.asList("x", "y", "123")));

        String keyword = "x";
        when()
                .get(String.format("%s%s/username?%s=%s",
                        WordCountRestController.PATH,
                        WordCountRestController.WORD_COUNT_METHOD,
                        WordCountRestController.KEYWORD_PARAM,
                        keyword //keyword
                ))
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .and().contentType("application/json")
                        .and().body(new WordCountResponseDeserializationMatcher(new WordCountResponse(keyword, 0, new LinkedList<>())));
    }

    @Test
    public void testProvideWordsGetNotAllowed() throws IOException {
        RequestSpecification request = RestAssured.with().basePath(String.format("%s%s/username",
                WordCountRestController.PATH,
                WordCountRestController.PROVIDE_WORDS_METHOD));
        request.body("[\"abc\", \"\"]")
                .get()
                .then()
                        .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void testProvideWordsPostNotAllowed() throws IOException {
        RequestSpecification request = RestAssured.with().basePath(String.format("%s%s/username",
                WordCountRestController.PATH,
                WordCountRestController.PROVIDE_WORDS_METHOD));
        request.body("[\"abc\", \"\"]")
                .post()
                .then()
                        .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void testProvideWordsDeleteNotAllowed() throws IOException {
        RequestSpecification request = RestAssured.with().basePath(String.format("%s%s/username",
                WordCountRestController.PATH,
                WordCountRestController.PROVIDE_WORDS_METHOD));
        request.body("[\"abc\", \"\"]")
                .delete()
                .then()
                        .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void testProvideWordsEmptyString() throws IOException {
        RequestSpecification request = RestAssured.with().basePath(String.format("%s%s/username",
                WordCountRestController.PATH,
                WordCountRestController.PROVIDE_WORDS_METHOD));
        request.contentType("application/json")
                .body("[\"abc\", \"\"]")
                .put()
                .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .body(Matchers.equalTo("words mustn't contain the empty string (provision of empty string as word is conter-intuitive)"));
    }

    private class WordCountResponseDeserializationMatcher extends BaseMatcher<String> {
        private final WordCountResponse match;

        public WordCountResponseDeserializationMatcher(WordCountResponse match) {
            super();
            Preconditions.checkArgument(match != null, "match mustn't be null");
            this.match = match;
        }

        @Override
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        public boolean matches(Object item) {
            Object deserializedItem;
            try {
                deserializedItem = OBJECT_MAPPER.readValue((String)item, WordCountResponse.class);
            } catch (IOException ex) {
                throw new IllegalArgumentException(ex);
                    //only used to make the text fail, so no need of a fancy
                    //separate exception
            }
            return match.equals(deserializedItem);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(match.toString());
                //what is displayed for "expected:" in the expected vs. actual
                //mismatch description
        }
    }
}
