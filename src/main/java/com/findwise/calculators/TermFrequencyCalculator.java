package com.findwise.calculators;

public class TermFrequencyCalculator extends BaseCalculator {
    public TermFrequencyCalculator(int termCount, int matchedTermCount) {
        super(termCount, matchedTermCount);
    }

    @Override
    public double calculate() {
        return matchedValuesCount/allValuesCount;
    }
}
