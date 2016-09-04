package Metrics;

import Model.Foraminifera;

public class MaterialCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        return 1.23456;
    }

    @Override
    public String GetName() {
        return "Material volume";
    }
}
