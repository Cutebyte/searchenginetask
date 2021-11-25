package com.findwise.storage.utils;

public class ContentSanitizer {
    private static final String REPLACE_REGEX = "[.,();\\[\\]:'\"`!?]";
    private final String content;

    public ContentSanitizer(String content) {
        this.content = content;
    }

    public String sanitize() {
        String sanitized = content.strip().toLowerCase();
        sanitized = sanitized.replaceAll(REPLACE_REGEX, "");
        return sanitized;
    }
}
