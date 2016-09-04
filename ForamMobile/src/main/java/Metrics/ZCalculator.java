package Metrics;

import Model.Foraminifera;

public class ZCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        return 0;
    }

    @Override
    public String GetName() {
        return "Z size";
    }
}
