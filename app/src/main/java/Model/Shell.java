package Model;

/**
 * Created by mateu_000 on 2015-02-08.
 */
public class Shell {
    private Point center;
    private double radius;
    private Point apperturePosition;
    private double thickness;

    private int shellIterator;

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Point getApperturePosition() {
        return apperturePosition;
    }

    public void setApperturePosition(Point apperturePosition) {
        this.apperturePosition = apperturePosition;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getShellIterator() {
        return shellIterator;
    }

    public Shell(int shellIterator) {
        this.shellIterator = shellIterator;
    }
}
