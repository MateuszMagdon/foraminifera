package Model;


import Helpers.Point;
import Helpers.ReferenceSpace;
import Helpers.Vector;
import OpenGL.Sphere;
import OpenGL.SphereFactory;

public class Shell {
    private Point center;
    private Point aperturePosition;

    private double radius;
    private double thickness;

    private Vector scaleRate;

    private ReferenceSpace referenceSpace;

    private Vector nextShellGrowthAxis;
    private ReferenceSpace nextShellReferenceSpace;

    private Sphere innerSphere;
    private Sphere outerSphere;

    private Shell previousShell;

    private SphereFactory sphereFactory;

    public Shell(SphereFactory sphereFactory) //for initial shell only
    {
        previousShell = null;
        this.sphereFactory = sphereFactory;

        center = new Point(0, 0, 0);
        radius = 1.0d;
        thickness = 0.1d;

        scaleRate = new Vector(1.0d, 1.0d, 1.0d);
        referenceSpace = new ReferenceSpace(
                new Vector(1.0d, 0.0d, 0.0d),
                new Vector(0.0d, 1.0d, 0.0d),
                new Vector(0.0d, 0.0d, 1.0d));

        createOpenGLSpheres();

        aperturePosition = new Point(0, 1.0d, 0);
        nextShellGrowthAxis = aperturePosition.GetVector(center);

        nextShellReferenceSpace = calculateNextReferenceSpace();
    }

    public Shell(Point center, double radius, double thickness, Shell previousShell, Vector scaleRate,
                 SphereFactory sphereFactory)
    {
        this.previousShell = previousShell;
        this.sphereFactory = sphereFactory;

        this.center = center.Clone();
        this.radius = radius;
        this.thickness = thickness;

        this.scaleRate = scaleRate.Clone();

        referenceSpace = previousShell.nextShellReferenceSpace;

        createOpenGLSpheres();

        aperturePosition = calculateAperturePosition(previousShell);
        nextShellGrowthAxis = aperturePosition.GetVector(previousShell.aperturePosition).Normalize();

        nextShellReferenceSpace = calculateNextReferenceSpace();
    }

    private void createOpenGLSpheres() {
        innerSphere = sphereFactory.CreateSphere(radius, center, scaleRate, referenceSpace);
        outerSphere = sphereFactory.CreateSphere(radius + thickness, center, scaleRate, referenceSpace);
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

    private ReferenceSpace calculateNextReferenceSpace() {
        Vector diffOnYAxis = referenceSpace.getY().DifferenceVector(nextShellGrowthAxis);


        //TODO what should be x and z?

        return new ReferenceSpace(new Vector(0,0,0), nextShellGrowthAxis, new Vector(0,0,0));
    }


    public double getThickness() {
        return thickness;
    }

    public double getRadius() {
        return radius;
    }

    public Point getAperturePosition() {
        return aperturePosition.Clone();
    }

    public Vector getNextShellGrowthAxis() {
        return nextShellGrowthAxis.Clone();
    }

    public Sphere getInnerSphere() {
        return innerSphere;
    }

    public Sphere getOuterSphere() {
        return outerSphere;
    }
}
