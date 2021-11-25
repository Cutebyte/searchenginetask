package com.findwise.storage;

import com.findwise.storage.exceptions.MissingID;
import com.findwise.storage.exceptions.MissingTerms;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DocumentBuilderTest {
    @Test void testBuilderWithEmptyIDAndNoTerms() {
        Document.DocumentBuilder builder = new Document.DocumentBuilder("");

        assertThrows(MissingID.class, builder::build);
    }

    @Test
    void testBuilderWithEmptyIDAndOneTerm() {
        Document.DocumentBuilder builder = new Document.DocumentBuilder("").appendTerm("someTerm");

        assertThrows(MissingID.class, builder::build);
    }

    @Test
    void testBuilderWithEmptyIDAndMultipleTerms() {
        Document.DocumentBuilder builder = new Document.DocumentBuilder("").appendTerm("someTerm")
                .appendTerm("someOtherTerm").appendTerm("evenMoreTerms").appendTerm("tooManyTerms");

        assertThrows(MissingID.class, builder::build);
    }

    @Test
    void testBuilderWithIDAndNoTerms() {
        Document.DocumentBuilder builder = new Document.DocumentBuilder("something");

        assertThrows(MissingTerms.class, builder::build);
    }

    @Test
    void testBuilderWithValidIdAndUniqueTerms() throws MissingTerms, MissingID {
        String validId = "valid-id";
        Map<String, Integer> uniqueTerms = new HashMap<>();
        uniqueTerms.put("valid-term-1", 1);
        uniqueTerms.put("valid-term-2", 1);
        uniqueTerms.put("valid-term-3", 1);

        Document document = new Document.DocumentBuilder(validId)
                .appendTerm("valid-term-1").appendTerm("valid-term-2").appendTerm("valid-term-3").build();

        assertEquals(validId, document.getId());
        assertEquals(uniqueTerms, document.getTerms());
    }

    @Test
    void testBuilderWithValidIdAndDuplicatedTerms() throws MissingTerms, MissingID {
        String validId = "valid-id";
        Map<String, Integer> uniqueTerms = new HashMap<>();
        uniqueTerms.put("valid-term-1", 1);
        uniqueTerms.put("valid-term-2", 2);
        uniqueTerms.put("valid-term-3", 2);

        Document document = new Document.DocumentBuilder(validId)
                .appendTerm("valid-term-1").appendTerm("valid-term-2").appendTerm("valid-term-2")
                .appendTerm("valid-term-3").appendTerm("valid-term-3").build();

        assertEquals(validId, document.getId());
        assertEquals(uniqueTerms, document.getTerms());
    }
}