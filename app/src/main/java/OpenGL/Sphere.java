package OpenGL;


import java.nio.FloatBuffer;
import java.util.List;

import Model.Point;
import Model.Vector;

public class Sphere {
    public int pointsCount;
    public FloatBuffer sphereVerticesBuffer;
    public List<Point> points;

    private final Point center;
    private double radius;
    private Vector scaleRate;

    public Sphere(double radius, Point center, Vector scaleRate, int pointsCount) {
        this.radius = radius;
        this.center = center;
        this.scaleRate = scaleRate;
        this.pointsCount = pointsCount;
    }

}
