package com.findwise;

import com.findwise.engine.SimpleSearchEngine;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class);
    static final String[] TEST_DOCUMENTS = {"/document1.txt", "/document2.txt", "/document3.txt"};

    public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
        CommandLineParser parser = new DefaultParser();
        Options options = prepareOptions();
        CommandLine commandLine = parser.parse(options, args);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("simplesearchengine", options);

        handleOptions(commandLine);
    }

    private static void handleOptions(CommandLine commandLine) throws IOException, URISyntaxException {
        SearchEngine engine = new SimpleSearchEngine();
        if (commandLine.hasOption('t')) {
            logger.info("Loading test documents...");
            loadTestDocuments(engine);
        } else if (commandLine.hasOption('d')) {
            logger.info("Loading specified documents...");
            loadDocuments(engine, commandLine.getOptionValues('d'));
        } else {
            System.out.println("Provide path to at least one document!");
            System.exit(1);
        }
        if (commandLine.hasOption('q')) {
            search(engine, commandLine.getOptionValue('q'));
        } else {
            System.out.println("Provide query for the search engine!");
            System.exit(1);
        }
    }

    private static void loadDocuments(SearchEngine engine, String[] documents) throws IOException {
        for (var document : documents) {
            Path path = Paths.get(document);
            String content = Files.readString(path, StandardCharsets.UTF_8);
            engine.indexDocument(path.getFileName().toString(), content);
        }
    }

    private static void loadTestDocuments(SearchEngine engine) throws IOException {
        for (var document : TEST_DOCUMENTS) {
            try (InputStream inputStream = Main.class.getResourceAsStream(document)) {
                String content = new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8);
                engine.indexDocument(document, content);
            }
        }
    }

    private static void search(SearchEngine engine, String term) {
        logger.info("Starting SearchEngine...");
        List<IndexEntry> results = engine.search(term);
        results.forEach(result -> logger.info("Document: {} with score of {}", result.getId(), result.getScore()));
    }

    private static Options prepareOptions() {
        Options options = new Options();
        options.addOption("d", "document", true, "Document to be indexed");
        options.addOption("t", "test", false, "Loads test documents");
        options.addOption("q", "query", true, "Term that will be searched");
        return options;
    }
}
