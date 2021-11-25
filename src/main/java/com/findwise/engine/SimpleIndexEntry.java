package com.findwise.engine;

import com.findwise.IndexEntry;

import java.util.Objects;

public class SimpleIndexEntry implements IndexEntry {
    private String id;
    private double score;

    public SimpleIndexEntry(String id, double score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleIndexEntry that = (SimpleIndexEntry) o;
        return Double.compare(that.score, score) == 0 && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }

    @Override
    public String toString() {
        return "SimpleIndexEntry{" + "id='" + id + '\'' + ", score=" + score + '}';
    }
}
