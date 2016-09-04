package Metrics;

import Model.Foraminifera;

public interface IMetricCalculator {
    double CalculateMetric(Foraminifera foraminifera);
    String GetName();
}
