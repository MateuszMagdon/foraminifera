package Helpers;

public class Point {
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double sinPhi, double cosPhi, double sinTheta, double cosTheta){
        x = sinPhi * cosTheta;
        y = cosPhi;
        z = sinPhi * sinTheta;
    }

    public Point Scale(Vector scalingVector){
        x *= scalingVector.getX();
        y *= scalingVector.getY();
        z *= scalingVector.getZ();
        return this;
    }

    public Point Scale(Vector scalingVector, ReferenceSpace referenceSpace){
        x *= scalingVector.getX();
        y *= scalingVector.getY();
        z *= scalingVector.getZ();
        return this;
    }

    public Point Rotate(){

        return this;
    }

    public Point Translate(Vector translationVector){
        x += translationVector.getX();
        y += translationVector.getY();
        z += translationVector.getZ();
        return this;
    }

    public Point Clone(){
        return new Point(x, y, z);
    }

    public float[] AsFloatArray(){
        float[] result = new float[3];
        result[0] = (float) x;
        result[1] = (float) y;
        result[2] = (float) z;

        return result;
    }

    public Vector GetVector(){
        return GetVector(new Point(0, 0, 0));
    }

    public Vector GetVector(Point basePoint){
        return new Vector(x - basePoint.x, y - basePoint.y, z - basePoint.z);
    }

    public double GetDistance(Point point){
        double v = x - point.x;
        double u = y - point.y;
        double w = z - point.z;
        return Math.sqrt(v * v + u * u + w * w);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
