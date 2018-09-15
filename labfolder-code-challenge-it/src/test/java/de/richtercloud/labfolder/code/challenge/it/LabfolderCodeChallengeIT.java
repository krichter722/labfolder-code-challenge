package de.richtercloud.labfolder.code.challenge.it;

//import de.richtercloud.labfolder.code.challenge.Application;
//import de.richtercloud.labfolder.code.challenge.rest.WordCountRestController;
//import de.richtercloud.labfolder.code.challenge.wordsupply.WordSupplier;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class LabfolderCodeChallengeIT {
//    @Value("${local.server.port}")
//    private int serverPort = 8080;
//    @Autowired
//    private WordSupplier wordSupplier;

    @Before
    public void setUp() {
//        RestAssured.port = serverPort;
    }

    @Test
    public void testWordCount() throws MalformedURLException, IOException {
//        wordSupplier.provideWords(new LinkedList<>());

//        when()
//                .get(String.format("%s%s",
//                        "/wordcount",
//                        "/wordcount" //WordCountRestController.WORD_COUNT_METHOD
//                ))
//                .then()
//                        .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
