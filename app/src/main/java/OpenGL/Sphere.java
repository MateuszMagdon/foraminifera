package OpenGL;


import java.nio.FloatBuffer;
import java.util.List;

import Helpers.Point;
import Helpers.ReferenceSpace;
import Helpers.Vector;

public class Sphere {
    public int pointsCount;
    public FloatBuffer sphereVerticesBuffer;
    public List<Point> points;

    private final Point center;
    private double radius;
    private Vector scaleRate;
    private ReferenceSpace referenceSpace;

    public Sphere(double radius, Point center, Vector scaleRate, ReferenceSpace referenceSpace, int pointsCount) {
        this.radius = radius;
        this.center = center;
        this.scaleRate = scaleRate;
        this.referenceSpace = referenceSpace;
        this.pointsCount = pointsCount;
    }

}
