package Helpers;

import Model.Point;

public class PointFactory {

    private final double radius;
    private final Point offset;

    public PointFactory(double radius, Point offset) {
        this.radius = radius;
        this.offset = offset;
    }

    public Point CreatePoint(double sinPhi, double cosPhi, double sinTheta, double cosTheta){
        return new Point(radius, sinPhi, cosPhi, sinTheta, cosTheta, offset);
    }
}
