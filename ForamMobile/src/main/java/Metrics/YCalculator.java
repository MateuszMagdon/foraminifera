package Metrics;

import Helpers.Metrics;
import Model.Foraminifera;
import Model.Shell;

public class YCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {

        Shell min = null;
        Shell max = null;

        for (Shell shell : foraminifera.getShells()){
            double shellY = shell.getCenter().getY();
            if (min == null || shellY < min.getCenter().getY()){
                min = shell;
            }
            if (max == null || shellY > max.getCenter().getY()){
                max = shell;
            }
        };

        double maxY = max.getCenter().getY() + max.getRadius() + max.getThickness();
        double minY = min.getCenter().getY() - min.getRadius() - min.getThickness();

        Metrics metrics = foraminifera.getMetrics();
        metrics.setMaxY(maxY);
        metrics.setMinY(minY);

        double result = maxY - minY;
        metrics.setSizeY(result);

        return result;
    }

    @Override
    public String GetName() {
        return "Y size";
    }
}
