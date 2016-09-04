package Model;


import java.util.LinkedList;

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
        Point center = new Point(0, 0, 0);
        double radius = 1.0d;
        double thickness = 0.1d;

        Shell previousShell = null;

        Vector scaleRate = new Vector(1.0d, 1.0d, 1.0d);
        referenceSpace = new ReferenceSpace(
                new Vector(1.0d, 0.0d, 0.0d),
                new Vector(0.0d, 1.0d, 0.0d),
                new Vector(0.0d, 0.0d, 1.0d));

        initializeShell(center, radius, thickness, previousShell, scaleRate, sphereFactory);

        aperturePosition = new Point(0, 1.1d, 0);
        createOpenGLSpheres();

        nextShellGrowthAxis = aperturePosition.GetVector(center).Normalize();
        nextShellReferenceSpace = calculateNextReferenceSpace();
    }

    public Shell(Point center, double radius, double thickness, Shell previousShell, Vector scaleRate,
                 SphereFactory sphereFactory)
    {
        referenceSpace = previousShell.nextShellReferenceSpace;

        initializeShell(center, radius, thickness, previousShell, scaleRate, sphereFactory);

        createOpenGLSpheres();

        nextShellGrowthAxis = aperturePosition.GetVector(previousShell.aperturePosition).Normalize();
        nextShellReferenceSpace = calculateNextReferenceSpace();
    }

    private void initializeShell(Point center, double radius, double thickness, Shell previousShell, Vector scaleRate,
                                 SphereFactory sphereFactory){
        this.previousShell = previousShell;
        this.sphereFactory = sphereFactory;

        this.center = center.Clone();
        this.radius = radius;
        this.thickness = thickness;

        this.scaleRate = scaleRate.Clone();
    }

    private void createOpenGLSpheres() {
        LinkedList<Shell> previousShells = GetPreviousShells();

        outerSphere = sphereFactory.CreateSphere(radius + thickness, center, scaleRate, referenceSpace);
        innerSphere = sphereFactory.CreateSphere(radius, center, scaleRate, referenceSpace);

        calculateAperturePosition(outerSphere);

        sphereFactory.RotateSphereToAperture(outerSphere, aperturePosition, scaleRate, referenceSpace);
        sphereFactory.CalculateTrianglesForSphere(outerSphere, previousShells);

        sphereFactory.RotateSphereToAperture(innerSphere, aperturePosition, scaleRate, referenceSpace);
        sphereFactory.CalculateTrianglesForSphere(innerSphere, previousShells);
    }

    private void calculateAperturePosition(Sphere sphere) {
        if (aperturePosition == null){
            Point min = null;
            double minDistance = 0;

            for (Point point : sphere.points) {
                if (!isInsidePrevSpheres(point, GetPreviousShells())){
                    double distance = point.GetDistance(previousShell.aperturePosition);
                    if (min == null || distance < minDistance){
                        min = point;
                        minDistance = distance;
                    }
                }
            }

            aperturePosition = min;
        }
    }

    private boolean isInsidePrevSpheres(Point point, LinkedList<Shell> shells) {

        for (Shell shell : shells){
            boolean isInsideSphere = shell.outerSphere.IsPointInside(point);
            if (isInsideSphere){
                return true;
            }
        }
        return false;
    }

    private ReferenceSpace calculateNextReferenceSpace() {
        Vector diffOnYAxis = referenceSpace.getY().DifferenceVector(nextShellGrowthAxis);
        //TODO X and Z needs to be rotated (just like Sphere is in Sphere factory in RotateSphereToAperture());

        return new ReferenceSpace(new Vector(0,0,0), nextShellGrowthAxis, new Vector(0,0,0));
    }

    public LinkedList<Shell> GetPreviousShells(){
        LinkedList<Shell> previousShells = new LinkedList<>();

        Shell previous = previousShell;
        while (previous != null){
            previousShells.add(previous);
            previous = previous.previousShell;
        }

        return previousShells;
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
        return nextShellGrowthAxis.Clone().Normalize();
    }

    public Sphere getInnerSphere() {
        return innerSphere;
    }

    public Sphere getOuterSphere() {
        return outerSphere;
    }

    public Point getCenter() {
        return center;
    }
}
