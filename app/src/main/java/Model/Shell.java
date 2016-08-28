package Model;


import OpenGL.Sphere;
import OpenGL.SphereFactory;

public class Shell {
    public Point center;
    public Point aperturePosition;

    public double radius;
    public double thickness;

    public Vector scaleRate;

    public Vector axisVector;

    public Sphere innerSphere;
    public Sphere outerSphere;
    private SphereFactory sphereFactory;

    public Shell(SphereFactory sphereFactory)
    {
        this.sphereFactory = sphereFactory;

        center = new Point(0, 0, 0);
        radius = 1.0d;
        thickness = 0.1d;
        scaleRate = new Vector(1.0d, 1.0d, 1.0d);

        createOpenGLSpheres();

        aperturePosition = new Point(0, 1.0d, 0);
        this.axisVector = aperturePosition.GetVector(center);
    }

    public Shell(Point center, double radius, double thickness, Shell previousShell, Vector scaleRate, SphereFactory sphereFactory)
    {
        this.sphereFactory = sphereFactory;

        this.center = center;
        this.radius = radius;
        this.thickness = thickness;

        this.scaleRate = scaleRate.Clone();

        createOpenGLSpheres();

        this.aperturePosition = calculateAperturePosition(previousShell);
        this.axisVector = aperturePosition.GetVector(previousShell.aperturePosition);
    }

    private void createOpenGLSpheres() {
        innerSphere = sphereFactory.CreateSphere(radius, center, scaleRate);
        outerSphere = sphereFactory.CreateSphere(radius + thickness, center, scaleRate);
    }

    private Point calculateAperturePosition(Shell previousShell) {
        Point min = null;
        double minDistance = 0;

        for (Point point : outerSphere.points) {
            double distance = point.GetDistance(previousShell.center);
            double previousOuterRadius = previousShell.radius + previousShell.thickness;
            if (distance > previousOuterRadius){
                if (min == null || distance < minDistance){
                    min = point;
                    minDistance = distance;
                }
            }
        }
        return min;
    }




}
