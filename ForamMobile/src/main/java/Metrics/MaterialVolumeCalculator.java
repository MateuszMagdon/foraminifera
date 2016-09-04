package Metrics;

import java.util.LinkedList;

import Helpers.Point;
import Model.Foraminifera;
import Model.Shell;
import OpenGL.Sphere;

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
            Sphere outerSphere = shell.getOuterSphere();
            Sphere innerSphere = shell.getInnerSphere();
            if (outerSphere.IsPointInside(point) && !innerSphere.IsPointInside(point)){
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
