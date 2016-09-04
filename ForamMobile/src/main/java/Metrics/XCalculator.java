package Metrics;

import Helpers.Metrics;
import Model.Foraminifera;
import Model.Shell;

public class XCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        Shell min = null;
        Shell max = null;

        for (Shell shell : foraminifera.getShells()){
            double shellX = shell.getCenter().getX();
            if (min == null || shellX < min.getCenter().getX()){
                min = shell;
            }
            if (max == null || shellX > max.getCenter().getX()){
                max = shell;
            }
        };

        double maxX = max.getCenter().getX() + max.getRadius() + max.getThickness();
        double minX = min.getCenter().getX() - min.getRadius() - min.getThickness();

        Metrics metrics = foraminifera.getMetrics();
        metrics.setMaxX(maxX);
        metrics.setMinX(minX);

        double result = maxX - minX;
        metrics.setSizeX(result);

        return result;
    }

    @Override
    public String GetName() {
        return "X size";
    }
}
