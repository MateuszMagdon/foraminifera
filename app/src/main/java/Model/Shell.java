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

        this.aperturePosition = calculateAperturePosition(previousShell.aperturePosition);
        //this.axisVector = aperturePosition.getVector(previousShell.aperturePosition);
    }

    private void createOpenGLSperes() {
        innerSphere = new Sphere(radius, center);
        outerSphere = new Sphere(radius + thickness, center);
    }

    private Point calculateAperturePosition(Point previousAperturePosition) {
        return null;
    }




}
