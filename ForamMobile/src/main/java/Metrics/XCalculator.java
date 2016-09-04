package Metrics;

import Model.Foraminifera;

public class XCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        return 0;
    }

    @Override
    public String GetName() {
        return "X size";
    }
}
