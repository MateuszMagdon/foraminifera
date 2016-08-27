package Model;

public class Point {
    public double x;
    public double y;
    public double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double radius, double phi, double theta, Point offset) {
        double sinPhi = Math.sin(phi);
        double cosPhi = Math.cos(phi);
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        calculatePosition(radius, sinPhi, cosPhi, sinTheta, cosTheta, offset);
    }

    public Point(double radius, double sinPhi, double cosPhi, double sinTheta, double cosTheta, Point offset){
        calculatePosition(radius, sinPhi, cosPhi, sinTheta, cosTheta, offset);
    }

    private void calculatePosition(double radius, double sinPhi, double cosPhi, double sinTheta, double cosTheta, Point offset) {
        x = radius * sinPhi * cosTheta;
        x += offset.x;
        y = radius * sinPhi * sinTheta;
        y += offset.y;
        z = radius * cosPhi;
        z += offset.z;
    }

    public Point ScalePointPosition(Vector scalingVector){
        x *= scalingVector.x;
        y *= scalingVector.y;
        z *= scalingVector.z;
        return this;
    }

    public float[] AsFloatArray(){
        float[] result = new float[3];
        result[0] = (float) x;
        result[1] = (float) y;
        result[2] = (float) z;

        return result;
    }

    public Point AddVector(Vector vector){
        double x = this.x + vector.x;
        double y = this.y + vector.y;
        double z = this.z + vector.z;

        return new Point(x, y, z);
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
}
