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

    public Vector multiply(double growthFactor) {
        x *= growthFactor;
        y *= growthFactor;
        z *= growthFactor;

        return this;
    }

    public Vector rotate(double rotationAngle){

        return this;
    }

    public Vector deflect(double rotationAngle) {

        return this;
    }

    public Vector clone(){
        return new Vector(x, y, z);
    }
}
