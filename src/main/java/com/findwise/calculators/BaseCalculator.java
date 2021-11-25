package com.findwise.calculators;

public abstract class BaseCalculator implements Calculator {
    protected final double allValuesCount;
    protected final double matchedValuesCount;

    protected BaseCalculator(int allValuesCount, int matchedValuesCount) {
        this.allValuesCount = allValuesCount;
        this.matchedValuesCount = matchedValuesCount;
    }
}
