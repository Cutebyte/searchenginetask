package com.findwise.storage.exceptions;

public class MissingID extends Exception {
    public MissingID() {
        super();
    }

    @Override
    public String getMessage() {
        return "Document is missing ID!";
    }
}
