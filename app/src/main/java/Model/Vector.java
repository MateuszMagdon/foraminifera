package Model;

public class Vector {
    public double x;
    public double y;
    public double z;

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
        //Rotate this vector

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
}
