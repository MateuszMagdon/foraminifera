package Helpers;

import Model.Point;
import Model.Vector;

public class PointFactory {

    private final double radius;
    private final Point offset;
    private final Vector scaleRate;

    public PointFactory(double radius, Point offset, Vector scaleRate) {
        this.radius = radius;
        this.offset = offset;
        this.scaleRate = scaleRate;
    }

    public Point CreatePoint(double sinPhi, double cosPhi, double sinTheta, double cosTheta){
        return new Point(radius, sinPhi, cosPhi, sinTheta, cosTheta, offset).ScalePointPosition(scaleRate);
    }
}
