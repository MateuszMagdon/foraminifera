package Metrics;

import java.util.LinkedList;

import Helpers.Metrics;
import Helpers.Point;
import Model.Foraminifera;
import Model.Shell;

public abstract class MonteCarloVolumeCalculator implements IMetricCalculator {

    protected int triesCount = 10000;

    public double CalculateMetric(Foraminifera foraminifera) {
        int insideCount = 0;

        Metrics metrics = foraminifera.getMetrics();
        RandomPointFactory randomPointFactory = new RandomPointFactory(
                metrics.getMinX(), metrics.getMaxX(),
                metrics.getMinY(), metrics.getMaxY(),
                metrics.getMinZ(), metrics.getMaxZ());

        double cubeVolume = metrics.getSizeX() * metrics.getSizeY() * metrics.getSizeZ();

        LinkedList<Shell> shells = foraminifera.getShells();

        for (int i = 0; i < triesCount; i++){
            Point p = randomPointFactory.GenerateRandomPoint();

            if (isInsideShells(p, shells)){
                insideCount++;
            }
        }

        return ((double) insideCount / triesCount) * cubeVolume;
    }

    protected abstract boolean isInsideShells(Point point, LinkedList<Shell> shells);

    @Override
    public String GetName() {
        return "Volume";
    }
}
