package com.findwise.storage.exceptions;

public class MissingTerms extends Exception {
    public MissingTerms() {
        super();
    }

    @Override
    public String getMessage() {
        return "Document is missing terms!";
    }
}
