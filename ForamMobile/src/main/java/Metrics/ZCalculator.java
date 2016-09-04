package Metrics;

import Model.Foraminifera;
import Model.Shell;

public class ZCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        Shell min = null;
        Shell max = null;

        for (Shell shell : foraminifera.getShells()){
            double shellZ = shell.getCenter().getZ();
            if (min == null || shellZ < min.getCenter().getZ()){
                min = shell;
            }
            if (max == null || shellZ > max.getCenter().getZ()){
                max = shell;
            }
        };

        double maxZ = max.getCenter().getZ() + max.getRadius() + max.getThickness();
        double minZ = min.getCenter().getZ() - min.getRadius() - min.getThickness();
        return maxZ - minZ;
    }

    @Override
    public String GetName() {
        return "Z size";
    }
}
