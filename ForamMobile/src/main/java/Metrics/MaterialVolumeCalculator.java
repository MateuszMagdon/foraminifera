package Metrics;

import java.util.LinkedList;

import Helpers.Point;
import Model.Foraminifera;
import Model.Shell;

public class MaterialVolumeCalculator extends MonteCarloVolumeCalculator {
    @Override
    public double CalculateMetric(Foraminifera foraminifera) {
        triesCount = 30000;

        double volume  = super.CalculateMetric(foraminifera);
        foraminifera.getMetrics().setMaterialVolume(volume);
        return volume;
    }

    @Override
    protected boolean isInsideShells(Point point, LinkedList<Shell> shells) {

        for (Shell shell : shells){
            double distance = shell.getCenter().GetDistance(point);
            boolean isInsideSphere = distance < shell.getRadius() + shell.getThickness();
            boolean isInsideChamber = distance < shell.getRadius();
            if (isInsideSphere && !isInsideChamber){
                return true;
            }
        }
        return false;
    }

    @Override
    public String GetName() {
        return "Material volume";
    }
}
