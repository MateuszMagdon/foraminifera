package Metrics;

import Model.Foraminifera;

public class VolumeCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        return 123123.123;
    }

    @Override
    public String GetName() {
        return "Volume";
    }
}
