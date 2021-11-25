package com.findwise.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DocumentStorage {
    private final Map<String, Set<Document>> invertedIndex;
    private final Set<Document> allDocuments;

    public DocumentStorage() {
        invertedIndex = new HashMap<>();
        allDocuments = new HashSet<>();
    }

    public void storeDocument(Document document) {
        allDocuments.add(document);
        document.getTerms().forEach((term, uses) -> {
            if (!invertedIndex.containsKey(term)) {
                invertedIndex.put(term, new HashSet<>());
            }
            invertedIndex.get(term).add(document);
        });
    }

    public Set<Document> findDocuments(String term) {
        return invertedIndex.get(term);
    }

    public int getDocumentCount() {
        return allDocuments.size();
    }
}
