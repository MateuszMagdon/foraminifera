package Metrics;

import Model.Foraminifera;

public class YCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        return 0;
    }

    @Override
    public String GetName() {
        return "Y size";
    }
}
