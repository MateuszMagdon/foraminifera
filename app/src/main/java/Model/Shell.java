package Model;


import OpenGL.Sphere;

public class Shell {
    public Point center;
    public Point aperturePosition;

    public double radius;
    public double thickness;

    public Vector scaleRate;

    public Vector axisVector;

    public Sphere innerSphere;
    public Sphere outerSphere;

    public Shell()
    {
        center = new Point(0, 0, 0);
        radius = 1.0d;
        thickness = 0.02d;
        scaleRate = new Vector(1.0d, 1.0d, 1.0d);

        createOpenGLSpheres();

        aperturePosition = new Point(0, 1, 0);
        this.axisVector = aperturePosition.GetVector(center);
    }

    public Shell(Point center, double radius, double thickness, Shell previousShell, Vector scaleRate)
    {
        this.center = center;
        this.radius = radius;
        this.thickness = thickness;

        this.scaleRate = scaleRate;

        createOpenGLSpheres();

        this.aperturePosition = calculateAperturePosition(previousShell);
        this.axisVector = aperturePosition.GetVector(previousShell.aperturePosition);
    }

    private void createOpenGLSpheres() {
        innerSphere = new Sphere(radius, center, scaleRate);
        outerSphere = new Sphere(radius + thickness, center, scaleRate);
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
