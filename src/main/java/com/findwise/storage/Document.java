package com.findwise.storage;

import com.findwise.storage.exceptions.MissingID;
import com.findwise.storage.exceptions.MissingTerms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Document {
    private final String id;
    private final Map<String, Integer> terms;

    private Document(DocumentBuilder builder) {
        this.id = builder.id;
        this.terms = Collections.unmodifiableMap(builder.terms);
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getTerms() {
        return terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) && Objects.equals(terms, document.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, terms);
    }

    @Override
    public String toString() {
        return "Document{" + "id='" + id + '\'' + ", terms=" + terms + '}';
    }

    public static class DocumentBuilder {
        private final String id;
        private final Map<String, Integer> terms;

        public DocumentBuilder(String id) {
            this.id = id;
            terms = new HashMap<>();
        }

        public DocumentBuilder appendTerm(String term) {
            if (terms.containsKey(term)) {
                terms.put(term, terms.get(term) + 1);
            } else {
                terms.put(term, 1);
            }
            return this;
        }

        private void validate(Document document) throws MissingID, MissingTerms {
            if (null == document.id || "".equals(document.id)) {
                throw new MissingID();
            }
            if (terms.isEmpty()) {
                throw new MissingTerms();
            }
        }

        public Document build() throws MissingTerms, MissingID {
            Document document = new Document(this);
            validate(document);
            return document;
        }
    }
}
