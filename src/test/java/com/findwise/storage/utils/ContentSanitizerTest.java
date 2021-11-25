package com.findwise.storage.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentSanitizerTest {

    @Test
    void testSanitizationOne() {
        String documentOne = " The (brown) fox jumped, over the brown dog! ";
        String expectedDocumentOne = "the brown fox jumped over the brown dog";

        var result = new ContentSanitizer(documentOne).sanitize();

        assertEquals(expectedDocumentOne, result);
    }

    @Test
    void testSanitizationTwo() {
        String documentTwo = "the lazy Brown dog; sat in [the] corner.";
        String expectedDocumentTwo = "the lazy brown dog sat in the corner";

        var result = new ContentSanitizer(documentTwo).sanitize();

        assertEquals(expectedDocumentTwo, result);
    }

    @Test
    void testSanitizationThree() {
        String documentThree = "The red fox: \"Bit\" the lazy dog";
        String expectedDocumentThree = "the red fox bit the lazy dog";

        var result = new ContentSanitizer(documentThree).sanitize();

        assertEquals(expectedDocumentThree, result);
    }
}