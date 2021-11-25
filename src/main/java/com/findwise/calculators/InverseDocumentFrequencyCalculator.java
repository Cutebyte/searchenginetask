package com.findwise.calculators;

public class InverseDocumentFrequencyCalculator extends BaseCalculator {
    public InverseDocumentFrequencyCalculator(int documentsCount, int matchedDocumentsCount) {
        super(documentsCount, matchedDocumentsCount);
    }

    @Override
    public double calculate() {
        return Math.log10(allValuesCount/matchedValuesCount);
    }
}
