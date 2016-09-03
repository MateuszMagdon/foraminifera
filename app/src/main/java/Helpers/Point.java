package Helpers;

public class Point {
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z) {
        assignNewValues(x, y, z);
    }

    public Point(double sinPhi, double cosPhi, double sinTheta, double cosTheta){
        assignNewValues(sinPhi * cosTheta, cosPhi, sinPhi * sinTheta);
    }

    private void assignNewValues(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
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

        //TODO rotate to reference space

        return this;
    }

    public Point Rotate(Vector rotationVector){
        RotateZ(rotationVector.getZ());
        RotateY(rotationVector.getY());
        RotateX(rotationVector.getX());
        return this;
    }

    public Point RotateX(double angle){
        double sinRot = Math.sin(angle);
        double cosRot = Math.cos(angle);

        double newX = x;
        double newY = y * cosRot - z * sinRot;
        double newZ = y * sinRot + z * cosRot;

        assignNewValues(newX, newY, newZ);

        return this;
    }

    public Point RotateY(double angle){
        double sinRot = Math.sin(angle);
        double cosRot = Math.cos(angle);

        double newX = z * sinRot + x * cosRot;
        double newY = y;
        double newZ = z * cosRot - x * sinRot;

        assignNewValues(newX, newY, newZ);

        return this;
    }

    public Point RotateZ(double angle){
        double sinRot = Math.sin(angle);
        double cosRot = Math.cos(angle);

        double newX = x * cosRot - y * sinRot;
        double newY = x * sinRot + y * cosRot;
        double newZ = z;

        assignNewValues(newX, newY, newZ);

        return this;
    }

    public Point Translate(Vector translationVector){
        x += translationVector.getX();
        y += translationVector.getY();
        z += translationVector.getZ();
        return this;
    }

    public Point Normalize(){
        double distanceToCenter = this.GetDistance(new Point(0, 0, 0));

        x /= distanceToCenter;
        y /= distanceToCenter;
        z /= distanceToCenter;

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
