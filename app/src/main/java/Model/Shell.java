package Model;


public class Shell {
    public Point center;
    public Point aperturePosition;

    public double radius;
    public double thickness;

    public Vector axisVector;

    public Shell(Point center, double radius, double thickness, Point aperturePosition){
        setFields(center, radius, thickness);
        this.aperturePosition = aperturePosition;
        this.axisVector = aperturePosition.getVector(center);
    }

    public Shell(Point center, double radius, double thickness, Shell previousShell){
        setFields(center, radius, thickness);
        this.aperturePosition = calculateAperturePosition(previousShell.aperturePosition);
        this.axisVector = aperturePosition.getVector(previousShell.aperturePosition);
    }

    private void setFields(Point center, double radius, double thickness) {
        this.center = center;
        this.radius = radius;
        this.thickness = thickness;
    }

    private Point calculateAperturePosition(Point aperturePosition) {
        return ;
    }


}
