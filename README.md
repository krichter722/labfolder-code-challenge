[![Build Status](https://www.travis-ci.org/krichter722/labfolder-code-challenge.svg?branch=master)](https://www.travis-ci.org/krichter722/labfolder-code-challenge)

# Labfolder code challenge
This code challenge fulfills the task to implement the user story

    As a researcher, I would like to view the frequency of a given word and any similar words in
my notebook entry.

    Concepts
    A word is considered “similar” to another word if the Levenshtein distance is not more than 1.

## Definition of Done
I consider the task done after implementing the REST API in Spring and stick to the simplest approach which ignores the fact that the collection of all words of all notebook entries probably doesn't fit into an affordable amount of memory and that the word counting should thus be processed in a map-reduce manner an multiple cluster nodes, e.g. with Apache Storm. I'm ignoring as well that it'd be nice if REST requests would be processed by a microservice in order to make it easier to scale and change the application. Clients send requests won't be authenticated and authorized to request any data which should be avoided at all cost in the real world.

## Further nice-to-have features
The REST API should be documented in HTML automatically. Frameworks are available for that.

## Implementation notes
Without information how data is structured it's sufficient to define a minimal `WordSupplier` interface. It supplies a `java.util.Stream` which can be created from a local collection of fetch data remotely through (socket/network) streams (ideally using a buffer) in `supply(String)`. It's lazy nature avoids building collections locally. It contains another method `provideWords` which allows to provide data and thus the API to do something predictable rather than just return random data (since we don't have any real data or data format information).

A `WordSupplier` is then implemented using separate storages for different users with a `Map`. The storage of words doesn't enforce a `java.util.List` implementation since they have their respective trade-offs for different data (number and length of words) and a choice only makes sense based on statistics or extended profiling. If large amounts of data are handled, a decision regarding (disk) caching and/or stream-based processing in a map-reduce manner has to be made.

The REST API is implemented following the suggestions in the task description adding a username path parameter because sharing data between researchers (after authorization of the owner(s)) is an easy to implement additional feature.

The implementation is covered by unit and integration tests. Due to lack of Spring knowledge the tests are skipped in `mvn install` and need to be executed manually in the `labfolder-code-challenge-core` directory using

    JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 /home/richter/netbeans-9.0-incubating/java/maven/bin/mvn -Dtest=de.richtercloud.labfolder.code.challenge.it.LabfolderCodeChallengeIT surefire:test

The tests are run on Travis CI using Docker in order to increase flexibility for the choice of underlying OS and JVM.

## Missing features and best practices
A HTML documentation for the REST API should be provided automatically with a framework parsing 

## Project structure
The integration tests were planned to be place in a separate integration test project to keep integration test dependencies out-of-the-way. However, the classes are not available there because the Spring jar doesn't contain them in the expected location. This requires further know-how in Spring.
