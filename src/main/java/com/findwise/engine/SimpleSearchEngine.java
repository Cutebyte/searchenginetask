package com.findwise.engine;

import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.calculators.InverseDocumentFrequencyCalculator;
import com.findwise.calculators.TermFrequencyCalculator;
import com.findwise.storage.Document;
import com.findwise.storage.DocumentStorage;
import com.findwise.storage.exceptions.MissingID;
import com.findwise.storage.exceptions.MissingTerms;
import com.findwise.storage.utils.ContentSanitizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SimpleSearchEngine implements SearchEngine {
    private final Logger logger = LogManager.getLogger(getClass());
    private final DocumentStorage storage;

    public SimpleSearchEngine() {
        this.storage = new DocumentStorage();
    }

    @Override
    public void indexDocument(String id, String content) {
        logger.info("Indexing document {} with content \"{}\"", id, content);
        Document.DocumentBuilder builder = new Document.DocumentBuilder(id);
        Arrays.stream(new ContentSanitizer(content).sanitize().split(" ")).forEach(builder::appendTerm);
        try {
            storage.storeDocument(builder.build());
        } catch (MissingID | MissingTerms exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    @Override
    public List<IndexEntry> search(String term) {
        logger.info("Searching for {}...", term);
        List<IndexEntry> results = new ArrayList<>();

        Set<Document> matchingDocuments = storage.findDocuments(term);
        double idf = new InverseDocumentFrequencyCalculator(storage.getDocumentCount(), matchingDocuments.size())
                .calculate();

        for (var document : matchingDocuments) {
            logger.info("Matched document: {}", document.getId());
            var sum = document.getTerms().values().stream().reduce(0, Integer::sum);
            double tf = new TermFrequencyCalculator(sum, document.getTerms().get(term)) .calculate();
            results.add(new SimpleIndexEntry(document.getId(), tf * idf));
        }
        results.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());
        logger.info("Matched {} entries!", results.size());
        return results;
    }

}