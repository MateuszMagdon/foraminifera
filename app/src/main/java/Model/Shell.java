package Model;


import OpenGL.Sphere;

public class Shell {
    public Point center;
    public Point aperturePosition;

    public double radius;
    public double thickness;

    public Vector axisVector;

    public Sphere innerSphere;
    public Sphere outerSphere;

    public Shell()
    {
        center = new Point(0, 0, 0);
        thickness = 0.02d;
        radius = 1.0d;

        createOpenGLSperes();

        aperturePosition = new Point(0, 1, 0);
        this.axisVector = aperturePosition.getVector(center);
    }

    public Shell(Point center, double radius, double thickness, Shell previousShell)
    {
        this.center = center;
        this.radius = radius;
        this.thickness = thickness;

        createOpenGLSperes();

        this.aperturePosition = calculateAperturePosition(previousShell);
        this.axisVector = aperturePosition.getVector(previousShell.aperturePosition);
    }

    private void createOpenGLSperes() {
        innerSphere = new Sphere(radius, center);
        outerSphere = new Sphere(radius + thickness, center);
    }

    private Point calculateAperturePosition(Shell previousShell) {
        Point min = null;
        double minDistance = 0;

        for (Point point : outerSphere.points) {
            double distance = point.getDistance(previousShell.center);
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
