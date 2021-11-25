package com.findwise.engine;

import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleSearchEngineTest {

    @Test
    void testExamplesProvidedInAssignmentWithBrown() {
        String documentOne = "the brown fox jumped over the brown dog";
        String documentTwo = "the lazy brown dog sat in the corner";
        String documentThree = "the red fox bit the lazy dog";
        List<IndexEntry> expectedResult = new ArrayList<>();
        var idf = Math.log10(3.0/2.0);
        expectedResult.add(new SimpleIndexEntry("Document 1", (2.0/8.0)*idf));
        expectedResult.add(new SimpleIndexEntry("Document 2", (1.0/8.0)*idf));
        expectedResult.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());

        SearchEngine engine = new SimpleSearchEngine();
        engine.indexDocument("Document 1", documentOne);
        engine.indexDocument("Document 2", documentTwo);
        engine.indexDocument("Document 3", documentThree);

        List<IndexEntry> result = engine.search("brown");

        assertEquals(expectedResult, result);
    }

    @Test
    void testExamplesProvidedInAssignmentWithFox() {
        String documentOne = "the brown fox jumped over the brown dog";
        String documentTwo = "the lazy brown dog sat in the corner";
        String documentThree = "the red fox bit the lazy dog";
        List<IndexEntry> expectedResult = new ArrayList<>();
        var idf = Math.log10(3.0/2.0);
        expectedResult.add(new SimpleIndexEntry("Document 1", (1.0/8.0)*idf));
        expectedResult.add(new SimpleIndexEntry("Document 3", (1.0/7.0)*idf));
        expectedResult.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());

        SearchEngine engine = new SimpleSearchEngine();
        engine.indexDocument("Document 1", documentOne);
        engine.indexDocument("Document 2", documentTwo);
        engine.indexDocument("Document 3", documentThree);

        List<IndexEntry> result = engine.search("fox");

        assertEquals(expectedResult, result);
    }

    @Test
    void testUnsanitizedInputWithBrown() {
        String documentOne = "The (brown) fox jumped, over the brown dog!";
        String documentTwo = "the lazy Brown dog; sat in [the] corner.";
        String documentThree = "The red fox: \"Bit\" the lazy dog";
        List<IndexEntry> expectedResult = new ArrayList<>();
        var idf = Math.log10(3.0/2.0);
        expectedResult.add(new SimpleIndexEntry("Document 1", (2.0/8.0)*idf));
        expectedResult.add(new SimpleIndexEntry("Document 2", (1.0/8.0)*idf));
        expectedResult.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());

        SearchEngine engine = new SimpleSearchEngine();
        engine.indexDocument("Document 1", documentOne);
        engine.indexDocument("Document 2", documentTwo);
        engine.indexDocument("Document 3", documentThree);

        List<IndexEntry> result = engine.search("brown");

        assertEquals(expectedResult, result);
    }

    @Test
    void testUnsanitizedInputWithFox() {
        String documentOne = "The (brown) fox jumped, over the brown dog!";
        String documentTwo = "the lazy Brown dog; sat in [the] corner.";
        String documentThree = "The red fox: \"Bit\" the lazy dog";
        List<IndexEntry> expectedResult = new ArrayList<>();
        var idf = Math.log10(3.0/2.0);
        expectedResult.add(new SimpleIndexEntry("Document 1", (1.0/8.0)*idf));
        expectedResult.add(new SimpleIndexEntry("Document 3", (1.0/7.0)*idf));
        expectedResult.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());

        SearchEngine engine = new SimpleSearchEngine();
        engine.indexDocument("Document 1", documentOne);
        engine.indexDocument("Document 2", documentTwo);
        engine.indexDocument("Document 3", documentThree);

        List<IndexEntry> result = engine.search("fox");

        assertEquals(expectedResult, result);
    }
}