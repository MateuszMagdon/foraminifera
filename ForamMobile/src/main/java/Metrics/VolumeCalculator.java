package Metrics;

import java.util.LinkedList;

import Helpers.Metrics;
import Helpers.Point;
import Model.Foraminifera;
import Model.Shell;

public class VolumeCalculator implements IMetricCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        int triesCount = 10000;
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

        double volume = ((double) insideCount / triesCount) * cubeVolume;
        metrics.setVolume(volume);

        return volume;
    }

    private boolean isInsideShells(Point point, LinkedList<Shell> shells) {

        for (Shell shell : shells){
            boolean isInsideSphere = shell.getCenter().GetDistance(point) < shell.getRadius() + shell.getThickness();
            if (isInsideSphere){
                return true;
            }
        }
        return false;
    }

    @Override
    public String GetName() {
        return "Volume";
    }
}
