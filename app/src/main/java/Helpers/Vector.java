package Helpers;

public class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector Multiply(double growthFactor) {
        x *= growthFactor;
        y *= growthFactor;
        z *= growthFactor;

        return this;
    }

    public Vector Deflect(double deviationAngle) {

        double cos = Math.cos(deviationAngle);
        double sin = Math.sin(deviationAngle);

        double xPrime = x * cos - y * sin;
        double yPrime = x * sin + y * cos;

        x = xPrime;
        y = yPrime;

        return this;
    }

    public Vector Rotate(double rotationAngle, Vector rotationAxisVector) {
        Vector rotationAxisVersor = rotationAxisVector.Clone().Normalize();
        double u = rotationAxisVersor.x;
        double v = rotationAxisVersor.y;
        double w = rotationAxisVersor.z;

        double cos = Math.cos(rotationAngle);
        double sin = Math.sin(rotationAngle);

        double xPrime = u * (u * x + v * y + w * z) * (1d - cos)
                + x * cos
                + (-w * y + v * z) * sin;
        double yPrime = v * (u * x + v * y + w * z) * (1d - cos)
                + y * cos
                + (w * x - u * z) * sin;
        double zPrime = w * (u * x + v * y + w * z) * (1d - cos)
                + z * cos
                + (-v * x + u * y) * sin;

        x = xPrime;
        y = yPrime;
        z = zPrime;

        return this;
    }

    public Vector Normalize() {
        double length = GetLength();
        x /= length;
        y /= length;
        z /= length;

        return this;
    }

    public Vector Cross(Vector v){
        return new Vector(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x);
    }

    public Vector DifferenceVector(Vector newVector){

        return new Vector(newVector.x - x, newVector.y - y, newVector.z - z);
    }

    public Vector Clone() {
        return new Vector(x, y, z);
    }

    public double GetLength(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public String toString() {
        return "Vector{" +
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
